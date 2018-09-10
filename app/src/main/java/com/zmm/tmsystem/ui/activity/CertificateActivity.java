package com.zmm.tmsystem.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.zmm.tmsystem.R;
import com.zmm.tmsystem.bean.CertificatesBean;
import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.dagger.component.AppComponent;
import com.zmm.tmsystem.dagger.component.DaggerCertificateInfoComponent;
import com.zmm.tmsystem.dagger.module.CertificateInfoModule;
import com.zmm.tmsystem.mvp.presenter.CertificateInfoPresenter;
import com.zmm.tmsystem.mvp.presenter.contract.CertificateInfoContract;
import com.zmm.tmsystem.ui.adapter.CertificateAdapter;
import com.zmm.tmsystem.ui.widget.TitleBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/9/4
 * Email:65489469@qq.com
 */
public class CertificateActivity extends BaseActivity<CertificateInfoPresenter> implements Toolbar.OnMenuItemClickListener, CertificateInfoContract.CertificateInfoView {

    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.empty)
    RelativeLayout mEmpty;
    @BindView(R.id.rl_parent)
    RelativeLayout rlParent;
    @BindView(R.id.iv_show_pic)
    PhotoView ivShowPic;

    private MenuItem mMenuItemAdd;
    private String mChildcareStudentId;
    private CertificateAdapter mCertificateAdapter;

    //谈入谈出
    AlphaAnimation in = new AlphaAnimation(0, 1);
    AlphaAnimation out = new AlphaAnimation(1, 0);

    @Override
    protected int setLayout() {
        return R.layout.activity_certificate;
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


        initPicScaleView();

        initToolBar();

        initListData();

    }

    private void initPicScaleView() {


        in.setDuration(500);
        out.setDuration(300);

        out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rlParent.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        ivShowPic.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ivShowPic.enable();
        ivShowPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rlParent.getVisibility() == View.VISIBLE) {
                    rlParent.startAnimation(out);
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.queryAllCertificates(mChildcareStudentId);

    }

    private void initToolBar() {
        //这里一定要加上，否则menu不显示
        setSupportActionBar(mTitleBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mTitleBar.setCenterTitle("荣誉证书");
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

        mTitleBar.setOnMenuItemClickListener(this);
    }


    private void initListData() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCertificateAdapter = new CertificateAdapter(this);
        mCertificateAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

//                Intent intent = new Intent(CertificateActivity.this, CertificatePicShowActivity.class);
//                intent.putExtra(Constant.CERTIFICATE, mCertificateAdapter.getData().get(position));
//                startActivity(intent);

                String[] pics = mCertificateAdapter.getData().get(position).getPic().split(",");
                if (pics.length == 0) {
                    return;
                }

                view.startAnimation(in);
                rlParent.setVisibility(View.VISIBLE);

                switch (view.getId()) {
                    case R.id.iv_item_1:
                        Glide.with(CertificateActivity.this)
                                .load(Constant.BASE_IMG_URL + pics[0])
                                .into(ivShowPic);
                        break;
                    case R.id.iv_item_2:
                        if (pics.length >= 1) {
                            Glide.with(CertificateActivity.this)
                                    .load(Constant.BASE_IMG_URL + pics[1])
                                    .into(ivShowPic);
                        }

                        break;
                    case R.id.iv_item_3:
                        if (pics.length >= 2) {
                            Glide.with(CertificateActivity.this)
                                    .load(Constant.BASE_IMG_URL + pics[2])
                                    .into(ivShowPic);
                        }

                        break;
                }
            }
        });

        mRecyclerView.setAdapter(mCertificateAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionbar, menu);
        menu.findItem(R.id.menu_setting).setVisible(false);

        mMenuItemAdd = menu.findItem(R.id.menu_add);

        mMenuItemAdd.setIcon(new IconicsDrawable(this)
                .icon(Ionicons.Icon.ion_android_add)
                .sizeDp(20)
                .color(getResources().getColor(R.color.white)
                ));

        mMenuItemAdd.setVisible(true);

        return true;
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {

        Intent intent = new Intent(this, CertificateInfoActivity.class);
        intent.putExtra(Constant.CHILDCARE_STUDENT_ID, mChildcareStudentId);
        startActivity(intent);

        return false;
    }

    @Override
    public void uploadSuccess() {

    }

    @Override
    public void querySuccess(List<CertificatesBean> certificatesBeans) {

        if (certificatesBeans != null && certificatesBeans.size() > 0) {
            mEmpty.setVisibility(View.GONE);
        } else {
            mEmpty.setVisibility(View.VISIBLE);
        }
        mCertificateAdapter.setNewData(certificatesBeans);
    }

    @Override
    public void deleteSuccess() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void onBackPressed() {
        if (rlParent.getVisibility() == View.VISIBLE) {
            rlParent.startAnimation(out);
        } else {
            super.onBackPressed();
        }
    }

}
