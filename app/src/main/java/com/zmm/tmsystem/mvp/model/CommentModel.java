package com.zmm.tmsystem.mvp.model;

import com.zmm.tmsystem.bean.BaseBean;
import com.zmm.tmsystem.bean.ChildcareStudentBean;
import com.zmm.tmsystem.http.ApiService;
import com.zmm.tmsystem.mvp.presenter.contract.CommentContract;

import java.util.List;

import io.reactivex.Observable;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/8/20
 * Email:65489469@qq.com
 */
public class CommentModel implements CommentContract.ICommentModel {

    private ApiService mApiService;


    public CommentModel(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Observable<BaseBean<List<ChildcareStudentBean>>> queryToday(String id) {
        return mApiService.queryToday(id);
    }

    @Override
    public Observable<BaseBean<String>> addCommentStudent(String s_id, int level) {
        return mApiService.addCommentStudent(s_id,level);
    }
}
