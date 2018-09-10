package com.zmm.tmsystem.mvp.presenter.contract;

import com.zmm.tmsystem.bean.BaseBean;
import com.zmm.tmsystem.bean.CertificatesBean;
import com.zmm.tmsystem.mvp.view.BaseView;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/9/4
 * Email:65489469@qq.com
 */
public interface CertificateInfoContract {

    interface ICertificateInfoModel{


        Observable<BaseBean<List<CertificatesBean>>> queryAllCertificates(String id);

        Observable<BaseBean<String>> deleteCertificate(String id);

        Observable<BaseBean<String>> uploadCertificatePics(String id, String title,String content, MultipartBody.Part [] part);
    }


    interface CertificateInfoView extends BaseView{

        void uploadSuccess();

        void querySuccess(List<CertificatesBean> certificatesBeans);

        void deleteSuccess();
    }
}
