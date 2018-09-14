package com.zmm.tmsystem.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.zmm.tmsystem.R;
import com.zmm.tmsystem.application.AppApplication;
import com.zmm.tmsystem.dagger.component.AppComponent;
import com.zmm.tmsystem.mvp.presenter.BasePresenter;
import com.zmm.tmsystem.mvp.view.BaseView;

import java.io.File;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.LoginStateChangeEvent;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

public abstract class ProgressFragment<T extends BasePresenter> extends Fragment  implements BaseView {



    private FrameLayout mRootView;

    private View mViewProgress;
    private FrameLayout mViewContent;
    private View mViewEmpty;
    private Unbinder mUnbinder;

    private TextView mTextError;

    protected AppApplication mApplication;


    @Inject
    T mPresenter ;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mRootView = (FrameLayout) inflater.inflate(R.layout.fragment_progress,container,false);
        mViewProgress = mRootView.findViewById(R.id.view_progress);
        mViewContent = (FrameLayout) mRootView.findViewById(R.id.view_content);
        mViewEmpty = mRootView.findViewById(R.id.view_empty);

        mTextError = (TextView) mRootView.findViewById(R.id.text_tip);

        mViewEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEmptyViewClick();
            }
        });
        //订阅接收消息,子类只要重写onEvent就能收到消息
//        JMessageClient.registerEventReceiver(this);

        return mRootView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.mApplication = (AppApplication) getActivity().getApplication();
        setupAcitivtyComponent(mApplication.getAppComponent());
        setRealContentView();

        init();
    }


    protected void startActivity(Class activity) {

        Intent intent = new Intent(getActivity(), activity);
        startActivity(intent);

    }

    /**
     * 空数据时，点击事件
     */
    protected void onEmptyViewClick(){

    }

    private void setRealContentView() {

        View realContentView=  LayoutInflater.from(getActivity()).inflate(setLayout(),mViewContent,true);
        mUnbinder=  ButterKnife.bind(this, realContentView);
    }


    protected void  showProgressView(){
        showView(R.id.view_progress);

    }

    protected void showContentView(){

        showView(R.id.view_content);
    }

    protected void showEmptyView(){


        showView(R.id.view_empty);
    }


    protected void showEmptyView(int resId){


        showEmptyView();
        mTextError.setText(resId);
    }

    protected void showEmptyView(String msg){


        showEmptyView();
        mTextError.setText(msg);
    }



    public void showView(int viewId){

        for(int i=0;i<mRootView.getChildCount();i++){

            if( mRootView.getChildAt(i).getId() == viewId){

                mRootView.getChildAt(i).setVisibility(View.VISIBLE);
            }
            else {
                mRootView.getChildAt(i).setVisibility(View.GONE);
            }

        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销消息接收
//        JMessageClient.unRegisterEventReceiver(this);
        if(mUnbinder != Unbinder.EMPTY){
            mUnbinder.unbind();
        }
    }

    @Override
    public void showLoading() {
        showProgressView();
    }

    @Override
    public void showError(String msg) {
        
        showEmptyView(msg);
    }

    @Override
    public void dismissLoading() {
        showContentView();
    }

    protected abstract int setLayout();

    protected abstract void setupAcitivtyComponent(AppComponent appComponent);

    protected abstract void init();



    public void onEventMainThread(LoginStateChangeEvent event) {
        final LoginStateChangeEvent.Reason reason = event.getReason();
        UserInfo myInfo = event.getMyInfo();
        if (myInfo != null) {
            JMessageClient.logout();
        }
        switch (reason) {
            case user_logout:

                break;
            case user_password_change:
                break;
        }
    }
}
