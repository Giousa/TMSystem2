package com.zmm.tmsystem.dagger.component;

import com.zmm.tmsystem.dagger.module.TeacherModule;
import com.zmm.tmsystem.dagger.scope.ActivityScope;
import com.zmm.tmsystem.ui.activity.TeacherInfoActivity;

import dagger.Component;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/15
 * Time:下午10:41
 */

@ActivityScope
@Component(modules = TeacherModule.class,dependencies = AppComponent.class)
public interface TeacherComponent {

    void inject(TeacherInfoActivity activity);
}
