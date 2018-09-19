package com.zmm.tmsystem.ui.fragment;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.data.PieEntry;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.zmm.tmsystem.R;
import com.zmm.tmsystem.bean.StatisticsBean;
import com.zmm.tmsystem.bean.TeacherBean;
import com.zmm.tmsystem.bean.TermBean;
import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.common.utils.ACache;
import com.zmm.tmsystem.common.utils.CheckUtils;
import com.zmm.tmsystem.dagger.component.AppComponent;
import com.zmm.tmsystem.dagger.component.DaggerHomeComponent;
import com.zmm.tmsystem.dagger.module.HomeModule;
import com.zmm.tmsystem.mvp.presenter.HomePresenter;
import com.zmm.tmsystem.mvp.presenter.contract.HomeContract;
import com.zmm.tmsystem.rx.RxBus;
import com.zmm.tmsystem.ui.activity.TeacherInfoActivity;
import com.zmm.tmsystem.ui.widget.CustomMPChartPieView;
import com.zmm.tmsystem.ui.widget.GlideCircleTransform;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/15
 * Time:上午10:02
 */

public class HomeFragment extends ProgressFragment<HomePresenter> implements HomeContract.HomeView {


    @BindView(R.id.iv_head_icon)
    ImageView mIvHeadIcon;
    @BindView(R.id.tv_head_name)
    TextView mTvHeadName;
    @BindView(R.id.iv_head_gender)
    ImageView mIvHeadGender;
    @BindView(R.id.tv_head_childcare)
    TextView mTvHeadChildcare;
    @BindView(R.id.tv_head_school_name)
    TextView mTvHeadSchoolName;
    @BindView(R.id.tv_head_grade_name)
    TextView mTvHeadGradeName;
    @BindView(R.id.tv_head_course_name)
    TextView mTvHeadCourseName;

    @BindView(R.id.custom_pie_view1)
    CustomMPChartPieView customPieView1;
    @BindView(R.id.custom_pie_view2)
    CustomMPChartPieView customPieView2;
    @BindView(R.id.custom_pie_view3)
    CustomMPChartPieView customPieView3;

    @BindView(R.id.tv_childcare_name)
    TextView tvChildcareName;
    @BindView(R.id.tv_num_total)
    TextView tvNumTotal;
    @BindView(R.id.tv_num_male)
    TextView tvNumMale;
    @BindView(R.id.tv_num_female)
    TextView tvNumFemale;
    @BindView(R.id.ll_show)
    LinearLayout llShow;
    @BindView(R.id.ll_chart_show)
    LinearLayout llChartShow;
    @BindView(R.id.tv_num_primary)
    TextView tvNumPrimary;
    @BindView(R.id.tv_num_middle)
    TextView tvNumMiddle;


    private Context mContext;
    private ACache mACache;

