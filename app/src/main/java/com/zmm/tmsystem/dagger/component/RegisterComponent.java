package com.zmm.tmsystem.dagger.component;

import com.zmm.tmsystem.dagger.module.RegisterModule;
import com.zmm.tmsystem.dagger.scope.ActivityScope;
import com.zmm.tmsystem.ui.activity.ModifyActivity;
import com.zmm.tmsystem.ui.activity.RegisterActivity;

import dagger.Component;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/14
 * Time:下午2:35
 */

@ActivityScope
@Component(modules = RegisterModule.class,dependencies = AppComponent.class)
public interface RegisterComponent {

    void inject(RegisterActivity activity);

    void inject(ModifyActivity activity);
}
