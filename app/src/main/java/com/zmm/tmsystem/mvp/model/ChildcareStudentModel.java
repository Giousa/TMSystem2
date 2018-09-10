package com.zmm.tmsystem.mvp.model;

import com.zmm.tmsystem.bean.BaseBean;
import com.zmm.tmsystem.bean.ChildcareListBean;
import com.zmm.tmsystem.bean.ChildcareStudentBean;
import com.zmm.tmsystem.bean.MoneyBean;
import com.zmm.tmsystem.bean.SchoolBean;
import com.zmm.tmsystem.bean.StudentBean;
import com.zmm.tmsystem.bean.TermBean;
import com.zmm.tmsystem.http.ApiService;
import com.zmm.tmsystem.mvp.presenter.contract.ChildcareStudentContract.IChildcareStudentModel;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/7/2
 * Time:上午11:52
 */

public class ChildcareStudentModel implements IChildcareStudentModel {

    private ApiService mApiService;

    public ChildcareStudentModel(ApiService apiService) {
        mApiService = apiService;
    }

    @Override
    public Observable<BaseBean<List<ChildcareStudentBean>>> queryAllChildcareStudents(String id) {
        return mApiService.queryAllChildcareStudents(id);
    }

    @Override
    public Observable<BaseBean<ChildcareStudentBean>> updateChildcareStudent(int type,String id,int level,String content) {
        return mApiService.updateChildcareStudent(type,id,level,content);
    }

    @Override
    public Observable<BaseBean<String>> deleteChildcareStudent(String id) {
        return mApiService.deleteChildcareStudent(id);
    }

    @Override
    public Observable<BaseBean<ChildcareStudentBean>> findChildcareStudentById(String id) {
        return mApiService.findChildcareStudentById(id);
    }

    @Override
    public Observable<BaseBean<List<SchoolBean>>> querySchools() {
        return mApiService.querySchools();
    }

    @Override
    public Observable<BaseBean<String>> uploadPics(String t_id, String listJson, MultipartBody.Part[] part) {
        return mApiService.uploadPics(t_id,listJson,part);
    }

    @Override
    public Observable<BaseBean<StudentBean>> uploadChildcareStudentPic(String id, MultipartBody.Part file) {
        return mApiService.uploadChildcareStudentPic(id,file);
    }

    @Override
    public Observable<BaseBean<MoneyBean>> getMoneyByStudentId(String studentId) {
        return mApiService.getMoneyByStudentId(studentId);
    }

}
