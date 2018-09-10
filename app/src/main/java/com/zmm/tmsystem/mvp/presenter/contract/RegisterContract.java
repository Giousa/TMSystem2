package com.zmm.tmsystem.mvp.presenter.contract;

import com.zmm.tmsystem.bean.BaseBean;
import com.zmm.tmsystem.bean.TeacherBean;
import com.zmm.tmsystem.mvp.view.BaseView;

import io.reactivex.Observable;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/12
 * Time:下午4:08
 */

public interface RegisterContract {

    interface IRegisterModel{

        Observable<BaseBean<String>> getVerifyCode(String phone);

        Observable<BaseBean<String>> register(String phone, String password, String verifyCode);

        Observable<BaseBean<String>> forgetPassword(String phone, String newPassword, String verifyCode);

        Observable<BaseBean<String>> modifyByType(String id,int type, String content, String verifyCode);


    }

    interface RegisterView extends BaseView{

        void checkPhoneError();
        void checkPasswordError();

        void sendVerifyCodeSuccess();
        void performSuccess(String msg);

    }
}
