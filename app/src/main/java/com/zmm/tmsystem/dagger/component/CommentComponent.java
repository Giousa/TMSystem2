package com.zmm.tmsystem.dagger.component;

import com.zmm.tmsystem.dagger.module.CommentModule;
import com.zmm.tmsystem.dagger.scope.ActivityScope;
import com.zmm.tmsystem.ui.fragment.CommentFragment;

import dagger.Component;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/8/20
 * Email:65489469@qq.com
 */
@ActivityScope
@Component(modules = CommentModule.class,dependencies = AppComponent.class)
public interface CommentComponent {

    void inject(CommentFragment fragment);
}
