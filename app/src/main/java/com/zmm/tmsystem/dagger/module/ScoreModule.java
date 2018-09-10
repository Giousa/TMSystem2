package com.zmm.tmsystem.dagger.module;

import com.zmm.tmsystem.http.ApiService;
import com.zmm.tmsystem.mvp.model.ChildcareStudentModel;
import com.zmm.tmsystem.mvp.model.ScoreModel;
import com.zmm.tmsystem.mvp.presenter.contract.ChildcareStudentContract;
import com.zmm.tmsystem.mvp.presenter.contract.ScoreContract;

import dagger.Module;
import dagger.Provides;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/7/2
 * Time:上午11:54
 */

@Module
public class ScoreModule {

    private ScoreContract.ScoreView mChildcareStudentView;

    public ScoreModule(ScoreContract.ScoreView childcareStudentView) {
        mChildcareStudentView = childcareStudentView;
    }

    @Provides
    public ScoreContract.ScoreView provideScoreView(){
        return mChildcareStudentView;
    }

    @Provides
    public ScoreContract.IScoreModel provideScoreModel(ApiService apiService){
        return new ScoreModel(apiService);
    }
}
