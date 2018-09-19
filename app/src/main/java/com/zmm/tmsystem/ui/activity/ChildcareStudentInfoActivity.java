package com.zmm.tmsystem.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.zmm.tmsystem.R;
import com.zmm.tmsystem.bean.ChildcareStudentBean;
import com.zmm.tmsystem.bean.MoneyBean;
import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.common.utils.ToastUtils;
import com.zmm.tmsystem.dagger.component.AppComponent;
import com.zmm.tmsystem.dagger.component.DaggerChildcareStudentComponent;
import com.zmm.tmsystem.dagger.module.ChildcareStudentModule;
import com.zmm.tmsystem.mvp.presenter.ChildcareStudentPresenter;
import com.zmm.tmsystem.mvp.presenter.contract.ChildcareStudentContract;
import com.zmm.tmsystem.rx.RxBus;
import com.zmm.tmsystem.ui.widget.CustomInfoItemView;
import com.zmm.tmsystem.ui.widget.SimpleConfirmDialog;
import com.zmm.tmsystem.ui.widget.SimplePhoneDialog;
import com.zmm.tmsystem.ui.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Description:托管学生资料
 * Author:zhangmengmeng
 * Date:2018/7/3
 * Time:上午11:34
 */

public class ChildcareStudentInfoActivity extends BaseActivity<ChildcareStudentPresenter> implements ChildcareStudentContract.ChildcareStudentView, Toolbar.OnMenuItemClickListener, CustomInfoItemView.OnItemClickListener {

    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.custom_item_icon)
    CustomInfoItemView mCustomItemIcon;
    @BindView(R.id.custom_item_name)
    CustomInfoItemView mCustomItemName;
    @BindView(R.id.custom_item_school)
    CustomInfoItemView mCustomItemSchool;
    @BindView(R.id.custom_item_grade)
    CustomInfoItemView mCustomItemGrade;
    @BindView(R.id.custom_item_teacher)
    CustomInfoItemView mCustomItemTeacher;
    @BindView(R.id.custom_item_teacher_phone)
    CustomInfoItemView mCustomItemTeacherPhone;
    @BindView(R.id.custom_item_info)
    CustomInfoItemView mCustomItemInfo;
    @BindView(R.id.custom_item_certificates)
    CustomInfoItemView mCustomItemCertificates;
    @BindView(R.id.custom_item_pay)
    CustomInfoItemView mCustomItemPay;
    @BindView(R.id.root_view)
    LinearLayout mRootView;
    @BindView(R.id.custom_item_score)
    CustomInfoItemView mCustomItemScore;

    private ChildcareStudentBean mChildcareStudentBean;
    private MenuItem mItemEdit;
    private boolean isEdit = false;
    private String moneyId;
    private MoneyBean mMoneyBean;

    @Override
    protected int setLayout() {
        return R.layout.activity_student_childcare;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerChildcareStudentComponent.builder()
                .appComponent(appComponent)
                .childcareStudentModule(new ChildcareStudentModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void init() {

        mChildcareStudentBean = (ChildcareStudentBean) getIntent().getSerializableExtra(Constant.CHILDCARE_STUDENT);

        initToolBar();

        initView();

        initData(mChildcareStudentBean);

//        operateBus();
    }



    @Override
    protected void onResume() {
        super.onResume();


        initMoneyData();
    }

    /**
     * 请求money
     */
    private void initMoneyData() {

        mPresenter.getMoneyByStudentId(mChildcareStudentBean.getId());

        mPresenter.findChildcareStudentById(mChildcareStudentBean.getId());

    }

    private void initToolBar() {

        //这里一定要加上，否则menu不显示
        setSupportActionBar(mTitleBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mTitleBar.setCenterTitle("托管学生资料");
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
        mCustomItemSchool.setOnItemClickListener(this, Constant.TYPE_STUDENT_SCHOOL);
        mCustomItemGrade.setOnItemClickListener(this, Constant.TYPE_STUDENT_GRADE);
        mCustomItemTeacher.setOnItemClickListener(this, Constant.TYPE_STUDENT_TEACHER);
        mCustomItemTeacherPhone.setOnItemClickListener(this, Constant.TYPE_STUDENT_TEACHER_PHONE);
        mCustomItemInfo.setOnItemClickListener(this, Constant.TYPE_STUDENT_INFO);
        mCustomItemCertificates.setOnItemClickListener(this, Constant.TYPE_STUDENT_CERTIFICATES);
        mCustomItemPay.setOnItemClickListener(this, Constant.TYPE_STUDENT_PAY);
        mCustomItemScore.setOnItemClickListener(this,Constant.TYPE_STUDENT_SCORE);

    }

    private void initData(ChildcareStudentBean childcareStudentBean) {

        if (mCustomItemIcon == null) {
            return;
        }

        String icon = childcareStudentBean.getStudent().getIcon();
        String name = childcareStudentBean.getStudent().getName();
        String school = childcareStudentBean.getSchool();
        String grade = childcareStudentBean.getGrade();
        String teacher = childcareStudentBean.getTeacher();
        String teacherPhone = childcareStudentBean.getTeacherPhone();

        mCustomItemIcon.setIcon(icon,childcareStudentBean.getStudent().getGender());
        mCustomItemName.setContent(name);
        mCustomItemSchool.setContent(school);
        mCustomItemGrade.setContent(grade);
        mCustomItemTeacher.setContent(teacher);
        mCustomItemTeacherPhone.setContent(teacherPhone);


    }


    @Override
    public void showLoading() {

    }

    @Override
    public void showError(String msg) {
        ToastUtils.SimpleToast(this, msg);
    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void querySuccess(List<ChildcareStudentBean> childcareStudentBeans) {

    }

    @Override
    public void updateSuccess(ChildcareStudentBean childcareStudentBean) {

        mChildcareStudentBean = childcareStudentBean;

        initData(childcareStudentBean);
        RxBus.getDefault().post(Constant.UPDATE_STUDENT_CHILDCARE);
    }

    @Override
    public void deleteSuccess() {
        ToastUtils.SimpleToast(this, getResources().getString(R.string.student_delete_success));
        RxBus.getDefault().post(Constant.UPDATE_STUDENT_CHILDCARE);
        finish();
    }

    @Override
    public void queryMoney(MoneyBean moneyBean) {
        moneyId = moneyBean.getId();
        mMoneyBean = moneyBean;
        mCustomItemPay.setContent("当前余额: ￥"+moneyBean.getSurplus());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionbar, menu);
//        menu.findItem(R.id.menu_setting).setVisible(false);
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
            isEdit = false;
            mItemEdit.setIcon(new IconicsDrawable(this)
                    .iconText("编辑")
                    .sizeDp(30)
                    .color(getResources().getColor(R.color.white)
                    ));

        } else {
            isEdit = true;
            mItemEdit.setIcon(new IconicsDrawable(this)
                    .iconText("完成")
                    .sizeDp(30)
                    .color(getResources().getColor(R.color.white)
                    ));
        }
    }

    private void delete() {

        if(mMoneyBean != null && mMoneyBean.getSurplus() > 0){
            ToastUtils.SimpleToast(this,"当前学生仍有余额，无法删除");
            return;
        }

        final SimpleConfirmDialog simpleConfirmDialog = new SimpleConfirmDialog(this, "是否确定删除此学生？");
        simpleConfirmDialog.setOnClickListener(new SimpleConfirmDialog.OnClickListener() {
            @Override
            public void onCancel() {
                simpleConfirmDialog.dismiss();
            }

            @Override
            public void onConfirm() {
                simpleConfirmDialog.dismiss();
                mPresenter.deleteChildcareStudent(mChildcareStudentBean.getId());

            }
        });

        simpleConfirmDialog.show();
    }

    @Override
    public void itemClick(int type, String name) {

        if (type == Constant.TYPE_STUDENT_INFO) {

            Intent intentInfo = new Intent(this, StudentInfoActivity.class);
            intentInfo.putExtra(Constant.INTENT_PARAM, 2);
            intentInfo.putExtra(Constant.STUDENT_ID, mChildcareStudentBean.getStudent().getId());
            startActivity(intentInfo);

        } else if (type == Constant.TYPE_STUDENT_ICON) {

            uloadIcon();

        } else if (type == Constant.TYPE_STUDENT_CERTIFICATES) {
            //荣誉证书
            Intent intent = new Intent(this,CertificateActivity.class);
            intent.putExtra(Constant.CHILDCARE_STUDENT_ID,mChildcareStudentBean.getId());
            startActivity(intent);
        } else if (type == Constant.TYPE_STUDENT_PAY) {
            //消费界面
            if(TextUtils.isEmpty(moneyId)){
                ToastUtils.SimpleToast(this,"服务器繁忙，请稍后再试");
            }else {
                Intent intent = new Intent(this,SpendActivity.class);
                intent.putExtra(Constant.MONEY_ID,moneyId);
                startActivity(intent);
            }

        } else if (type == Constant.TYPE_STUDENT_SCORE) {

            String grade = mChildcareStudentBean.getGrade();
            if(TextUtils.isEmpty(grade)){
                ToastUtils.SimpleToast(this,"请选择年级");
            }else {
                Intent intent = new Intent(this,ScoreActivity.class);
                intent.putExtra(Constant.CHILDCARE_STUDENT_ID,mChildcareStudentBean.getId());
                intent.putExtra(Constant.CHILDCARE_STUDENT_GRADE_LEVEL,mChildcareStudentBean.getGradeLevel());
                startActivity(intent);
            }

        } else {

            if (isEdit) {
                mPresenter.updateChildcareStudentData(type, name, mRootView, mScreenWidth, mChildcareStudentBean);
            } else {
                switch (type) {

                    case Constant.TYPE_STUDENT_TEACHER:
                        String teacherName = mCustomItemTeacher.getContent();
                        if(TextUtils.isEmpty(teacherName)){
                            ToastUtils.SimpleToast(this,"请编辑输入班主任姓名");
                            return;
                        }
                        phone();

                        break;

                    case Constant.TYPE_STUDENT_TEACHER_PHONE:
                        String teacherNum = mCustomItemTeacherPhone.getContent();

                        if(TextUtils.isEmpty(teacherNum)){
                            ToastUtils.SimpleToast(this,"请编辑输入班主任联系电话");
                            return;
                        }
                        phone();
                        break;
                }
            }
        }


    }


    private void phone(){
        String teacherName = mCustomItemTeacher.getContent();
        String teacherNum = mCustomItemTeacherPhone.getContent();
        if(TextUtils.isEmpty(teacherName)){
            ToastUtils.SimpleToast(this,"请编辑输入班主任姓名");
            return;
        }

        if(TextUtils.isEmpty(teacherNum)){
            ToastUtils.SimpleToast(this,"请编辑输入班主任联系电话");
            return;
        }
        final SimplePhoneDialog simplePhoneDialog = new SimplePhoneDialog(this,teacherName,teacherNum);
        simplePhoneDialog.setOnClickListener(new SimplePhoneDialog.OnClickListener() {
            @Override
            public void onCancel() {
                simplePhoneDialog.dismiss();
            }

            @Override
            public void onConfirm(String num) {
                simplePhoneDialog.dismiss();
                //拨号界面
                Intent intent = new Intent(Intent.ACTION_DIAL);
                //直接拨打
//                                Intent intent = new Intent(Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:" + num);
                intent.setData(data);
                startActivity(intent);
            }
        });

        simplePhoneDialog.show();
    }

    /**
     * 上传头像
     */
    private void uloadIcon() {

        //可以剪切
        ImagePicker.getInstance().setCrop(true);
        //头像单选
        ImagePicker.getInstance().setMultiMode(false);

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
                    System.out.println("选择图片：" + images.get(0).path);
                    mPresenter.uploadStudentPic(mChildcareStudentBean, images.get(0).path);
                }

            } else {
                System.out.println("没有数据");
            }
        }
    }

    /**
     * RxBus  这里是更新选中的托管项目
     */
    private void operateBus() {
        RxBus.getDefault().toObservable()
                .map(new Function<Object, String>() {
                    @Override
                    public String apply(Object o) throws Exception {
                        return (String) o;
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (!TextUtils.isEmpty(s)) {

                            if (s.equals(Constant.UPDATE_STUDENT)) {
                                //学生个人信息变动，更新数据
                                mPresenter.findChildcareStudentById(mChildcareStudentBean.getId());
                            }

                        }
                    }
                });
    }


}
