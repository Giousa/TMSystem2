package com.zmm.tmsystem.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.zmm.tmsystem.R;
import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.common.utils.ToastUtils;
import com.zmm.tmsystem.common.utils.VerificationUtils;
import com.zmm.tmsystem.dagger.component.AppComponent;
import com.zmm.tmsystem.dagger.component.DaggerRegisterComponent;
import com.zmm.tmsystem.dagger.module.RegisterModule;
import com.zmm.tmsystem.mvp.presenter.RegisterPresenter;
import com.zmm.tmsystem.mvp.presenter.contract.RegisterContract;
import com.zmm.tmsystem.ui.widget.LoadingButton;
import com.zmm.tmsystem.ui.widget.TitleBar;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;

/**
 * Description:  注册用户+忘记密码
 * Author:zhangmengmeng
 * Date:2018/6/12
 * Time:下午10:50
 */

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.RegisterView {

    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_psd)
    EditText mEtPsd;
    @BindView(R.id.et_yzm)
    EditText mEtYzm;
    @BindView(R.id.tv_getYzm)
    TextView mTvGetYzm;
    @BindView(R.id.btn_loading_button)
    LoadingButton mLoadingButton;
    private int mParamInt;

    @Override
    protected int setLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void init() {


        mTitleBar.setNavigationIcon(new IconicsDrawable(this)
                .icon(Ionicons.Icon.ion_android_arrow_back)
                .sizeDp(20)
                .color(getResources().getColor(R.color.white)
                ));
        mTitleBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Bundle bundle = getIntent().getExtras();
        mParamInt = bundle.getInt(Constant.REGISTER_PARAM, 1);

        System.out.println("paramInt = " + mParamInt);

        if (mParamInt == 1) {
            mTitleBar.setCenterTitle(getResources().getString(R.string.register));
            mLoadingButton.setText(getResources().getString(R.string.register));
            mLoadingButton.setLoadingText(getResources().getString(R.string.register_loading));
        } else {
            mTitleBar.setCenterTitle(getResources().getString(R.string.forget_password));
            mLoadingButton.setText(getResources().getString(R.string.reset_password));
            mLoadingButton.setLoadingText(getResources().getString(R.string.reset_password_loading));
            mEtPsd.setHint("请输入新密码");
        }


        InitialValueObservable<CharSequence> obMobi = RxTextView.textChanges(mEtPhone);
        InitialValueObservable<CharSequence> obPassword = RxTextView.textChanges(mEtPsd);
        InitialValueObservable<CharSequence> obYzm = RxTextView.textChanges(mEtYzm);


        Observable.combineLatest(obMobi, obPassword, obYzm, new Function3<CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence phone, CharSequence password, CharSequence yzm) throws Exception {
                return isPhoneValid(phone.toString()) && isPasswordValid(password.toString()) && isYzmValid(yzm.toString());
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                RxView.enabled(mLoadingButton).accept(aBoolean);
            }

        });

        RxView.clicks(mLoadingButton).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {

                if (mParamInt == 1) {
                    mPresenter.register(mEtPhone.getText().toString().trim(),mEtPsd.getText().toString().trim(),mEtYzm.getText().toString().trim());
                }else {
                    mPresenter.forgetPassword(mEtPhone.getText().toString().trim(),mEtPsd.getText().toString().trim(),mEtYzm.getText().toString().trim());
                }
            }
        });

    }

    private boolean isYzmValid(String yzm) {
        return yzm.length() == 6;
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6 && password.length() <= 16;
    }

    private boolean isPhoneValid(String phone) {
        return phone.length() == 11;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerRegisterComponent.builder()
                .appComponent(appComponent)
                .registerModule(new RegisterModule(this))
                .build()
                .inject(this);
    }


    @OnClick({R.id.tv_getYzm})
    public void onViewClicked(View view) {
        String phoneStr = mEtPhone.getText().toString();

        if(TextUtils.isEmpty(phoneStr)){
            ToastUtils.SimpleToast(RegisterActivity.this,"手机号不能为空");
            return;
        }

        boolean phoneValid = VerificationUtils.matcherPhoneNum(phoneStr);
        if(!phoneValid){
            ToastUtils.SimpleToast(RegisterActivity.this,"手机号格式不正确");
            return;
        }

        sendVerifyCode(phoneStr);
    }

    /**
     * 发送验证码
     * @param phone
     */
    private void sendVerifyCode(String phone) {

        mPresenter.getVerifyCode(phone);

    }

    @Override
    public void showLoading() {
        mLoadingButton.showLoading();
    }

    @Override
    public void showError(String msg) {
        mLoadingButton.showButtonText();
    }

    @Override
    public void dismissLoading() {
        mLoadingButton.showButtonText();
    }

    @Override
    public void checkPhoneError() {
        ToastUtils.SimpleToast(this,getResources().getString(R.string.check_phone_error));
    }

    @Override
    public void checkPasswordError() {
        ToastUtils.SimpleToast(this,getResources().getString(R.string.check_password_error));
    }

    @Override
    public void sendVerifyCodeSuccess() {
        ToastUtils.SimpleToast(this,getResources().getString(R.string.verify_code_send_success));

        countDown();
    }

    private Disposable mDisposable;

    /**
     * 计时
     */
    private void countDown() {

        System.out.println("开始计时：：");
        final int count = 60;

        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(count)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return count - aLong;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mTvGetYzm.setClickable(false);
                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Long value) {
                        System.out.println("value = " + value);
                        mTvGetYzm.setText(value + " 秒");

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        ToastUtils.SimpleToast(RegisterActivity.this,getResources().getString(R.string.exception));
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("complete");
                        mTvGetYzm.setClickable(true);
                        mTvGetYzm.setText("发送验证码");
                    }
                });
    }

    @Override
    public void performSuccess(String msg) {

        ToastUtils.SimpleToast(RegisterActivity.this,msg);

        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        //取消计时器的订阅，在退出时，停止
        if(mDisposable != null){
            mDisposable.dispose();
        }
    }
}
