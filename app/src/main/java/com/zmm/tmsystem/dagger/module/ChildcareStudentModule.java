package com.zmm.tmsystem.dagger.module;

import com.zmm.tmsystem.http.ApiService;
import com.zmm.tmsystem.mvp.model.ChildcareStudentModel;
import com.zmm.tmsystem.mvp.presenter.contract.ChildcareStudentContract;

import dagger.Module;
import dagger.Provides;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/7/2
 * Time:上午11:54
 */

@Module
public class ChildcareStudentModule {

    private ChildcareStudentContract.ChildcareStudentView mChildcareStudentView;

    public ChildcareStudentModule(ChildcareStudentContract.ChildcareStudentView childcareStudentView) {
        mChildcareStudentView = childcareStudentView;
    }

    @Provides
    public ChildcareStudentContract.ChildcareStudentView provideChildcareStudentView(){
        return mChildcareStudentView;
    }

    @Provides
    public ChildcareStudentContract.IChildcareStudentModel provideChildcareStudentModel(ApiService apiService){

        return new ChildcareStudentModel(apiService);
    }
}
