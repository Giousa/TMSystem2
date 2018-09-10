package com.zmm.tmsystem.dagger.module;

import com.zmm.tmsystem.http.ApiService;
import com.zmm.tmsystem.mvp.model.SpendingModel;
import com.zmm.tmsystem.mvp.presenter.contract.SpendingContract;

import dagger.Module;
import dagger.Provides;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/9/5
 * Email:65489469@qq.com
 */

@Module
public class SpendingModule {

    private SpendingContract.SpendingView mSpendingView;

    public SpendingModule(SpendingContract.SpendingView spendingView) {
        mSpendingView = spendingView;
    }

    @Provides
    public SpendingContract.SpendingView provideSpendingView(){
        return mSpendingView;
    }

    @Provides
    public SpendingContract.ISpendingModel provideSpendingModel(ApiService apiService){
        return new SpendingModel(apiService);
    }
}
