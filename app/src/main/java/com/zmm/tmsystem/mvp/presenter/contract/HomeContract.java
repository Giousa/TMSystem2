package com.zmm.tmsystem.mvp.presenter.contract;

import com.zmm.tmsystem.bean.BaseBean;
import com.zmm.tmsystem.bean.StatisticsBean;
import com.zmm.tmsystem.bean.TeacherBean;
import com.zmm.tmsystem.mvp.view.BaseView;

import java.io.File;

import io.reactivex.Observable;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/14
 * Time:下午11:13
 */

public interface HomeContract {


    interface IHomeModel {

        Observable<BaseBean<TeacherBean>> getTeacherById(String id);

        Observable<BaseBean<String>> signInfo(String tId);

        Observable<BaseBean<String>> sign(String tId);

        Observable<BaseBean<StatisticsBean>> getStatistics(String id);


    }

    interface HomeView extends BaseView{

        void statistics(StatisticsBean statisticsBean);
        void showTeacherInfo(TeacherBean teacherBean);
    }
}
