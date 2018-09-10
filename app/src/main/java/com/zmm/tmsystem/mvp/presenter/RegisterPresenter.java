package com.zmm.tmsystem.mvp.presenter;

import com.zmm.tmsystem.common.utils.VerificationUtils;
import com.zmm.tmsystem.mvp.presenter.contract.RegisterContract;
import com.zmm.tmsystem.rx.RxHttpResponseCompat;
import com.zmm.tmsystem.rx.subscriber.ErrorHandlerSubscriber;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/14
 * Time:下午2:28
 */

public class RegisterPresenter extends BasePresenter<RegisterContract.IRegisterModel,RegisterContract.RegisterView> {

    @Inject
    public RegisterPresenter(RegisterContract.IRegisterModel model, RegisterContract.RegisterView view) {
        super(model, view);
    }


    /**
     * 获取验证码
     * @param phone
     */
    public void getVerifyCode(String phone){

        mModel.getVerifyCode(phone)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>(mContext) {


                    @Override
                    public void onNext(String s) {
                        mView.sendVerifyCodeSuccess();
                    }
                });


    }

    /**
     * 注册功能
     * @param phone
     * @param password
     */
    public void register(String phone, String password, String verifyCode) {

        if(!VerificationUtils.matcherPassword(password)){
            mView.checkPasswordError();
            return;
        }

        mModel.register(phone,password,verifyCode)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>(mContext) {
                    @Override
                    public void onSubscribe(Disposable d) {

                        mView.showLoading();
                    }

                    @Override
                    public void onComplete() {

                        mView.dismissLoading();
                    }


                    @Override
                    public void onNext(String s) {
                        mView.dismissLoading();
                        mView.performSuccess(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.dismissLoading();
                    }
                });
    }

    /**
     * 忘记密码
     * @param phone
     * @param newPassword
     * @param verifyCode
     */
    public void forgetPassword(String phone, String newPassword, String verifyCode) {
        mModel.forgetPassword(phone,newPassword,verifyCode)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>(mContext) {
                    @Override
                    public void onSubscribe(Disposable d) {

                        mView.showLoading();
                    }

                    @Override
                    public void onComplete() {

                        mView.dismissLoading();
                    }

                    @Override
                    public void onNext(String s) {
                        mView.dismissLoading();
                        mView.performSuccess(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.dismissLoading();
                    }
                });
    }


    /**
     * 修改 手机号或密码
     * @param id
     * @param type
     * @param content
     */
    public void modifyByType(String id,int type, String content, String verifyCode){

        System.out.println("id = "+id+",type = "+type+",content = "+content+",code = "+verifyCode);

        mModel.modifyByType(id,type,content,verifyCode)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>(mContext) {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.showLoading();
                    }

                    @Override
                    public void onNext(String s) {
                        mView.dismissLoading();
                        mView.performSuccess(s);

                    }

                    @Override
                    public void onComplete() {
                        mView.dismissLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.dismissLoading();
                    }
                });
    }
}
