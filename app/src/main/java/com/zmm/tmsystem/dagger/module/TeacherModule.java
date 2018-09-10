package com.zmm.tmsystem.dagger.module;

import com.zmm.tmsystem.http.ApiService;
import com.zmm.tmsystem.mvp.model.HomeModel;
import com.zmm.tmsystem.mvp.model.TeacherModel;
import com.zmm.tmsystem.mvp.presenter.contract.HomeContract;
import com.zmm.tmsystem.mvp.presenter.contract.TeacherContract;

import dagger.Module;
import dagger.Provides;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/14
 * Time:下午11:48
 */

@Module
public class TeacherModule {

    private TeacherContract.TeacherView mTeacherView;

    public TeacherModule(TeacherContract.TeacherView teacherView) {
        mTeacherView = teacherView;
    }

    @Provides
    public TeacherContract.TeacherView provideTeacherView(){
        return mTeacherView;
    }

    @Provides
    public TeacherContract.ITeacherModel provideTeacherModel(ApiService apiService){
        return new TeacherModel(apiService);
    }
}
