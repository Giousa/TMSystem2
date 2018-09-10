package com.zmm.tmsystem.dagger.component;

import com.zmm.tmsystem.dagger.module.LoginModule;
import com.zmm.tmsystem.dagger.scope.ActivityScope;
import com.zmm.tmsystem.ui.activity.LoginActivity;

import dagger.Component;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/13
 * Time:下午11:03
 */

@ActivityScope
@Component(modules = LoginModule.class,dependencies = AppComponent.class)
public interface LoginComponent {

    void inject(LoginActivity activity);
}
