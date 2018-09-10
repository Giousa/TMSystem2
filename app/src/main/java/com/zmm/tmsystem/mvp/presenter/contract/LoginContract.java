package com.zmm.tmsystem.mvp.presenter.contract;


import com.zmm.tmsystem.bean.BaseBean;
import com.zmm.tmsystem.bean.TeacherBean;
import com.zmm.tmsystem.mvp.view.BaseView;

import io.reactivex.Observable;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/6
 * Time:上午11:27
 */

public interface LoginContract {

    interface ILoginModel{

        Observable<BaseBean<TeacherBean>> login(String phone, String password);

    }


    interface LoginView extends BaseView {

        void checkPhoneError();
        void checkPasswprdError();
        void loginSuccess();

    }
}
