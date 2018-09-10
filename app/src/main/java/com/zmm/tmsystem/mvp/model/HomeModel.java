package com.zmm.tmsystem.mvp.model;

import com.zmm.tmsystem.bean.BaseBean;
import com.zmm.tmsystem.bean.StatisticsBean;
import com.zmm.tmsystem.bean.TeacherBean;
import com.zmm.tmsystem.http.ApiService;
import com.zmm.tmsystem.mvp.presenter.contract.HomeContract;

import java.io.File;

import io.reactivex.Observable;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/14
 * Time:下午11:47
 */

public class HomeModel implements HomeContract.IHomeModel {

    private ApiService mApiService;

    public HomeModel(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Observable<BaseBean<TeacherBean>> getTeacherById(String id) {

        Observable<BaseBean<TeacherBean>> observable = mApiService.getTeacherById(id);
        return observable;
    }

    @Override
    public Observable<BaseBean<String>> signInfo(String tId) {

        Observable<BaseBean<String>> observable = mApiService.signInfo(tId);

        return observable;
    }

    @Override
    public Observable<BaseBean<String>> sign(String tId) {

        Observable<BaseBean<String>> observable = mApiService.sign(tId);

        return observable;
    }

    @Override
    public Observable<BaseBean<StatisticsBean>> getStatistics(String id) {
        return mApiService.getStatistics(id);
    }
}
