package com.zmm.tmsystem.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.print.PrintJob;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.zmm.tmsystem.R;
import com.zmm.tmsystem.bean.StudentBean;
import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.common.utils.ACache;
import com.zmm.tmsystem.common.utils.DateUtils;
import com.zmm.tmsystem.common.utils.ToastUtils;
import com.zmm.tmsystem.dagger.component.AppComponent;
import com.zmm.tmsystem.dagger.component.DaggerStudentComponent;
import com.zmm.tmsystem.dagger.module.StudentModule;
import com.zmm.tmsystem.mvp.presenter.StudentPresenter;
import com.zmm.tmsystem.mvp.presenter.contract.StudentContract;
import com.zmm.tmsystem.rx.RxBus;
import com.zmm.tmsystem.ui.widget.CustomInfoItemView;
import com.zmm.tmsystem.ui.widget.SimpleConfirmDialog;
import com.zmm.tmsystem.ui.widget.TitleBar;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description: 学生资料
 * Author:zhangmengmeng
 * Date:2018/6/20
 * Time:下午3:57
 */

public class StudentInfoActivity extends BaseActivity<StudentPresenter> implements StudentContract.StudentView, CustomInfoItemView.OnItemClickListener, Toolbar.OnMenuItemClickListener {


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
    @BindView(R.id.custom_item_address)
    CustomInfoItemView mCustomItemAddress;
    @BindView(R.id.custom_item_guardian1)
    CustomInfoItemView mCustomItemGuardian1;
    @BindView(R.id.custom_item_guardian_phone1)
    CustomInfoItemView mCustomItemGuardianPhone1;
    @BindView(R.id.custom_item_guardian2)
    CustomInfoItemView mCustomItemGuardian2;
    @BindView(R.id.custom_item_guardian_phone2)
    CustomInfoItemView mCustomItemGuardianPhone2;
    @BindView(R.id.custom_item_birthday)
    CustomInfoItemView mCustomItemBirthday;

    @BindView(R.id.root_view)
    LinearLayout mRootView;
    @BindView(R.id.btn_select_confirm)
    Button mBtnSelectConfirm;


    private int mIntentParam;
    private MenuItem mItemEdit;
    private boolean isEdit = true;
    private String mId;
    private String mIconPath;
    private String mPicPath;
    private long mBirthdayLong;


