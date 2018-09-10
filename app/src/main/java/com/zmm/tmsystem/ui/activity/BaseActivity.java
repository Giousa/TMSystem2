package com.zmm.tmsystem.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.mikepenz.iconics.context.IconicsLayoutInflater2;
import com.zmm.tmsystem.R;
import com.zmm.tmsystem.application.AppApplication;
import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.dagger.component.AppComponent;
import com.zmm.tmsystem.mvp.presenter.BasePresenter;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/5/24
 * Time:下午1:13
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

    private Unbinder mUnbinder;
    protected AppApplication mAppApplication;

    private BaseActivity mBaseActivity;

    public int mScreenWidth;


    @Inject
    T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        overridePendingTransition(R.anim.in_from_right, 0);//进入的动画
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

//        LayoutInflaterCompat.setFactory2(getLayoutInflater(), new IconicsLayoutInflater2(getDelegate()));

        super.onCreate(savedInstanceState);

        setContentView(setLayout());

        mUnbinder = ButterKnife.bind(this);

        mScreenWidth = getScreenWidth();

        mBaseActivity = this;

        mAppApplication = (AppApplication) getApplication();

        addActivity();

        setupActivityComponent(mAppApplication.getAppComponent());

        init();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity();
        if(mUnbinder != Unbinder.EMPTY){
            mUnbinder.unbind();
        }
    }

    protected abstract int setLayout();

    protected abstract void setupActivityComponent(AppComponent appComponent);

    protected abstract void init();


    protected void startActivity(Class activity) {

        startActivity(activity,true);
    }

    protected void startActivity(Class activity, boolean flag) {

        Intent intent = new Intent(this, activity);
        startActivity(intent);

        if (flag) {
            finish();
        }

    }

    @Override
    public void finish() {
        super.finish();
//        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);

    }


    public void addActivity() {
        mAppApplication.addActivity_(mBaseActivity);
    }

    public void removeActivity(){
        mAppApplication.removeActivity_(mBaseActivity);
    }

    public void removeAllActivity() {
        mAppApplication.removeAllActivity_();
    }



    public int getScreenWidth(){
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }


    /**
     * 让屏幕变暗
     */
    protected void makeWindowDark(){
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.5f;
        window.setAttributes(lp);
    }
    /**
     * 让屏幕变亮
     */
    protected void makeWindowLight(){
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 1f;
        window.setAttributes(lp);
    }

//    protected void gcImageView(ImageView imageView){
//
//        if(imageView !=  null &&  imageView.getDrawable() != null){
//
//            Bitmap oldBitmap =  ((BitmapDrawable) imageView.getDrawable()).getBitmap();
//
//            imageView.setImageDrawable(null);
//
//            if(oldBitmap !=  null){
//
//                oldBitmap.recycle();
//
//                oldBitmap =  null;
//
//            }
//
//        }
//    }
}
