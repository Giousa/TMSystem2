package com.zmm.tmsystem.dagger.module;

import com.zmm.tmsystem.http.ApiService;
import com.zmm.tmsystem.mvp.model.StudentModel;
import com.zmm.tmsystem.mvp.presenter.contract.StudentContract;

import dagger.Module;
import dagger.Provides;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/20
 * Time:下午2:18
 */

@Module
public class StudentModule {

    private StudentContract.StudentView mStudentView;

    public StudentModule(StudentContract.StudentView studentView) {
        mStudentView = studentView;
    }

    @Provides
    public StudentContract.StudentView provideStudentView(){
        return mStudentView;
    }


    @Provides
    public StudentContract.IStudentModel provideStudentModel(ApiService apiService){
        return new StudentModel(apiService);
    }
}
