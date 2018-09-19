package com.zmm.tmsystem.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.zmm.tmsystem.R;
import com.zmm.tmsystem.bean.TeacherBean;
import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.common.utils.ACache;
import com.zmm.tmsystem.common.utils.ToastUtils;
import com.zmm.tmsystem.dagger.component.AppComponent;
import com.zmm.tmsystem.dagger.component.DaggerTeacherComponent;
import com.zmm.tmsystem.dagger.module.TeacherModule;
import com.zmm.tmsystem.mvp.presenter.TeacherPresenter;
import com.zmm.tmsystem.mvp.presenter.contract.TeacherContract;
import com.zmm.tmsystem.rx.RxBus;
import com.zmm.tmsystem.ui.widget.CustomInfoItemView;
import com.zmm.tmsystem.ui.widget.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Description:教师信息
 * Author:zhangmengmeng
 * Date:2018/6/15
 * Time:下午4:09
 */

public class TeacherInfoActivity extends BaseActivity<TeacherPresenter> implements
        CustomInfoItemView.OnItemClickListener, TeacherContract.TeacherView {

    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.custom_item_icon)
    CustomInfoItemView mCustomItemIcon;
    @BindView(R.id.custom_item_name)
    CustomInfoItemView mCustomItemName;
    @BindView(R.id.custom_item_gender)
    CustomInfoItemView mCustomItemGender;
    @BindView(R.id.custom_item_phone)
    CustomInfoItemView mCustomItemPhone;
    @BindView(R.id.custom_item_childcare)
    CustomInfoItemView mCustomItemChildcare;
    @BindView(R.id.custom_item_school)
    CustomInfoItemView mCustomItemSchool;
    @BindView(R.id.custom_item_grade)
    CustomInfoItemView mCustomItemGrade;
    @BindView(R.id.custom_item_course)
    CustomInfoItemView mCustomItemCourse;
    @BindView(R.id.custom_item_address)
    CustomInfoItemView mCustomItemAddress;
    @BindView(R.id.root_view)
    LinearLayout mRootView;
    private TeacherBean mTeacherBean;
    private ArrayList<ImageItem> mImages;


    @Override
    protected int setLayout() {
        return R.layout.activity_teacher_info;
    }


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerTeacherComponent.builder()
                .appComponent(appComponent)
                .teacherModule(new TeacherModule(this))
                .build()
                .inject(this);
    }


    @Override
    protected void init() {

        //头像单选
        ImagePicker.getInstance().setMultiMode(false);


        mTitleBar.setCenterTitle("教师信息");
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


        mCustomItemIcon.setOnItemClickListener(this, Constant.TYPE_ICON);
        mCustomItemName.setOnItemClickListener(this, Constant.TYPE_NAME);
        mCustomItemGender.setOnItemClickListener(this, Constant.TYPE_GENDER);
        mCustomItemPhone.setOnItemClickListener(this, Constant.TYPE_PHONE);
        mCustomItemChildcare.setOnItemClickListener(this, Constant.TYPE_CHILDCARE_NAME);
        mCustomItemSchool.setOnItemClickListener(this, Constant.TYPE_SCHOOL);
        mCustomItemGrade.setOnItemClickListener(this, Constant.TYPE_GRADE);
        mCustomItemCourse.setOnItemClickListener(this, Constant.TYPE_COURSE);
        mCustomItemAddress.setOnItemClickListener(this, Constant.TYPE_ADDRESS);

        mCustomItemGender.setContent("女");

        ACache aCache = ACache.get(this);
        mTeacherBean = (TeacherBean) aCache.getAsObject(Constant.TEACHER);

        initData(mTeacherBean);


    }

    /**
     * 初始化数据
     */
    private void initData(TeacherBean teacherBean) {


        String icon = teacherBean.getIcon();
        String name = teacherBean.getName();
        int gender = teacherBean.getGender();
        String phone = teacherBean.getPhoneNum1();
        String childcareName = teacherBean.getChildcareName();
        String schoolName = teacherBean.getSchoolName();
        String gradeName = teacherBean.getGradeName();
        String courseName = teacherBean.getCourseName();
        String address = teacherBean.getAddress();

        if(!TextUtils.isEmpty(icon)){
            mCustomItemIcon.setIcon(icon,gender);
        }

        if (!TextUtils.isEmpty(name)) {
            mCustomItemName.setContent(name);
        }

        if (!TextUtils.isEmpty(phone)) {
            mCustomItemPhone.setContent(phone);
        }

        if (!TextUtils.isEmpty(childcareName)) {
            mCustomItemChildcare.setContent(childcareName);
        }

        if (!TextUtils.isEmpty(schoolName)) {
            mCustomItemSchool.setContent(schoolName);
        }

        if (!TextUtils.isEmpty(gradeName)) {
            mCustomItemGrade.setContent(gradeName);
        }

        if (!TextUtils.isEmpty(courseName)) {
            mCustomItemCourse.setContent(courseName);
        }

        if (!TextUtils.isEmpty(address)) {
            mCustomItemAddress.setContent(address);
        }

        if (gender == 0) {
            mCustomItemGender.setContent("女");
        } else {
            mCustomItemGender.setContent("男");
        }

    }

    @Override
    public void itemClick(int type,String name) {

        if(type == Constant.TYPE_ICON){
            uloadIcon();
        }else {
            mPresenter.updateTeacherByType(type,name,mRootView,mScreenWidth);
        }

    }



    private void uloadIcon() {


        ImagePicker.getInstance().setCrop(true);

        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, 100);

    }

    @Override
    public void showLoading() {
        makeWindowDark();
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void dismissLoading() {
        makeWindowLight();
    }

    @Override
    public void updateSuccess(String title, TeacherBean teacherBean) {

//        RxBus.getDefault().post(Constant.UPDATE_TEACHER);

        ToastUtils.SimpleToast(this, "更新" + title + "成功");
        initData(teacherBean);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {

                mImages = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if(mImages != null && mImages.size() > 0){
                    System.out.println("选择图片："+ mImages.get(0).path);
                    mPresenter.uploadTeacherPic(mTeacherBean.getId(), mImages.get(0).path);
                }

            } else {
                System.out.println("没有数据");
            }
        }
    }


}