    @Override
    protected int setLayout() {
        return R.layout.activity_student_info;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerStudentComponent.builder()
                .appComponent(appComponent)
                .studentModule(new StudentModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void init() {

        //头像单选
        ImagePicker.getInstance().setMultiMode(false);

        //0：代表添加新学生，无删除按钮，无修改
        //1: 可选学生管理，代表修改学生，这个时候需要initData数据，移除学生，有删除按钮，可修改
        //2：从托管学生和移除学生跳转,无删除按钮,可修改
        mIntentParam = getIntent().getIntExtra(Constant.INTENT_PARAM, 0);

        mId = getIntent().getStringExtra(Constant.STUDENT_ID);

        System.out.println("mId = " + mId);


        if (mIntentParam == 1 || mIntentParam == 2) {
            isEdit = false;
            mBtnSelectConfirm.setText("修改学生信息");
            mBtnSelectConfirm.setVisibility(View.GONE);
        }

        initToolBar();

        initView();

        if (!TextUtils.isEmpty(mId)) {
            mPresenter.getStudentById(mId);
        }

    }


    private void initToolBar() {

        //这里一定要加上，否则menu不显示
        setSupportActionBar(mTitleBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mTitleBar.setCenterTitle("学生资料");
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

    private void initView() {
        mCustomItemIcon.setOnItemClickListener(this, Constant.TYPE_STUDENT_ICON);
        mCustomItemName.setOnItemClickListener(this, Constant.TYPE_STUDENT_NAME);
        mCustomItemGender.setOnItemClickListener(this, Constant.TYPE_STUDENT_GENDER);
        mCustomItemPhone.setOnItemClickListener(this, Constant.TYPE_STUDENT_PHONE);
        mCustomItemAddress.setOnItemClickListener(this, Constant.TYPE_STUDENT_ADDRESS);
        mCustomItemGuardian1.setOnItemClickListener(this, Constant.TYPE_STUDENT_GUARDIAN1);
        mCustomItemGuardianPhone1.setOnItemClickListener(this, Constant.TYPE_STUDENT_GUARDIANPHONE1);
        mCustomItemGuardian2.setOnItemClickListener(this, Constant.TYPE_STUDENT_GUARDIAN2);
        mCustomItemGuardianPhone2.setOnItemClickListener(this, Constant.TYPE_STUDENT_GUARDIANPHONE2);
        mCustomItemBirthday.setOnItemClickListener(this,Constant.TYPE_STUDENT_BIRTHDAY);

        mCustomItemGender.setContent("男");
    }


    private void initData(StudentBean studentBean) {

        if (mCustomItemIcon == null) {
            return;
        }

        String icon = studentBean.getIcon();
        mIconPath = icon;
        String name = studentBean.getName();
        int gender = studentBean.getGender();
        String phone = studentBean.getPhone();
        String address = studentBean.getAddress();
        String guardian1 = studentBean.getGuardian1();
        String guardian1Phone = studentBean.getGuardian1Phone();
        String guardian2 = studentBean.getGuardian2();
        String guardian2Phone = studentBean.getGuardian2Phone();

        long birthday = studentBean.getBirthday();
        if(birthday != 0){
            try {
                String s = DateUtils.longToString(birthday, null);
                mCustomItemBirthday.setContent(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        if (!TextUtils.isEmpty(icon)) {
            mCustomItemIcon.setIcon(icon,gender);
        }

        if (!TextUtils.isEmpty(name)) {
            mCustomItemName.setContent(name);
        }

        if (gender == 0) {
            mCustomItemGender.setContent("女");
        } else {
            mCustomItemGender.setContent("男");
        }

        if (!TextUtils.isEmpty(phone)) {
            mCustomItemPhone.setContent(phone);
        }

        if (!TextUtils.isEmpty(address)) {
            mCustomItemAddress.setContent(address);
        }

        if (!TextUtils.isEmpty(guardian1)) {
            mCustomItemGuardian1.setContent(guardian1);
        }

        if (!TextUtils.isEmpty(guardian1Phone)) {
            mCustomItemGuardianPhone1.setContent(guardian1Phone);
        }

        if (!TextUtils.isEmpty(guardian2)) {
            mCustomItemGuardian2.setContent(guardian2);
        }

        if (!TextUtils.isEmpty(guardian2Phone)) {
            mCustomItemGuardianPhone2.setContent(guardian2Phone);
        }


    }


    /**
     * 条目点击事件
     *
     * @param type
     * @param name
     */
    @Override
    public void itemClick(int type, String name) {

        if (isEdit) {

            if (type == Constant.TYPE_STUDENT_ICON) {
                uloadIcon();
            } else if(type == Constant.TYPE_STUDENT_BIRTHDAY){

                ACache aCache = ACache.get(this);
                aCache.put(Constant.STUDENT_BIRTHDAY,mCustomItemBirthday.getContent());

                mPresenter.updateStudentData(type, name, mRootView, mScreenWidth);
            } else {

                mPresenter.updateStudentData(type, name, mRootView, mScreenWidth);
            }
        } else {
            switch (type) {
                case Constant.TYPE_STUDENT_PHONE:
                    ToastUtils.SimpleToast(this, "电话");
                    break;

                case Constant.TYPE_STUDENT_GUARDIANPHONE1:
                    ToastUtils.SimpleToast(this, "电话1");

                    break;

                case Constant.TYPE_STUDENT_GUARDIANPHONE2:
                    ToastUtils.SimpleToast(this, "电话2");

                    break;

                case Constant.TYPE_STUDENT_ICON:
                    uloadIcon();

                    break;
            }
        }
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
    public void inputSuccess(int type, String content) {

        switch (type) {

            case Constant.TYPE_STUDENT_NAME:
                mCustomItemName.setContent(content);
                break;
            case Constant.TYPE_STUDENT_GENDER:
                mCustomItemGender.setContent(content);
//                if(content.equals("女")){
//                    mCustomItemIcon.setIcon(mCustomItemIcon.getContent(),0);
//                }else {
//                    mCustomItemIcon.setIcon(mCustomItemIcon.getContent(),1);
//                }
                break;
            case Constant.TYPE_STUDENT_PHONE:
                mCustomItemPhone.setContent(content);
                break;
            case Constant.TYPE_STUDENT_ADDRESS:
                mCustomItemAddress.setContent(content);
                break;
            case Constant.TYPE_STUDENT_GUARDIAN1:
                mCustomItemGuardian1.setContent(content);
                break;
            case Constant.TYPE_STUDENT_GUARDIANPHONE1:
                mCustomItemGuardianPhone1.setContent(content);
                break;
            case Constant.TYPE_STUDENT_GUARDIAN2:
                mCustomItemGuardian2.setContent(content);
                break;
            case Constant.TYPE_STUDENT_GUARDIANPHONE2:
                mCustomItemGuardianPhone2.setContent(content);
                break;
            case Constant.TYPE_STUDENT_BIRTHDAY:
                mCustomItemBirthday.setContent(content);
                break;

        }

    }

    @Override
    public void addSuccess(StudentBean studentBean) {
        ToastUtils.SimpleToast(this, getResources().getString(R.string.student_add_success));
//        RxBus.getDefault().post(Constant.UPDATE_STUDENT);
        finish();
    }

    @Override
    public void addChildcareStudentSuccess(String msg) {

    }

    @Override
    public void updateSuccess() {
        ToastUtils.SimpleToast(this, getResources().getString(R.string.student_update_success));
        isEdit = false;
        mItemEdit.setIcon(new IconicsDrawable(this)
                .iconText("编辑")
                .sizeDp(30)
                .color(getResources().getColor(R.color.white)
                ));

        mBtnSelectConfirm.setVisibility(View.GONE);
//        RxBus.getDefault().post(Constant.UPDATE_STUDENT);

    }

    @Override
    public void deleteStudent(String s) {
        ToastUtils.SimpleToast(this, s);
//        RxBus.getDefault().post(Constant.UPDATE_STUDENT);
        finish();
    }

    @Override
    public void querySuccess(List<StudentBean> studentBeans) {

    }

    @Override
    public void queryStudent(StudentBean studentBean) {
        initData(studentBean);
//        RxBus.getDefault().post(Constant.UPDATE_STUDENT);
    }

    private void uloadIcon() {

        ImagePicker.getInstance().setCrop(true);

        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, 100);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {

                List<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null && images.size() > 0) {

                    mPicPath = images.get(0).path;
                    System.out.println("选择图片：" + mPicPath);
                    mCustomItemIcon.setLocalIcon(mPicPath);
                    if (mIntentParam != 0) {
                        //修改学生
                        mPresenter.uploadStudentPic(mId, mPicPath);
                    }
                }

            } else {
                System.out.println("没有数据");
            }
        }
    }

    @OnClick(R.id.btn_select_confirm)
    public void onViewClicked() {
        modifyStudent();

    }

    /**
     * 修改或添加学生信息
     */
    private void modifyStudent() {
        String icon = mIconPath;
        String name = mCustomItemName.getContent();
        String gender = mCustomItemGender.getContent();
        String phone = mCustomItemPhone.getContent();
        String address = mCustomItemAddress.getContent();
        String guardian1 = mCustomItemGuardian1.getContent();
        String guardian_phone1 = mCustomItemGuardianPhone1.getContent();
        String guardian2 = mCustomItemGuardian2.getContent();
        String guardian_phone2 = mCustomItemGuardianPhone2.getContent();
        String birthdayContent = mCustomItemBirthday.getContent();


        if (TextUtils.isEmpty(name)) {
            ToastUtils.SimpleToast(this, "学生姓名不能为空");
            return;
        }

        if (TextUtils.isEmpty(guardian1)) {
            ToastUtils.SimpleToast(this, "监护人姓名不能为空");
            return;
        }

        StudentBean studentBean = new StudentBean();
        studentBean.setIcon(icon);
        studentBean.setName(name);
        int genderInt = gender.equals("女") ? 0 : 1;
        studentBean.setGender(genderInt);
        studentBean.setPhone(phone);
        studentBean.setAddress(address);
        studentBean.setGuardian1(guardian1);
        studentBean.setGuardian1Phone(guardian_phone1);
        studentBean.setGuardian2(guardian2);
        studentBean.setGuardian2Phone(guardian_phone2);

        if(!TextUtils.isEmpty(birthdayContent)){
            try {
                mBirthdayLong = DateUtils.stringToLong(birthdayContent, null);
                studentBean.setBirthday(mBirthdayLong);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        String tId = ACache.get(this).getAsString(Constant.TEACHER_ID);
        studentBean.setTeacherId(tId);

        if (mIntentParam == 0) {
            //0:添加新学生
            if(TextUtils.isEmpty(mPicPath)){
                mPresenter.addStudent(studentBean);
            }else {
                mPresenter.addStudentAndPic(tId,name,genderInt,mBirthdayLong,phone,address,guardian1,guardian_phone1,guardian2,guardian_phone2,mPicPath);
            }
        } else {
            //修改学生信息
            studentBean.setId(mId);
            mPresenter.updateStudent(studentBean);
//            edit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_actionbar, menu);

        if (mIntentParam == 1) {

            MenuItem item = menu.findItem(R.id.menu_add);

            item.setIcon(new IconicsDrawable(this)
                    .icon(Ionicons.Icon.ion_android_delete)
                    .sizeDp(20)
                    .color(getResources().getColor(R.color.white)
                    ));

            item.setVisible(true);

            mItemEdit = menu.findItem(R.id.menu_setting);

            mItemEdit.setIcon(new IconicsDrawable(this)
                    .iconText("编辑")
                    .sizeDp(30)
                    .color(getResources().getColor(R.color.white)
                    ));

            mItemEdit.setVisible(true);

        } else if (mIntentParam == 2) {
            menu.findItem(R.id.menu_add).setVisible(false);


            mItemEdit = menu.findItem(R.id.menu_setting);

            mItemEdit.setIcon(new IconicsDrawable(this)
                    .iconText("编辑")
                    .sizeDp(30)
                    .color(getResources().getColor(R.color.white)
                    ));

            mItemEdit.setVisible(true);
        } else if (mIntentParam == 3) {
            menu.findItem(R.id.menu_setting).setVisible(false);
            MenuItem item = menu.findItem(R.id.menu_add);

            item.setIcon(new IconicsDrawable(this)
                    .icon(Ionicons.Icon.ion_android_delete)
                    .sizeDp(20)
                    .color(getResources().getColor(R.color.white)
                    ));

            item.setVisible(true);
        } else {
            menu.findItem(R.id.menu_add).setVisible(false);
            menu.findItem(R.id.menu_setting).setVisible(false);

        }
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_add:

                delete();
                break;
            case R.id.menu_setting:

                edit();
                break;

        }

        return false;

    }

    private void edit() {

        if (isEdit) {

            modifyStudent();

        } else {
            isEdit = true;
            mItemEdit.setIcon(new IconicsDrawable(this)
                    .iconText("完成")
                    .sizeDp(30)
                    .color(getResources().getColor(R.color.white)
                    ));
            mBtnSelectConfirm.setVisibility(View.VISIBLE);
        }
    }

    private void delete() {

        String title;

        if (mIntentParam == 1) {
            title = "是否确定移除此学生？";
        } else {
            title = "是否确定删除此学生？";
        }
        final SimpleConfirmDialog simpleConfirmDialog = new SimpleConfirmDialog(this, title);
        simpleConfirmDialog.setOnClickListener(new SimpleConfirmDialog.OnClickListener() {
            @Override
            public void onCancel() {
                simpleConfirmDialog.dismiss();
            }

            @Override
            public void onConfirm() {
                simpleConfirmDialog.dismiss();

                if (mIntentParam == 1) {
                    mPresenter.removeStudent(mId);
                } else {
                    mPresenter.deleteStudent(mId);
                }

            }
        });

        simpleConfirmDialog.show();
    }

}
