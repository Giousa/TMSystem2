package com.zmm.tmsystem.dagger.module;

import com.zmm.tmsystem.http.ApiService;
import com.zmm.tmsystem.mvp.model.TermModel;
import com.zmm.tmsystem.mvp.presenter.contract.TermContract;

import dagger.Module;
import dagger.Provides;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/17
 * Time:下午7:44
 */

@Module
public class TermModule {

    private TermContract.TermView mTermView;

    public TermModule(TermContract.TermView termView) {
        mTermView = termView;
    }

    @Provides
    public TermContract.TermView provideTermView(){
        return mTermView;
    }

    @Provides
    public TermContract.ITermModel provideTermModel(ApiService apiService){
        return new TermModel(apiService);
    }
}
