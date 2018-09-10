package com.zmm.tmsystem.dagger.component;

import com.zmm.tmsystem.dagger.module.ChildcareStudentModule;
import com.zmm.tmsystem.dagger.scope.FragmentScope;
import com.zmm.tmsystem.ui.activity.ChildcareStudentInfoActivity;
import com.zmm.tmsystem.ui.fragment.ManageFragment;

import dagger.Component;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/7/2
 * Time:上午11:56
 */

@FragmentScope
@Component(modules = ChildcareStudentModule.class,dependencies = AppComponent.class)
public interface ChildcareStudentComponent {

    void inject(ManageFragment fragment);

    void inject(ChildcareStudentInfoActivity activity);
}
