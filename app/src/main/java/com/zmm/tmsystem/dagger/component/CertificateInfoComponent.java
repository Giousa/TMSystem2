package com.zmm.tmsystem.dagger.component;

import com.zmm.tmsystem.dagger.module.CertificateInfoModule;
import com.zmm.tmsystem.dagger.scope.ActivityScope;
import com.zmm.tmsystem.ui.activity.CertificateActivity;
import com.zmm.tmsystem.ui.activity.CertificateInfoActivity;

import dagger.Component;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/9/4
 * Email:65489469@qq.com
 */
@ActivityScope
@Component(modules = CertificateInfoModule.class,dependencies = AppComponent.class)
public interface CertificateInfoComponent {

    void inject(CertificateInfoActivity activity);

    void inject(CertificateActivity activity);
}
