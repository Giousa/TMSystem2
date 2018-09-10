package com.zmm.tmsystem.dagger.module;

import com.zmm.tmsystem.http.ApiService;
import com.zmm.tmsystem.mvp.model.LoginModel;
import com.zmm.tmsystem.mvp.presenter.contract.LoginContract;

import dagger.Module;
import dagger.Provides;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/13
 * Time:下午10:59
 */

@Module
public class LoginModule {

    private LoginContract.LoginView mLoginView;

    public LoginModule(LoginContract.LoginView loginView) {
        mLoginView = loginView;
    }

    @Provides
    public LoginContract.LoginView provideLoginView(){

        return mLoginView;
    }

    @Provides
    public LoginContract.ILoginModel provideLoginModel(ApiService apiService){

        return new LoginModel(apiService);
    }


}
