package com.zmm.tmsystem.dagger.component;

import com.zmm.tmsystem.dagger.module.SpendingModule;
import com.zmm.tmsystem.dagger.scope.ActivityScope;
import com.zmm.tmsystem.ui.activity.SpendActivity;
import com.zmm.tmsystem.ui.activity.SpendDetailActivity;
import com.zmm.tmsystem.ui.activity.SpendInfoActivity;

import dagger.Component;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/9/5
 * Email:65489469@qq.com
 */
@ActivityScope
@Component(modules = SpendingModule.class,dependencies = AppComponent.class)
public interface SpendingComponent {

    void inject(SpendActivity activity);

    void inject(SpendInfoActivity activity);

    void inject(SpendDetailActivity activity);
}
