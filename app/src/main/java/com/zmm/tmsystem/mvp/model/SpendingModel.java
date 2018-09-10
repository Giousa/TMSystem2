package com.zmm.tmsystem.mvp.model;

import com.zmm.tmsystem.bean.BaseBean;
import com.zmm.tmsystem.bean.MoneyBean;
import com.zmm.tmsystem.bean.SpendingBean;
import com.zmm.tmsystem.http.ApiService;
import com.zmm.tmsystem.mvp.presenter.contract.SpendingContract;

import java.util.List;

import io.reactivex.Observable;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/9/5
 * Email:65489469@qq.com
 */
public class SpendingModel implements SpendingContract.ISpendingModel{


    private ApiService mApiService;

    public SpendingModel(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Observable<BaseBean<List<SpendingBean>>> getSpendingListByMoneyId(String moneyId) {
        return mApiService.getSpendingListByMoneyId(moneyId);
    }

    @Override
    public Observable<BaseBean<String>> deleteSpending(String spendingId, float pay, float deposit) {
        return mApiService.deleteSpending(spendingId,pay,deposit);
    }

    @Override
    public Observable<BaseBean<String>> updateSpending(String moneyId, String title, String content, float pay, float deposit) {
        return mApiService.updateSpending(moneyId,title,content,pay,deposit);
    }

    @Override
    public Observable<BaseBean<MoneyBean>> getMoneyById(String moneyId) {
        return mApiService.getMoneyById(moneyId);
    }
}
