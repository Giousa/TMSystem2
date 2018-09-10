package com.zmm.tmsystem.mvp.model;

import com.zmm.tmsystem.bean.BaseBean;
import com.zmm.tmsystem.http.ApiService;
import com.zmm.tmsystem.mvp.presenter.contract.RegisterContract;

import io.reactivex.Observable;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/14
 * Time:下午2:30
 */

public class RegisterModel implements RegisterContract.IRegisterModel {


    private ApiService mApiService;

    public RegisterModel(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Observable<BaseBean<String>> getVerifyCode(String phone) {

        return mApiService.getVerifyCode(phone);
    }

    @Override
    public Observable<BaseBean<String>> register(String phone, String password, String verifyCode) {

        return mApiService.register(phone, password,verifyCode);
    }

    @Override
    public Observable<BaseBean<String>> forgetPassword(String phone, String newPassword, String verifyCode) {

        return mApiService.forgetPassword(phone, newPassword, verifyCode);
    }

    @Override
    public Observable<BaseBean<String>> modifyByType(String id, int type, String content, String verifyCode) {
        return mApiService.modifyByType(id,type,content,verifyCode);
    }


}
