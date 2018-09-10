package com.zmm.tmsystem.dagger.module;

import com.zmm.tmsystem.http.ApiService;
import com.zmm.tmsystem.mvp.model.CommentModel;
import com.zmm.tmsystem.mvp.presenter.contract.CommentContract;

import dagger.Module;
import dagger.Provides;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/8/20
 * Email:65489469@qq.com
 */

@Module
public class CommentModule {


    private CommentContract.CommentView mCommentView;

    public CommentModule(CommentContract.CommentView commentView) {
        mCommentView = commentView;
    }

    @Provides
    public CommentContract.CommentView provideCommentView(){
        return  mCommentView;
    }


    @Provides
    public CommentContract.ICommentModel provideCommentModel(ApiService apiService){
        return new CommentModel(apiService);
    }

}
