package com.zmm.tmsystem.mvp.model;

import com.zmm.tmsystem.bean.BaseBean;
import com.zmm.tmsystem.bean.TermBean;
import com.zmm.tmsystem.http.ApiService;
import com.zmm.tmsystem.mvp.presenter.contract.TermContract;

import java.util.List;

import io.reactivex.Observable;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/17
 * Time:下午7:38
 */

public class TermModel implements TermContract.ITermModel{


    private ApiService mApiService;

    public TermModel(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Observable<BaseBean<TermBean>> createNewTerm(TermBean termBean) {
        return mApiService.createNewTerm(termBean);
    }

    @Override
    public Observable<BaseBean<List<TermBean>>> queryAllTerm(String tId) {
        return mApiService.queryAllTerm(tId);
    }

    @Override
    public Observable<BaseBean<TermBean>> queryTermById(String id) {
        return mApiService.queryTermById(id);
    }

    @Override
    public Observable<BaseBean<TermBean>> updateTerm(TermBean termBean) {
        return mApiService.updateTerm(termBean);
    }

    @Override
    public Observable<BaseBean<String>> deleteTerm(String id) {
        return mApiService.deleteTerm(id);
    }
}
