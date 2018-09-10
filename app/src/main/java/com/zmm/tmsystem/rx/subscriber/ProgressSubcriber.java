package com.zmm.tmsystem.rx.subscriber;

import android.content.Context;

import com.zmm.tmsystem.common.exception.BaseException;
import com.zmm.tmsystem.mvp.view.BaseView;

import io.reactivex.disposables.Disposable;



public abstract class ProgressSubcriber<T> extends ErrorHandlerSubscriber<T>  {




    private BaseView mView;


    public ProgressSubcriber(Context context, BaseView view) {
        super(context);
        this.mView = view;

    }


    public boolean isShowProgress(){
        return true;
    }



    @Override
    public void onSubscribe(Disposable d) {

        if(isShowProgress()){
            mView.showLoading();
        }

    }

    @Override
    public void onComplete() {

        mView.dismissLoading();
    }

    @Override
    public void onError(Throwable e) {

        e.printStackTrace();
        BaseException baseException =  mRxErrorHandler.handlerError(e);

        //界面会显示，异常信息
        mView.showError(baseException.getDisplayMessage());

    }

}
