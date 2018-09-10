package com.zmm.tmsystem.mvp.presenter;

import com.lzy.imagepicker.bean.ImageItem;
import com.zmm.tmsystem.bean.CertificatesBean;
import com.zmm.tmsystem.mvp.presenter.contract.CertificateInfoContract;
import com.zmm.tmsystem.rx.RxHttpResponseCompat;
import com.zmm.tmsystem.rx.subscriber.ErrorHandlerSubscriber;
import com.zmm.tmsystem.rx.subscriber.ProgressSubcriber;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/9/4
 * Email:65489469@qq.com
 */
public class CertificateInfoPresenter extends BasePresenter<CertificateInfoContract.ICertificateInfoModel,CertificateInfoContract.CertificateInfoView>{


    @Inject
    public CertificateInfoPresenter(CertificateInfoContract.ICertificateInfoModel model, CertificateInfoContract.CertificateInfoView view) {
        super(model, view);
    }

    public void uploadMultyPics(String childcareStudentId, String title, String content, ArrayList<String> images) {

        MultipartBody.Part [] part = new MultipartBody.Part[images.size()];

        for (int i = 0; i < images.size(); i++) {
            File file= new File(images.get(i));
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            System.out.println("图片名称file.getName() = "+file.getName());
            part[i] = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        }

        mModel.uploadCertificatePics(childcareStudentId,title,content,part)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber(mContext) {

                    @Override
                    public void onSubscribe(Disposable d) {
                        mView.showLoading();
                    }

                    @Override
                    public void onNext(Object o) {
                        mView.uploadSuccess();
                    }

                    @Override
                    public void onComplete() {
                        mView.dismissLoading();
                    }

                });
    }

    /**
     * 获取所有
     * @param childcareStudentId
     */
    public void queryAllCertificates(String childcareStudentId) {

        mModel.queryAllCertificates(childcareStudentId)
                .compose(RxHttpResponseCompat.<List<CertificatesBean>>compatResult())
                .subscribe(new ErrorHandlerSubscriber<List<CertificatesBean>>(mContext) {
                    @Override
                    public void onNext(List<CertificatesBean> certificatesBeans) {
                        mView.querySuccess(certificatesBeans);
                    }
                });

    }
}
