package com.zmm.tmsystem.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.view.View;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.zmm.tmsystem.dagger.component.AppComponent;
import com.zmm.tmsystem.dagger.component.DaggerAppComponent;
import com.zmm.tmsystem.dagger.module.AppModule;
import com.zmm.tmsystem.dagger.module.HttpModule;
import com.zmm.tmsystem.jmessage.NotificationClickEventReceiver;
import com.zmm.tmsystem.ui.activity.BaseActivity;
import com.zmm.tmsystem.ui.widget.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Message;


/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/5/23
 * Time:下午5:56
 */

public class AppApplication extends Application {

    private static String tag = "AppApplication";
    private static Context mContext;

    public static List<Message> forwardMsg = new ArrayList<>();


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

        mContext = this.getApplicationContext();
        mBaseActivityList = new ArrayList<>();
        mAppComponent = DaggerAppComponent.builder().httpModule(new HttpModule()).appModule(new AppModule(this)).build();

        initPhoto();

        initUmeng();

        initJiGuang();

    }

    public static Context getContext() {
        return mContext;
    }

    /**
     * 初始化拍照
     */
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
     * 初始化友盟
     */
    private void initUmeng() {
        //友盟初始化
        UMConfigure.init(this,"5b92866ca40fa35cc00000e9","umeng", UMConfigure.DEVICE_TYPE_PHONE,"");
        //需要去微信平台获取
        PlatformConfig.setWeixin("wxe40ce71811dd034d", "a2cfd98f6329e01af142773fa4e6ea5f");
        //打印友盟日志
        UMConfigure.setLogEnabled(true);
    }


    /**
     * 初始化极光IM
     */
    private void initJiGuang() {

        JMessageClient.init(this);
        JMessageClient.setDebugMode(true);

        //设置Notification的模式
//        JMessageClient.setNotificationFlag(JMessageClient.FLAG_NOTIFY_WITH_SOUND | JMessageClient.FLAG_NOTIFY_WITH_LED | JMessageClient.FLAG_NOTIFY_WITH_VIBRATE);
        JMessageClient.setNotificationFlag(JMessageClient.NOTI_MODE_SILENCE);

        //注册Notification点击的接收器
        new NotificationClickEventReceiver(getApplicationContext());
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

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
