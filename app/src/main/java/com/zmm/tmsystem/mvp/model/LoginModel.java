package com.zmm.tmsystem.mvp.model;

import com.zmm.tmsystem.bean.BaseBean;
import com.zmm.tmsystem.bean.TeacherBean;
import com.zmm.tmsystem.http.ApiService;
import com.zmm.tmsystem.mvp.presenter.contract.LoginContract;

import io.reactivex.Observable;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/13
 * Time:下午11:02
 */

public class LoginModel implements LoginContract.ILoginModel{


    private ApiService mApiService;

    public LoginModel(ApiService apiService) {
        mApiService = apiService;
    }


    @Override
    public Observable<BaseBean<TeacherBean>> login(String phone, String password) {

        Observable<BaseBean<TeacherBean>> login = mApiService.login(phone, password);

        return login;
    }

}
