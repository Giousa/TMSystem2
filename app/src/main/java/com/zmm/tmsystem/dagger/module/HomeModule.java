package com.zmm.tmsystem.dagger.module;

import com.zmm.tmsystem.http.ApiService;
import com.zmm.tmsystem.mvp.model.HomeModel;
import com.zmm.tmsystem.mvp.presenter.contract.HomeContract;

import dagger.Module;
import dagger.Provides;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/14
 * Time:下午11:48
 */

@Module
public class HomeModule {

    private HomeContract.HomeView mHomeView;

    public HomeModule(HomeContract.HomeView homeView) {
        mHomeView = homeView;
    }

    @Provides
    public HomeContract.HomeView provideMainView(){
        return mHomeView;
    }

    @Provides
    public HomeContract.IHomeModel provideMainModel(ApiService apiService){
        return new HomeModel(apiService);
    }
}
