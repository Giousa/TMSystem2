package com.zmm.tmsystem.dagger.module;

import com.zmm.tmsystem.http.ApiService;
import com.zmm.tmsystem.mvp.model.RegisterModel;
import com.zmm.tmsystem.mvp.presenter.contract.RegisterContract;

import dagger.Module;
import dagger.Provides;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/14
 * Time:下午2:29
 */

@Module
public class RegisterModule {

    private RegisterContract.RegisterView mRegisterView;

    public RegisterModule(RegisterContract.RegisterView registerView) {
        mRegisterView = registerView;
    }

    @Provides
    public RegisterContract.RegisterView provideRegisgerView(){
        return mRegisterView;
    }

    @Provides
    public RegisterContract.IRegisterModel provideRegisterView(ApiService apiService){
        return new RegisterModel(apiService);
    }
}