    @Override
    protected int setLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void setupAcitivtyComponent(AppComponent appComponent) {
        DaggerHomeComponent.builder()
                .appComponent(appComponent)
                .homeModule(new HomeModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void init() {

        mContext = getActivity();

        mACache = ACache.get(mContext);

        //展示教师信息
        TeacherBean teacherBean = (TeacherBean) mACache.getAsObject(Constant.TEACHER);

        if (teacherBean != null) {
            showTeacherInfo(teacherBean);
        }

        //展示统计报表
        TermBean termBean = (TermBean) mACache.getAsObject(Constant.TERM);
        if(termBean != null){
            mPresenter.getStatisticsInfo(termBean.getId());
        }else {
            llShow.setVisibility(View.GONE);
            llChartShow.setVisibility(View.GONE);
        }


//        operateBus();

    }

    @Override
    protected void onEmptyViewClick() {
        super.onEmptyViewClick();
        mPresenter.getTeacherById(mACache.getAsString(Constant.TEACHER_ID));
    }

    public void showTeacherInfo(TeacherBean teacherBean) {

        String name = teacherBean.getName();
        String icon = teacherBean.getIcon();
        String childcareName = teacherBean.getChildcareName();
        String schoolName = teacherBean.getSchoolName();
        String gradeName = teacherBean.getGradeName();
        String courseName = teacherBean.getCourseName();

        int gender = teacherBean.getGender();

        if (!TextUtils.isEmpty(name)) {
            mTvHeadName.setText(name);
        }

        if (!TextUtils.isEmpty(childcareName)) {
            mTvHeadChildcare.setText(childcareName);
        }

        if (!TextUtils.isEmpty(schoolName)) {
            mTvHeadSchoolName.setText(schoolName);
        }

        if (!TextUtils.isEmpty(gradeName)) {
            mTvHeadGradeName.setText(gradeName);
        }

        if (!TextUtils.isEmpty(courseName)) {
            mTvHeadCourseName.setText(courseName);
        }

        if (!TextUtils.isEmpty(icon)) {

            Glide.with(mContext)
                    .load(Constant.BASE_IMG_URL + icon)
                    .transform(new GlideCircleTransform(mContext))
                    .error(new IconicsDrawable(mContext)
                            .icon(Ionicons.Icon.ion_android_contact)
                            .color(getResources().getColor(R.color.md_blue_500)
                            ))
                    .into(mIvHeadIcon);

        } else {
            mIvHeadIcon.setImageDrawable(new IconicsDrawable(mApplication)
                    .icon(Ionicons.Icon.ion_android_contact)
                    .color(getResources().getColor(R.color.md_blue_500)));
        }


        if (gender == 0) {
            mIvHeadGender.setImageDrawable(new IconicsDrawable(mApplication)
                    .icon(Ionicons.Icon.ion_female)
                    .color(getResources().getColor(R.color.colorAccent)));
        } else {
            mIvHeadGender.setImageDrawable(new IconicsDrawable(mApplication)
                    .icon(Ionicons.Icon.ion_male)
                    .color(getResources().getColor(R.color.colorPrimary)));
        }


    }


    @Override
    public void statistics(StatisticsBean statisticsBean) {


        System.out.println("statisticsBean = " + statisticsBean);


        //显示数据
        if(llShow != null){
            llShow.setVisibility(View.VISIBLE);
        }

        tvChildcareName.setText("托管周期：" + statisticsBean.getTitle());
        tvNumTotal.setText("总人数:  " + statisticsBean.getTotal() + "人");
        tvNumMale.setText("男生:  " + statisticsBean.getMale() + "人");
        tvNumFemale.setText("女生:  " + statisticsBean.getFemale() + "人");
        tvNumPrimary.setText("小学:  "+statisticsBean.getPrimary()+"人");
        tvNumMiddle.setText("初中:  "+statisticsBean.getMiddle()+"人");



        int total = statisticsBean.getTotal();
        if(total == 0){
            return;
        }

        //显示统计图表
        llChartShow.setVisibility(View.VISIBLE);

        ArrayList<PieEntry> entries = new ArrayList();
        entries.add(new PieEntry(statisticsBean.getMale(), "男"));
        entries.add(new PieEntry(statisticsBean.getFemale(), "女"));

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getResources().getColor(R.color.md_indigo_A700));
        colors.add(getResources().getColor(R.color.md_pink_400));

        customPieView1.setData(entries, colors, "性别");


        ArrayList<PieEntry> entries2 = new ArrayList();
        entries2.add(new PieEntry(statisticsBean.getMiddle(), "初中"));
        entries2.add(new PieEntry(statisticsBean.getPrimary(), "小学"));

        ArrayList<Integer> colors2 = new ArrayList<>();
        colors2.add(getResources().getColor(R.color.md_deep_orange_600));
        colors2.add(getResources().getColor(R.color.md_cyan_600));

        customPieView2.setData(entries2, colors2, "年级");


        ArrayList<PieEntry> entries3 = new ArrayList();
        ArrayList<Integer> colors3 = new ArrayList<>();

        if (statisticsBean.getRongguang() != 0) {
            entries3.add(new PieEntry(statisticsBean.getRongguang(), "荣光:"+statisticsBean.getRongguang()+"人"));
            colors3.add(getResources().getColor(R.color.md_indigo_A700));
        }

        if (statisticsBean.getShiyan() != 0) {
            entries3.add(new PieEntry(statisticsBean.getShiyan(), "实验:"+statisticsBean.getShiyan()+"人"));
            colors3.add(getResources().getColor(R.color.md_deep_orange_600));
        }

        if (statisticsBean.getWuchu() != 0) {
            entries3.add(new PieEntry(statisticsBean.getWuchu(), "五初:"+statisticsBean.getWuchu()+"人"));
            colors3.add(getResources().getColor(R.color.md_cyan_600));
        }

        if (statisticsBean.getZhucai() != 0) {
            entries3.add(new PieEntry(statisticsBean.getZhucai(), "铸才:"+statisticsBean.getZhucai()+"人"));
            colors3.add(getResources().getColor(R.color.md_deep_purple_400));
        }


        customPieView3.setData(entries3, colors3, "学校");

    }


    @OnClick({R.id.iv_head_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_head_icon:

                startActivity(TeacherInfoActivity.class);

                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        TermBean termBean = (TermBean) mACache.getAsObject(Constant.TERM);
        if(termBean != null){
            mPresenter.getStatisticsInfo(termBean.getId());

        }else {
            llShow.setVisibility(View.GONE);
            llChartShow.setVisibility(View.GONE);
        }

        TeacherBean teacherBean = (TeacherBean) mACache.getAsObject(Constant.TEACHER);
        mPresenter.getTeacherById(teacherBean.getId());

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

                        System.out.println("Home 消息："+s);
                        if(!TextUtils.isEmpty(s)){

                            if(s.equals(Constant.UPDATE_TITLE) || s.equals(Constant.UPDATE_STUDENT_CHILDCARE) || s.equals(Constant.UPDATE_STUDENT)){
                                TermBean termBean = (TermBean) mACache.getAsObject(Constant.TERM);
                                if(termBean != null){
                                    mPresenter.getStatisticsInfo(termBean.getId());
                                }else {
                                    llShow.setVisibility(View.GONE);
                                    llChartShow.setVisibility(View.GONE);
                                }
                            }else if(s.equals(Constant.UPDATE_TEACHER)){
                                TeacherBean teacherBean = (TeacherBean) mACache.getAsObject(Constant.TEACHER);
//                                showTeacherInfo(teacherBean);
                                mPresenter.getTeacherById(teacherBean.getId());
                            }

                        }
                    }
                });
    }

}
