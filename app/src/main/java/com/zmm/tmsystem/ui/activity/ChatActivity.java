package com.zmm.tmsystem.ui.activity;

import android.view.View;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.zmm.tmsystem.R;
import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.dagger.component.AppComponent;
import com.zmm.tmsystem.ui.widget.TitleBar;

import java.io.Serializable;

import butterknife.BindView;
import cn.jpush.im.android.api.model.Conversation;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/9/5
 * Email:65489469@qq.com
 */
public class ChatActivity extends BaseActivity{

    @BindView(R.id.title_bar)
    TitleBar mTitleBar;


    @Override
    protected int setLayout() {
        return R.layout.activity_chat;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void init() {

        initToolBar();
    }


    private void initToolBar() {

        String username = getIntent().getStringExtra(Constant.TARGET_NAME);
        String targetId = getIntent().getStringExtra(Constant.TARGET_ID);
        String targetAppKey = getIntent().getStringExtra(Constant.TARGET_APP_KEY);

        //这里一定要加上，否则menu不显示
        setSupportActionBar(mTitleBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        System.out.println("username = "+username);
        System.out.println("targetId = "+targetId);
        System.out.println("targetAppKey = "+targetAppKey);

        mTitleBar.setCenterTitle(username);
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

    }

}
