package com.zmm.tmsystem.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.zmm.tmsystem.R;
import com.zmm.tmsystem.bean.TeacherBean;
import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.common.utils.ACache;
import com.zmm.tmsystem.common.utils.TeacherCacheUtil;
import com.zmm.tmsystem.common.utils.ToastUtils;
import com.zmm.tmsystem.common.utils.VersionUtils;
import com.zmm.tmsystem.dagger.component.AppComponent;
import com.zmm.tmsystem.ui.widget.CustomInfoItemView;
import com.zmm.tmsystem.ui.widget.GlideCircleTransform;
import com.zmm.tmsystem.ui.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/17
 * Time:上午2:49
 */

public class SettingActivity extends BaseActivity implements CustomInfoItemView.OnItemClickListener {

    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.iv_head)
    ImageView mIvHead;
    @BindView(R.id.setting_item_phone)
    CustomInfoItemView mSettingItemPhone;
    @BindView(R.id.setting_item_password)
    CustomInfoItemView mSettingItemPassword;
    @BindView(R.id.setting_item_code)
    CustomInfoItemView mSettingItemCode;
    @BindView(R.id.setting_item_version)
    CustomInfoItemView mSettingItemVersion;

    @Override
    protected int setLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void init() {

        mTitleBar.setCenterTitle(getResources().getString(R.string.main_title_setting));
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


        String icon = teacherBean.getIcon();
        if (!TextUtils.isEmpty(icon)) {

            Glide.with(this)
                    .load(Constant.BASE_IMG_URL + icon)
                    .transform(new GlideCircleTransform(this))
                    .error(new IconicsDrawable(this)
                            .icon(Ionicons.Icon.ion_android_contact)
                            .color(getResources().getColor(R.color.md_blue_500)
                            ))
                    .into(mIvHead);
        } else {
            mIvHead.setImageDrawable(new IconicsDrawable(this)
                    .icon(Ionicons.Icon.ion_android_contact)
                    .color(getResources().getColor(R.color.md_blue_500)
                    ));
        }

        mSettingItemPhone.setOnItemClickListener(this, Constant.TYPE_MODIFY_PHONE);
        mSettingItemPassword.setOnItemClickListener(this, Constant.TYPE_MODIFY_PASSWORD);
        mSettingItemCode.setOnItemClickListener(this, Constant.TYPE_QR_CODE);
        mSettingItemVersion.setOnItemClickListener(this, Constant.TYPE_VERSION);
        mSettingItemVersion.setContent("v"+VersionUtils.getVersionName(this));


    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

        String s = "37a295fddd28d931903c76e86e1b219e";
    }


    @Override
    public void itemClick(int type, String name) {

        System.out.println("type = " + type);
        switch (type) {

            case Constant.TYPE_MODIFY_PHONE:

                Intent intent1 = new Intent(this, ModifyActivity.class);
                intent1.putExtra(Constant.MODIFY_PARAM, 1);
                startActivity(intent1);
                break;
            case Constant.TYPE_MODIFY_PASSWORD:
                Intent intent2 = new Intent(this, ModifyActivity.class);
                intent2.putExtra(Constant.MODIFY_PARAM, 2);
                startActivity(intent2);
                break;
            case Constant.TYPE_QR_CODE:
                shareUmeng();
                break;
            case Constant.TYPE_VERSION:
                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private void shareUmeng() {

//        UMImage image = new UMImage(ShareActivity.this, "imageurl");//网络图片
//        UMImage image = new UMImage(ShareActivity.this, file);//本地文件
//        UMImage image = new UMImage(ShareActivity.this, R.drawable.xxx);//资源文件
//        UMImage image = new UMImage(ShareActivity.this, bitmap);//bitmap文件
//        UMImage image = new UMImage(ShareActivity.this, byte[]);//字节流

        UMImage image = new UMImage(this, R.mipmap.ic_launcher);
        UMWeb web = new UMWeb(Constant.STUDENT_DOWNLOAD_URL);
        web.setTitle("学生端");//标题
        web.setThumb(image);  //缩略图
        web.setDescription("一款和教师系统紧密结合的辅助软件，配合教师对学生进行管理");//描述

        new ShareAction(this)
                .setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE)
                .withMedia(web)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        System.out.println("---onStart---");
                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        System.out.println("---onResult---");
                        ToastUtils.SimpleToast(SettingActivity.this,"恭喜您，分享成功！");
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        System.out.println("---onError---");

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        System.out.println("---onCancel---");

                    }
                }).open();
    }

    @OnClick(R.id.btn_exit)
    public void onViewClicked() {
        TeacherCacheUtil.clear(this);
        startActivity(LoginActivity.class);
    }


}
