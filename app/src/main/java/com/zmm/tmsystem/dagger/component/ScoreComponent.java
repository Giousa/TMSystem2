package com.zmm.tmsystem.dagger.component;

import com.zmm.tmsystem.dagger.module.ScoreModule;
import com.zmm.tmsystem.dagger.scope.ActivityScope;
import com.zmm.tmsystem.ui.activity.ScoreActivity;
import com.zmm.tmsystem.ui.activity.ScoreInfoActivity;
import com.zmm.tmsystem.ui.activity.ScoreStatsActivity;

import dagger.Component;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/9/4
 * Email:65489469@qq.com
 */
@ActivityScope
@Component(modules = ScoreModule.class,dependencies = AppComponent.class)
public interface ScoreComponent {

    void inject(ScoreActivity activity);

    void inject(ScoreInfoActivity activity);

    void inject(ScoreStatsActivity activity);
}
