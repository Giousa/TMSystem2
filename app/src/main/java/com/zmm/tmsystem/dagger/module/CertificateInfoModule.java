package com.zmm.tmsystem.dagger.module;

import com.zmm.tmsystem.dagger.scope.ActivityScope;
import com.zmm.tmsystem.http.ApiService;
import com.zmm.tmsystem.mvp.model.CertificateInfoModel;
import com.zmm.tmsystem.mvp.presenter.contract.CertificateInfoContract;

import dagger.Module;
import dagger.Provides;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/9/4
 * Email:65489469@qq.com
 */

@Module
public class CertificateInfoModule {

    private CertificateInfoContract.CertificateInfoView mCertificateInfoView;

    public CertificateInfoModule(CertificateInfoContract.CertificateInfoView certificateInfoView) {
        mCertificateInfoView = certificateInfoView;
    }

    @Provides
    public CertificateInfoContract.CertificateInfoView provideCertificateInfoView(){
        return mCertificateInfoView;
    }

    @Provides
    public CertificateInfoContract.ICertificateInfoModel provideCertificateInfoModel(ApiService apiService){
        return new CertificateInfoModel(apiService);
    }
}
