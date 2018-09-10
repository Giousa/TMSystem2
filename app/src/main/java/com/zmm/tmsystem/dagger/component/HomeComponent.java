package com.zmm.tmsystem.dagger.component;

import com.zmm.tmsystem.dagger.module.HomeModule;
import com.zmm.tmsystem.dagger.scope.ActivityScope;
import com.zmm.tmsystem.ui.activity.TeacherInfoActivity;
import com.zmm.tmsystem.ui.fragment.HomeFragment;

import dagger.Component;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/14
 * Time:下午11:50
 */
@ActivityScope
@Component(modules = HomeModule.class,dependencies = AppComponent.class)
public interface HomeComponent {

    void inject(HomeFragment fragment);
}
