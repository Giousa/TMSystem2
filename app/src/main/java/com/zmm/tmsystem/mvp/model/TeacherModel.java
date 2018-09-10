package com.zmm.tmsystem.mvp.model;

import com.zmm.tmsystem.bean.BaseBean;
import com.zmm.tmsystem.bean.SchoolBean;
import com.zmm.tmsystem.bean.TeacherBean;
import com.zmm.tmsystem.http.ApiService;
import com.zmm.tmsystem.mvp.presenter.contract.TeacherContract;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/15
 * Time:下午10:32
 */

public class TeacherModel implements TeacherContract.ITeacherModel {


    private ApiService mApiService;

    public TeacherModel(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Observable<BaseBean<TeacherBean>> updateTeacherInfo(TeacherBean teacherBean) {
        return mApiService.updateTeacherInfo(teacherBean);
    }


    @Override
    public Observable<BaseBean<TeacherBean>> updateTeacherByType(String id, int type, String content) {
        return mApiService.updateTeacherByType(id,type,content);
    }

    @Override
    public Observable<BaseBean<List<SchoolBean>>> querySchools() {
        return mApiService.querySchools();
    }

    @Override
    public Observable<BaseBean<TeacherBean>> uploadTeacherPic(String id, MultipartBody.Part file) {
        return mApiService.uploadTeacherPic(id,file);
    }

}
