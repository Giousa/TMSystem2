package com.zmm.tmsystem.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.zmm.tmsystem.R;
import com.zmm.tmsystem.bean.CertificatesBean;
import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.common.utils.PictureCompressUtil;
import com.zmm.tmsystem.common.utils.ToastUtils;
import com.zmm.tmsystem.dagger.component.AppComponent;
import com.zmm.tmsystem.dagger.component.DaggerCertificateInfoComponent;
import com.zmm.tmsystem.dagger.module.CertificateInfoModule;
import com.zmm.tmsystem.mvp.presenter.CertificateInfoPresenter;
import com.zmm.tmsystem.mvp.presenter.contract.CertificateInfoContract;
import com.zmm.tmsystem.ui.adapter.ImagePickerAdapter;
import com.zmm.tmsystem.ui.widget.ShapeLoadingDialog;
import com.zmm.tmsystem.ui.widget.TitleBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/9/4
 * Email:65489469@qq.com
 */
public class CertificateInfoActivity extends BaseActivity<CertificateInfoPresenter> implements ImagePickerAdapter.OnRecyclerViewItemClickListener,CertificateInfoContract.CertificateInfoView {


    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.et_title)
    EditText mEditText;
    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;


    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 3;               //允许选择图片最大数
    private ArrayList<ImageItem> images = null;
    private ArrayList<String> mNewListPath = new ArrayList<>();


    private String mChildcareStudentId;
    private ShapeLoadingDialog mShapeLoadingDialog;


    @Override
    protected int setLayout() {
        return R.layout.activity_certificate_info;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerCertificateInfoComponent.builder()
                .appComponent(appComponent)
                .certificateInfoModule(new CertificateInfoModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void init() {

        mChildcareStudentId = this.getIntent().getStringExtra(Constant.CHILDCARE_STUDENT_ID);

        //多选
        ImagePicker.getInstance().setMultiMode(true);

        initToolBar();

        initListView();

    }

    private void initListView() {
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
    }

    private void initToolBar() {
        //这里一定要加上，否则menu不显示
        setSupportActionBar(mTitleBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mTitleBar.setCenterTitle("上传荣誉证书");
        mTitleBar.setNavigationIcon(new IconicsDrawable(this)
                .icon(Ionicons.Icon.ion_android_arrow_back)
                .sizeDp(20)
                .color(getResources().getColor(R.color.white)
                ));
        mTitleBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:

                //打开选择,本次允许选择的数量
                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                Intent intent1 = new Intent(CertificateInfoActivity.this, ImageGridActivity.class);
                startActivityForResult(intent1, REQUEST_CODE_SELECT);

                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null) {
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);

                    for (int i = 0; i < images.size(); i++) {

                        System.out.println("选取的图片名称1："+images.get(i).name);
                        Bitmap bitmap = null;
                        try {
                            bitmap = PictureCompressUtil.revitionImageSize(images.get(i).path);
                            String newPath = PictureCompressUtil.saveBitmapFile(bitmap, "tmsystem/"+images.get(i).name);
                            mNewListPath.add("/storage/emulated/0/"+newPath);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null) {
                    selImageList.clear();
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);

                    mNewListPath.clear();

                    for (int i = 0; i < images.size(); i++) {

                        System.out.println("选取的图片名称2："+images.get(i).name);
                        Bitmap bitmap = null;
                        try {
                            bitmap = PictureCompressUtil.revitionImageSize(images.get(i).path);
                            String newPath = PictureCompressUtil.saveBitmapFile(bitmap, "tmsystem/"+images.get(i).name);
                            mNewListPath.add("/storage/emulated/0/"+newPath);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }


    @OnClick(R.id.btn_select_confirm)
    public void onViewClicked() {

        String title = mEditText.getText().toString();
        String content = mEtContent.getText().toString();

        if(TextUtils.isEmpty(title)){
            ToastUtils.SimpleToast(CertificateInfoActivity.this,"请输入标题");
            return;
        }

        if(selImageList == null || selImageList.size() == 0){
            ToastUtils.SimpleToast(CertificateInfoActivity.this,"请选择图片");
            return;
        }

        System.out.println("---上传荣誉证书---");

        mPresenter.uploadMultyPics(mChildcareStudentId,title,content,mNewListPath);

    }

    @Override
    public void uploadSuccess() {
        for (int i = 0; i < mNewListPath.size(); i++) {
            PictureCompressUtil.deleteFile(mNewListPath.get(i));
        }
        ToastUtils.SimpleToast(CertificateInfoActivity.this,"上传成功");
        finish();
    }

    @Override
    public void querySuccess(List<CertificatesBean> certificatesBeans) {

    }

    @Override
    public void deleteSuccess() {

    }

    @Override
    public void showLoading() {
        mShapeLoadingDialog = new ShapeLoadingDialog.Builder(this)
                .loadText("上传中...")
                .build();

        mShapeLoadingDialog.show();

    }

    @Override
    public void showError(String msg) {
        mShapeLoadingDialog.dismiss();
    }

    @Override
    public void dismissLoading() {
        mShapeLoadingDialog.dismiss();
    }
}
