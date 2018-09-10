package com.zmm.tmsystem.dagger.component;

import com.zmm.tmsystem.dagger.module.TermModule;
import com.zmm.tmsystem.dagger.scope.ActivityScope;
import com.zmm.tmsystem.ui.activity.TermActivity;
import com.zmm.tmsystem.ui.activity.TermInfoActivity;
import com.zmm.tmsystem.ui.fragment.ManageFragment;

import dagger.Component;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/17
 * Time:下午7:45
 */
@ActivityScope
@Component(modules = TermModule.class,dependencies = AppComponent.class)
public interface TermComponent {

    void inject(TermActivity activity);

    void inject(TermInfoActivity activity);
}
