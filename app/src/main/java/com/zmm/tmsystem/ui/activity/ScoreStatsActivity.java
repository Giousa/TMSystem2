package com.zmm.tmsystem.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.data.Entry;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.zmm.tmsystem.R;
import com.zmm.tmsystem.bean.ScoreBean;
import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.dagger.component.AppComponent;
import com.zmm.tmsystem.dagger.component.DaggerScoreComponent;
import com.zmm.tmsystem.dagger.module.ScoreModule;
import com.zmm.tmsystem.mvp.presenter.ScorePresenter;
import com.zmm.tmsystem.mvp.presenter.contract.ScoreContract;
import com.zmm.tmsystem.ui.widget.CustomMPChartLineView;
import com.zmm.tmsystem.ui.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/9/5
 * Email:65489469@qq.com
 */
public class ScoreStatsActivity extends BaseActivity<ScorePresenter> implements ScoreContract.ScoreView {

    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.empty)
    RelativeLayout mEmpty;
    @BindView(R.id.custom_line_chinese)
    CustomMPChartLineView customLineChinese;
    @BindView(R.id.custom_line_math)
    CustomMPChartLineView customLineMath;
    @BindView(R.id.custom_line_english)
    CustomMPChartLineView customLineEnglish;
    @BindView(R.id.custom_line_physics)
    CustomMPChartLineView customLinePhysics;
    @BindView(R.id.custom_line_chemistry)
    CustomMPChartLineView customLineChemistry;

    private String mChildcareStudentId;
    private int mGradeLevel;

    @Override
    protected int setLayout() {
        return R.layout.activity_score_stats;
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

        if (mGradeLevel >= 7) {
            customLinePhysics.setVisibility(View.VISIBLE);
            customLineChemistry.setVisibility(View.VISIBLE);
        }

        initToolBar();

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

    }

    @Override
    public void requestSuccess(String msg) {

    }

    @Override
    public void querySuccess(ScoreBean scoreBean) {

    }

    @Override
    public void queryAllScores(List<ScoreBean> scoreBeans) {

        if (mEmpty != null) {
            if (scoreBeans != null && scoreBeans.size() > 0) {
                mEmpty.setVisibility(View.GONE);
                parseScoreList(scoreBeans);
            } else {
                mEmpty.setVisibility(View.VISIBLE);
            }
        }


    }

    /**
     * 解析数据
     *
     * @param scoreBeans
     */
    private void parseScoreList(List<ScoreBean> scoreBeans) {
        ArrayList<Entry> entriesChinese = new ArrayList<>();
        ArrayList<Entry> entriesMath = new ArrayList<>();
        ArrayList<Entry> entriesEnglish = new ArrayList<>();
        ArrayList<Entry> entriesPhysics = new ArrayList<>();
        ArrayList<Entry> entriesChemistry = new ArrayList<>();
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        int e = 0;

        int minChinese = 150;
        int minMath = 150;
        int minEnglish = 150;
        int minPhysics = 150;
        int minChemistry = 150;

        for (ScoreBean scoreBean : scoreBeans) {
            int chinese = scoreBean.getChinese();
            int maths = scoreBean.getMaths();
            int english = scoreBean.getEnglish();
            int physics = scoreBean.getPhysics();
            int chemistry = scoreBean.getChemistry();


            if (chinese != 0) {
                if(chinese < minChinese){
                    minChinese = chinese;
                }
                entriesChinese.add(new Entry(a, chinese));
                a++;
            }

            if (maths != 0) {
                if(maths < minMath){
                    minMath = maths;
                }
                entriesMath.add(new Entry(b, maths));
                b++;
            }

            if (english != 0) {
                if(english < minEnglish){
                    minEnglish = english;
                }
                entriesEnglish.add(new Entry(c, english));
                c++;
            }

            if (physics != 0) {
                if(physics < minPhysics){
                    minPhysics = physics;
                }
                entriesPhysics.add(new Entry(d, physics));
                d++;
            }

            if (chemistry != 0) {
                if(chemistry < minChemistry){
                    minChemistry = chemistry;
                }
                entriesChemistry.add(new Entry(e, chemistry));
                e++;
            }

        }

        if(entriesChinese.size() == 0){
            customLineChinese.setVisibility(View.GONE);
        }else {
            customLineChinese.setChartData(entriesChinese, minChinese,"语文",mGradeLevel);

        }

        if(entriesMath.size() == 0){
            customLineMath.setVisibility(View.GONE);

        }else {
            customLineMath.setChartData(entriesMath, minMath,"数学",mGradeLevel);

        }

        if(entriesEnglish.size() == 0){
            customLineEnglish.setVisibility(View.GONE);

        }else {
            customLineEnglish.setChartData(entriesEnglish, minEnglish,"英语",mGradeLevel);


        }



        if (mGradeLevel >= 7) {

            if(entriesPhysics.size() > 0){
                customLinePhysics.setChartData(entriesPhysics, minPhysics,"物理",mGradeLevel);
            }

            if(entriesChemistry.size() > 0){
                customLineChemistry.setChartData(entriesChemistry, minChemistry,"化学",mGradeLevel);
            }
        }

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
