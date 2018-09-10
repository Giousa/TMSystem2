package com.zmm.tmsystem.mvp.presenter.contract;

import com.zmm.tmsystem.bean.BaseBean;
import com.zmm.tmsystem.bean.ScoreBean;
import com.zmm.tmsystem.mvp.view.BaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/9/4
 * Email:65489469@qq.com
 */
public interface ScoreContract {

    interface IScoreModel {


        Observable<BaseBean<List<ScoreBean>>> queryAllScores(String studentId);

        Observable<BaseBean<ScoreBean>> queryScoreById(String id);

        Observable<BaseBean<String>> deleteScore(String id);

        Observable<BaseBean<String>> addScore(ScoreBean scoreBean);

        Observable<BaseBean<String>> updateScore(ScoreBean scoreBean);

    }


    interface ScoreView extends BaseView {

        void requestSuccess(String msg);

        void querySuccess(ScoreBean scoreBean);

        void queryAllScores(List<ScoreBean> scoreBeans);

    }
}
