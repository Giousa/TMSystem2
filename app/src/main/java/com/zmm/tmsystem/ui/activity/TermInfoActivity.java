package com.zmm.tmsystem.ui.activity;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.zmm.tmsystem.R;
import com.zmm.tmsystem.bean.TermBean;
import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.common.utils.ACache;
import com.zmm.tmsystem.common.utils.ToastUtils;
import com.zmm.tmsystem.dagger.component.AppComponent;
import com.zmm.tmsystem.dagger.component.DaggerTermComponent;
import com.zmm.tmsystem.dagger.module.TermModule;
import com.zmm.tmsystem.mvp.presenter.TermPresenter;
import com.zmm.tmsystem.mvp.presenter.contract.TermContract;
import com.zmm.tmsystem.rx.RxBus;
import com.zmm.tmsystem.ui.widget.TitleBar;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/20
 * Time:上午12:18
 */

public class TermInfoActivity extends BaseActivity<TermPresenter> implements TermContract.TermView, View.OnClickListener, Toolbar.OnMenuItemClickListener {


    @BindView(R.id.et_dialog_title)
    TextView mEtDialogTitle;
    @BindView(R.id.et_dialog_year)
    TextView mEtDialogYear;
    @BindView(R.id.et_dialog_month)
    TextView mEtDialogMonth;
    @BindView(R.id.et_dialog_term)
    TextView mEtDialogTerm;
    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.root_view)
    LinearLayout mRootView;
    @BindView(R.id.btn_dialog_update)
    Button mBtnDialogUpdate;
    private TermBean mTermBean;
    private ACache mACache;
    private String mTeacherId;
    private boolean isChecked = false;

    @Override
    protected int setLayout() {
        return R.layout.activity_term_info;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerTermComponent.builder()
                .appComponent(appComponent)
                .termModule(new TermModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void init() {

        mACache = ACache.get(this);

        mTeacherId = mACache.getAsString(Constant.TEACHER_ID);
        TermBean termBean = (TermBean) mACache.getAsObject(Constant.TERM);
        mTermBean = (TermBean) getIntent().getSerializableExtra(Constant.TERM_CLICKED);


        if(termBean != null && termBean.getId() != null && mTermBean != null){
            if(mTermBean.getId().equals(termBean.getId())){
                isChecked = true;
            }
        }

        initToolBar();

        initEvent();

        initData();

    }

    private void initToolBar() {

        //这里一定要加上，否则menu不显示
        setSupportActionBar(mTitleBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mTitleBar.setCenterTitle("托管周期信息");
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


    private void initEvent() {
        mEtDialogYear.setOnClickListener(this);
        mEtDialogMonth.setOnClickListener(this);
        mEtDialogTerm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.et_dialog_year:
                mPresenter.insertContent(Constant.TYPE_TERM_YEAR, mRootView, mScreenWidth);
                break;
            case R.id.et_dialog_month:
                mPresenter.insertContent(Constant.TYPE_TERM_MONTH, mRootView, mScreenWidth);
                break;
            case R.id.et_dialog_term:
                mPresenter.insertContent(Constant.TYPE_TERM_TERM, mRootView, mScreenWidth);
                break;
        }
    }


    private void initData() {

        if (mTermBean != null) {

            mBtnDialogUpdate.setText("修改托管信息");

            String title = mTermBean.getTitle();
            String year = mTermBean.getYear() + "";
            String month = mTermBean.getMonth() + "";
            String term = mTermBean.getTerm();

            mEtDialogTitle.setText(title);
            mEtDialogYear.setText(year);
            mEtDialogMonth.setText(month);
            mEtDialogTerm.setText(term);

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
    public void insertContentSuccess(int type, String content) {

        switch (type) {

            case Constant.TYPE_TERM_YEAR:
                mEtDialogYear.setText(content);
                break;
            case Constant.TYPE_TERM_MONTH:
                mEtDialogMonth.setText(content);
                break;
            case Constant.TYPE_TERM_TERM:
                mEtDialogTerm.setText(content);
                break;
        }

        mEtDialogTitle.setText(mEtDialogYear.getText().toString() + mEtDialogTerm.getText().toString());
    }

    @Override
    public void createSuccess(TermBean termBean) {
        ToastUtils.SimpleToast(this,getResources().getString(R.string.childcare_add_success));
        RxBus.getDefault().post(Constant.UPDATE_TERM);
        finish();

    }

    @Override
    public void updateSuccess(TermBean termBean) {
        ToastUtils.SimpleToast(this, getResources().getString(R.string.childcare_update_success));
        RxBus.getDefault().post(Constant.UPDATE_TERM);
        if(isChecked){
            //需要修改主界面，选中的 托管周期 title
            mACache.put(Constant.TERM,termBean);
            RxBus.getDefault().post(Constant.UPDATE_TITLE);
        }
    }

    @Override
    public void deleteSuccess(String id) {


        TermBean termBean = (TermBean) mACache.getAsObject(Constant.TERM);
        if (id.equals(termBean.getId())) {
            mACache.put(Constant.TERM, new TermBean());
            RxBus.getDefault().post(Constant.UPDATE_TITLE);
        }
        RxBus.getDefault().post(Constant.UPDATE_TERM);
        finish();
    }

    @Override
    public void getAllTerms(List<TermBean> list) {

    }


    @OnClick(R.id.btn_dialog_update)
    public void onViewClicked() {

        String year = mEtDialogYear.getText().toString();
        String month = mEtDialogMonth.getText().toString();
        String term = mEtDialogTerm.getText().toString();
        String title = mEtDialogTitle.getText().toString();

        if(TextUtils.isEmpty(year)){
            ToastUtils.SimpleToast(this,"年份不能为空");
            return;
        }

        if(TextUtils.isEmpty(month)){
            ToastUtils.SimpleToast(this,"月份不能为空");
            return;
        }

        if(TextUtils.isEmpty(term)){
            ToastUtils.SimpleToast(this,"学期不能为空");
            return;
        }


        if(mTermBean != null){
            //更新

            mTermBean.setTitle(title);
            mTermBean.setYear(year);
            mTermBean.setMonth(month);
            mTermBean.setTerm(term);

            mPresenter.updateTerm(mTermBean);

        }else {
            //添加
            TermBean termBean = new TermBean();
            termBean.setTeacherId(mTeacherId);
            termBean.setTitle(title);
            termBean.setYear(year);
            termBean.setMonth(month);
            termBean.setTerm(term);

            mPresenter.createTerm(termBean);


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        if(mTermBean != null){

            getMenuInflater().inflate(R.menu.menu_actionbar, menu);
            menu.findItem(R.id.menu_setting).setVisible(false);
            MenuItem item = menu.findItem(R.id.menu_add);

            item.setIcon(new IconicsDrawable(this)
                    .icon(Ionicons.Icon.ion_android_delete)
                    .sizeDp(20)
                    .color(getResources().getColor(R.color.white)
                    ));

            item.setVisible(true);

        }
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        mPresenter.delete(mTermBean.getId());
        return false;
    }
}
