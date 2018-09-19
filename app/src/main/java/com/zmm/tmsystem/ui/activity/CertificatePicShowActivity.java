package com.zmm.tmsystem.ui.activity;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.zmm.tmsystem.R;
import com.zmm.tmsystem.bean.CertificatesBean;
import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.dagger.component.AppComponent;
import com.zmm.tmsystem.ui.widget.TitleBar;

import butterknife.BindView;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/9/4
 * Email:65489469@qq.com
 */
public class CertificatePicShowActivity extends BaseActivity {


    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    private String[] mPics;


    @Override
    protected int setLayout() {
        return R.layout.activity_certificate_pic_show;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    protected void init() {

        CertificatesBean certificatesBean = (CertificatesBean) this.getIntent().getSerializableExtra(Constant.CERTIFICATE);

        mPics = certificatesBean.getPic().split(",");

        Glide.with(this)
                .load(Constant.BASE_IMG_URL + mPics[0])
                .into(ivPic);

        initToolBar();

    }

    private void initToolBar() {
        //这里一定要加上，否则menu不显示
        setSupportActionBar(mTitleBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mTitleBar.setCenterTitle("");
        mTitleBar.setBackgroundColor(this.getResources().getColor(R.color.transparent));
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
