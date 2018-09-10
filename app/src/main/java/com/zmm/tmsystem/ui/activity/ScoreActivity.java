package com.zmm.tmsystem.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.zmm.tmsystem.R;
import com.zmm.tmsystem.bean.ScoreBean;
import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.common.utils.ToastUtils;
import com.zmm.tmsystem.dagger.component.AppComponent;
import com.zmm.tmsystem.dagger.component.DaggerScoreComponent;
import com.zmm.tmsystem.dagger.module.ScoreModule;
import com.zmm.tmsystem.mvp.presenter.ScorePresenter;
import com.zmm.tmsystem.mvp.presenter.contract.ScoreContract;
import com.zmm.tmsystem.ui.adapter.ScoreAdapter;
import com.zmm.tmsystem.ui.adapter.SpendingAdapter;
import com.zmm.tmsystem.ui.widget.TitleBar;

import java.util.List;

import butterknife.BindView;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/9/5
 * Email:65489469@qq.com
 */
public class ScoreActivity extends BaseActivity<ScorePresenter> implements Toolbar.OnMenuItemClickListener,ScoreContract.ScoreView {

    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.empty)
    RelativeLayout mEmpty;
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;

    private MenuItem mMenuItemAdd;
    private String mChildcareStudentId;
    private int mGradeLevel;
    private ScoreAdapter mScoreAdapter;
    private MenuItem mMenuItemStats;
    private List<ScoreBean> mScoreBeans;

    @Override
    protected int setLayout() {
        return R.layout.activity_score;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerScoreComponent.builder()
                .appComponent(appComponent)
                .scoreModule(new ScoreModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void init() {

        mChildcareStudentId = this.getIntent().getStringExtra(Constant.CHILDCARE_STUDENT_ID);
        mGradeLevel = this.getIntent().getIntExtra(Constant.CHILDCARE_STUDENT_GRADE_LEVEL, 0);

        initToolBar();

        initListData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.queryAllScores(mChildcareStudentId);

    }

    private void initToolBar() {
        //这里一定要加上，否则menu不显示
        setSupportActionBar(mTitleBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mTitleBar.setCenterTitle("成绩单");
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

        mScoreAdapter = new ScoreAdapter(mGradeLevel);

        mScoreAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(ScoreActivity.this,ScoreInfoActivity.class);
                intent.putExtra(Constant.CHILDCARE_STUDENT_ID,mChildcareStudentId);
                intent.putExtra(Constant.CHILDCARE_STUDENT_GRADE_LEVEL,mGradeLevel);
                intent.putExtra(Constant.SCORE, mScoreAdapter.getData().get(position));
                startActivity(intent);

            }
        });

        mRecyclerView.setAdapter(mScoreAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionbar, menu);

        mMenuItemStats = menu.findItem(R.id.menu_add);

        mMenuItemStats.setIcon(new IconicsDrawable(this)
                .icon(Ionicons.Icon.ion_stats_bars)
                .sizeDp(20)
                .color(getResources().getColor(R.color.white)
                ));

        mMenuItemStats.setVisible(true);

        mMenuItemAdd = menu.findItem(R.id.menu_setting);

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

        switch (item.getItemId()){
            case R.id.menu_add:
                showStats();
                break;

            case R.id.menu_setting:
                addScore();
                break;


        }



        return false;
    }

    /**
     * 展示折线图
     */
    private void showStats() {
        Intent intent = new Intent(this,ScoreStatsActivity.class);
        intent.putExtra(Constant.CHILDCARE_STUDENT_ID,mChildcareStudentId);
        intent.putExtra(Constant.CHILDCARE_STUDENT_GRADE_LEVEL,mGradeLevel);
        startActivity(intent);
    }

    /**
     * 添加成绩
     */
    private void addScore() {
        Intent intent = new Intent(this,ScoreInfoActivity.class);
        intent.putExtra(Constant.CHILDCARE_STUDENT_ID,mChildcareStudentId);
        intent.putExtra(Constant.CHILDCARE_STUDENT_GRADE_LEVEL,mGradeLevel);
        startActivity(intent);
    }

    @Override
    public void requestSuccess(String msg) {

    }

    @Override
    public void querySuccess(ScoreBean scoreBean) {

    }

    @Override
    public void queryAllScores(List<ScoreBean> scoreBeans) {

        mScoreBeans = scoreBeans;

        if(scoreBeans != null && scoreBeans.size() > 0){
            mEmpty.setVisibility(View.GONE);
        }else {
            mEmpty.setVisibility(View.VISIBLE);
        }

        mScoreAdapter.setNewData(scoreBeans);
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
}
