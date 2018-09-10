package com.zmm.tmsystem.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.zmm.tmsystem.R;
import com.zmm.tmsystem.bean.TeacherBean;
import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.common.utils.ACache;
import com.zmm.tmsystem.common.utils.CheckUtils;
import com.zmm.tmsystem.common.utils.ToastUtils;
import com.zmm.tmsystem.common.utils.VerificationUtils;
import com.zmm.tmsystem.dagger.component.AppComponent;
import com.zmm.tmsystem.dagger.component.DaggerRegisterComponent;
import com.zmm.tmsystem.dagger.module.RegisterModule;
import com.zmm.tmsystem.mvp.presenter.RegisterPresenter;
import com.zmm.tmsystem.mvp.presenter.contract.RegisterContract;
import com.zmm.tmsystem.ui.widget.GlideCircleTransform;
import com.zmm.tmsystem.ui.widget.LoadingButton;
import com.zmm.tmsystem.ui.widget.TitleBar;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/17
 * Time:上午5:20
 */

public class ModifyActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.RegisterView {


    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.iv_head)
    ImageView mIvHead;
    @BindView(R.id.iv_modify_icon)
    ImageView mIvModifyIcon;
    @BindView(R.id.et_modify)
    EditText mEtModify;
    @BindView(R.id.et_yzm)
    EditText mEtYzm;
    @BindView(R.id.tv_getYzm)
    TextView mTvGetYzm;
    @BindView(R.id.btn_loading_button)
    LoadingButton mLoadingButton;


    private int mParamInt;
    private String mPhone;
    private String mId;

    private Disposable mDisposable;


    @Override
    protected int setLayout() {
        return R.layout.activity_modify;
    }


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerRegisterComponent.builder()
                .appComponent(appComponent)
                .registerModule(new RegisterModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void init() {

        Bundle bundle = getIntent().getExtras();
        mParamInt = bundle.getInt(Constant.MODIFY_PARAM, 1);

        if (mParamInt == 1) {
            mTitleBar.setCenterTitle(getResources().getString(R.string.modify_phone));
            mIvModifyIcon.setImageDrawable(getResources().getDrawable(R.drawable.login_phone));
            mEtModify.setHint(getResources().getString(R.string.input_new_phone));
            mLoadingButton.setText(getResources().getString(R.string.modify_phone));
            mLoadingButton.setLoadingText(getResources().getString(R.string.modify_password_loading));
        } else {
            mTitleBar.setCenterTitle(getResources().getString(R.string.modify_password));
            mLoadingButton.setText(getResources().getString(R.string.modify_password));
            mLoadingButton.setLoadingText(getResources().getString(R.string.modify_password_loading));
        }



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


        ACache aCache = ACache.get(this);
        TeacherBean teacherBean = (TeacherBean) aCache.getAsObject(Constant.TEACHER);

        mPhone = teacherBean.getPhone();
        mId = teacherBean.getId();
        String icon = teacherBean.getIcon();

        initIconView(icon);

        InitialValueObservable<CharSequence> obModify= RxTextView.textChanges(mEtModify);
        InitialValueObservable<CharSequence> obYzm = RxTextView.textChanges(mEtYzm);

        Observable.combineLatest(obModify, obYzm, new BiFunction<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean apply(CharSequence et, CharSequence yzm) throws Exception {

                if(mParamInt == 1){
                    return isPhoneValid(et.toString()) && isYzmValid(yzm.toString());
                }else {
                    return isPasswordValid(et.toString()) && isYzmValid(yzm.toString());
                }


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
                mPresenter.modifyByType(mId,mParamInt,mEtModify.getText().toString().trim(),mEtYzm.getText().toString().trim());
            }
        });

    }

    private void initIconView(String icon) {
        if(!TextUtils.isEmpty(icon)){

            Glide.with(this)
                    .load(icon)
                    .transform(new GlideCircleTransform(this))
                    .error(new IconicsDrawable(this)
                            .icon(Ionicons.Icon.ion_android_contact)
                            .color(getResources().getColor(R.color.md_blue_500)
                            ))
                    .into(mIvHead);

        }else {
            mIvHead.setImageDrawable(new IconicsDrawable(this)
                    .icon(Ionicons.Icon.ion_android_contact)
                    .color(getResources().getColor(R.color.md_blue_500)));
        }
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



    @OnClick(R.id.tv_getYzm)
    public void onViewClicked() {


        if(mParamInt == 1){
            mPhone = mEtModify.getText().toString();
        }



        if(TextUtils.isEmpty(mPhone)){
            ToastUtils.SimpleToast(ModifyActivity.this,"手机号不能为空");
            return;
        }

        boolean phoneValid = VerificationUtils.matcherPhoneNum(mPhone);
        if(!phoneValid){
            ToastUtils.SimpleToast(ModifyActivity.this,"手机号格式不正确");
            return;
        }

        sendVerifyCode(mPhone);

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

    @Override
    public void performSuccess(String msg) {
        ToastUtils.SimpleToast(ModifyActivity.this,msg);
        startActivity(LoginActivity.class);
    }


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
                        ToastUtils.SimpleToast(ModifyActivity.this,getResources().getString(R.string.exception));
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
    protected void onDestroy() {
        super.onDestroy();
        //取消计时器的订阅，在退出时，停止
        if(mDisposable != null){
            mDisposable.dispose();
        }
    }
}
