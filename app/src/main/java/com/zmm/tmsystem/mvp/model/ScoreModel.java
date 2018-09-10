package com.zmm.tmsystem.mvp.model;

import com.zmm.tmsystem.bean.BaseBean;
import com.zmm.tmsystem.bean.ScoreBean;
import com.zmm.tmsystem.http.ApiService;
import com.zmm.tmsystem.mvp.presenter.contract.ScoreContract;

import java.util.List;

import io.reactivex.Observable;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/9/6
 * Email:65489469@qq.com
 */
public class ScoreModel implements ScoreContract.IScoreModel{

    private ApiService mApiService;

    public ScoreModel(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Observable<BaseBean<List<ScoreBean>>> queryAllScores(String studentId) {
        return mApiService.queryAllScores(studentId);
    }

    @Override
    public Observable<BaseBean<ScoreBean>> queryScoreById(String id) {
        return mApiService.queryScoreById(id);
    }

    @Override
    public Observable<BaseBean<String>> deleteScore(String id) {
        return mApiService.deleteScore(id);
    }

    @Override
    public Observable<BaseBean<String>> addScore(ScoreBean scoreBean) {
        return mApiService.addScore(scoreBean);
    }

    @Override
    public Observable<BaseBean<String>> updateScore(ScoreBean scoreBean) {
        return mApiService.updateScore(scoreBean);
    }
}
