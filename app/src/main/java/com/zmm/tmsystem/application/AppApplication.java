package com.zmm.tmsystem.application;

import android.app.Application;
import android.content.Context;
import android.view.View;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.zmm.tmsystem.dagger.component.AppComponent;
import com.zmm.tmsystem.dagger.component.DaggerAppComponent;
import com.zmm.tmsystem.dagger.module.AppModule;
import com.zmm.tmsystem.dagger.module.HttpModule;
import com.zmm.tmsystem.ui.activity.BaseActivity;
import com.zmm.tmsystem.ui.widget.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;


/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/5/23
 * Time:下午5:56
 */

public class AppApplication extends Application {


    private AppComponent mAppComponent;
    private View mView;

    private List<BaseActivity> mBaseActivityList;


    public AppComponent getAppComponent() {
        return mAppComponent;
    }


    public View getView() {
        return mView;
    }

    public void setView(View view) {
        mView = view;
    }

    public static AppApplication get(Context context){
        return (AppApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBaseActivityList = new ArrayList<>();
        mAppComponent = DaggerAppComponent.builder().httpModule(new HttpModule()).appModule(new AppModule(this)).build();

        initPhoto();
    }

    private void initPhoto() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setMultiMode(false);//单选或多选模式
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.CIRCLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }

    /**
     * 添加Activity
     */
    public void addActivity_(BaseActivity activity) {
        if (!mBaseActivityList.contains(activity)) {
            mBaseActivityList.add(activity);//把当前Activity添加到集合中
        }
    }

    /**
     * 销毁单个Activity
     */
    public void removeActivity_(BaseActivity activity) {
        if (mBaseActivityList.contains(activity)) {
            mBaseActivityList.remove(activity);//从集合中移除
            activity.finish();//销毁当前Activity
        }
    }


    /**
     * 销毁全部Activity
     */
    public void removeAllActivity_(){
        for (BaseActivity activity:mBaseActivityList) {
            activity.finish();
        }
    }
}
