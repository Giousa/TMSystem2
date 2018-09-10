package com.zmm.tmsystem.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.zmm.tmsystem.R;
import com.zmm.tmsystem.bean.StudentBean;
import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.common.utils.ACache;
import com.zmm.tmsystem.common.utils.ToastUtils;
import com.zmm.tmsystem.dagger.component.AppComponent;
import com.zmm.tmsystem.dagger.component.DaggerStudentComponent;
import com.zmm.tmsystem.dagger.module.StudentModule;
import com.zmm.tmsystem.mvp.presenter.StudentPresenter;
import com.zmm.tmsystem.mvp.presenter.contract.StudentContract;
import com.zmm.tmsystem.rx.RxBus;
import com.zmm.tmsystem.ui.adapter.StudentAdapter;
import com.zmm.tmsystem.ui.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Description: 移除学生管理界面
 * Author:zhangmengmeng
 * Date:2018/6/20
 * Time:下午3:18
 */

public class StudentRemoveActivity extends BaseActivity<StudentPresenter> implements StudentContract.StudentView, Toolbar.OnMenuItemClickListener {

    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.root_view)
    LinearLayout mRootView;
//    @BindView(R.id.custom_button_title_view)
//    CustomButtonTitleView mCustomButtonTitleView;
    @BindView(R.id.btn_select_confirm)
    Button mBtnSelectConfirm;


    private StudentAdapter mStudentAdapter;
    private ACache mACache;
    private String mTId;
    private int mIntExtra;


    @Override
    protected int setLayout() {
        return R.layout.activity_student;
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

        mIntExtra = getIntent().getIntExtra(Constant.INTENT_PARAM, 1);
        mBtnSelectConfirm.setVisibility(View.GONE);

        initToolBar();

        initData();

    }


    private void initData() {

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mStudentAdapter = new StudentAdapter(this);
        mStudentAdapter.setFlag(false);
        mStudentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //条目点击，进入详情
                StudentBean studentBean = (StudentBean) adapter.getItem(position);
                Intent intent = new Intent(StudentRemoveActivity.this, StudentInfoActivity.class);
                //1: 可选学生管理，代表修改学生，这个时候需要initData数据，移除学生，有删除按钮，可修改
                intent.putExtra(Constant.INTENT_PARAM, 1);
                intent.putExtra(Constant.STUDENT_ID, studentBean.getId());
                startActivity(intent);
            }
        });

        mStudentAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                StudentBean studentBean = (StudentBean) adapter.getItem(position);
                mPresenter.returnStudent(studentBean.getId());


            }
        });

        mRecyclerView.setAdapter(mStudentAdapter);

        mACache = ACache.get(this);
        mTId = mACache.getAsString(Constant.TEACHER_ID);

        mPresenter.queryRemoveStudents(mTId);
    }

    private void initToolBar() {
        //这里一定要加上，否则menu不显示
        setSupportActionBar(mTitleBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mTitleBar.setCenterTitle("移除学生管理");
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
    public void inputSuccess(int type, String content) {

    }

    @Override
    public void addSuccess(StudentBean studentBean) {

    }

    @Override
    public void addChildcareStudentSuccess(String msg) {

    }

    @Override
    public void updateSuccess() {

    }

    @Override
    public void deleteStudent(String s) {
        ToastUtils.SimpleToast(this, s);
        requestNewData();


    }


    @Override
    public void querySuccess(List<StudentBean> studentBeans) {
        mStudentAdapter.setNewData(studentBeans);
    }

    @Override
    public void queryStudent(StudentBean studentBean) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionbar, menu);
        menu.findItem(R.id.menu_setting).setVisible(false);
        menu.findItem(R.id.menu_add).setVisible(false);

        return true;
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {

        return false;
    }


    /**
     * 请求新数据
     */
    private void requestNewData() {

        mPresenter.queryRemoveStudents(mTId);

    }
}
