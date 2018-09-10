package com.zmm.tmsystem.ui.activity;

import android.graphics.drawable.BitmapDrawable;
import android.widget.LinearLayout;

import com.zmm.tmsystem.R;
import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.common.utils.ACache;
import com.zmm.tmsystem.common.utils.PicUtils;
import com.zmm.tmsystem.dagger.component.AppComponent;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/12
 * Time:下午4:15
 */

public class SplashActivity extends BaseActivity {


    @BindView(R.id.ll_splash_bg)
    LinearLayout mLlSplashBg;

    @Override
    protected int setLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init() {

        mLlSplashBg.setBackground(new BitmapDrawable(PicUtils.readBitMap(this, R.drawable.splash_bg)));

        Observable.timer(2, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {

                ACache aCache = ACache.get(SplashActivity.this);
                String asString = aCache.getAsString(Constant.TEACHER_ID);

                startActivity(LoginActivity.class);

//                if(!TextUtils.isEmpty(asString)){
//                    startActivity(MainActivity.class);
//                }else {
//                    startActivity(LoginActivity.class);
//                }


            }
        });


    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        gcImageView(mLlSplashBg);
//    }

}
