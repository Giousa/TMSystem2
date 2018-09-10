package com.zmm.tmsystem.ui.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.zmm.tmsystem.R;
import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.common.utils.PicUtils;
import com.zmm.tmsystem.common.utils.ToastUtils;
import com.zmm.tmsystem.dagger.component.AppComponent;
import com.zmm.tmsystem.dagger.component.DaggerLoginComponent;
import com.zmm.tmsystem.dagger.module.LoginModule;
import com.zmm.tmsystem.mvp.presenter.LoginPresenter;
import com.zmm.tmsystem.mvp.presenter.contract.LoginContract;
import com.zmm.tmsystem.ui.widget.LoadingButton;
import com.zmm.tmsystem.ui.widget.TitleBar;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/12
 * Time:下午9:33
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.LoginView {

    @BindView(R.id.et_login_phone)
    EditText mEtLoginPhone;
    @BindView(R.id.et_login_password)
    EditText mEtLoginPassword;
    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.btn_login)
    LoadingButton mLoadingButton;
    @BindView(R.id.ll_login_bg)
    LinearLayout mLlLoginBg;


    @Override
    protected int setLayout() {

        System.out.println("登录");
        return R.layout.activity_login;
    }

    @Override
    protected void init() {

        mLlLoginBg.setBackground(new BitmapDrawable(PicUtils.readBitMap(this, R.drawable.login_bg)));

        mTitleBar.setCenterTitle(getResources().getString(R.string.login));

        mLoadingButton.setText(getResources().getString(R.string.login));


        InitialValueObservable<CharSequence> obMobi = RxTextView.textChanges(mEtLoginPhone);
        InitialValueObservable<CharSequence> obPassword = RxTextView.textChanges(mEtLoginPassword);

        Observable.combineLatest(obMobi, obPassword, new BiFunction<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence mobi, CharSequence pwd) throws Exception {
                return isPhoneValid(mobi.toString()) && isPasswordValid(pwd.toString());
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
                mPresenter.login(mEtLoginPhone.getText().toString().trim(), mEtLoginPassword.getText().toString().trim());
            }
        });
    }


    private boolean isPhoneValid(String phone) {
        return phone.length() == 11;
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6 && password.length() <= 16;
    }


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerLoginComponent.builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @OnClick({R.id.tv_login_register, R.id.tv_login_forget})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.tv_login_register:

                Intent intent1 = new Intent(this, RegisterActivity.class);
                intent1.putExtra(Constant.REGISTER_PARAM, 1);
                startActivity(intent1);

                break;
            case R.id.tv_login_forget:
                Intent intent2 = new Intent(this, RegisterActivity.class);
                intent2.putExtra(Constant.REGISTER_PARAM, 2);
                startActivity(intent2);
                break;
        }
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
        ToastUtils.SimpleToast(this, getResources().getString(R.string.check_phone_error));
    }

    @Override
    public void checkPasswprdError() {
        ToastUtils.SimpleToast(this, getResources().getString(R.string.check_password_error));
    }

    @Override
    public void loginSuccess() {
        startActivity(MainActivity.class);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == event.KEYCODE_BACK) {
            removeAllActivity();
        }

        return true;
    }

}
