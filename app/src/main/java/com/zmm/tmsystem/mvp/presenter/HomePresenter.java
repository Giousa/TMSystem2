package com.zmm.tmsystem.mvp.presenter;

import android.text.TextUtils;

import com.zmm.tmsystem.R;
import com.zmm.tmsystem.bean.StatisticsBean;
import com.zmm.tmsystem.bean.TeacherBean;
import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.common.utils.ACache;
import com.zmm.tmsystem.common.utils.ToastUtils;
import com.zmm.tmsystem.mvp.presenter.contract.HomeContract;
import com.zmm.tmsystem.rx.RxHttpResponseCompat;
import com.zmm.tmsystem.rx.subscriber.ErrorHandlerSubscriber;
import com.zmm.tmsystem.rx.subscriber.ProgressSubcriber;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/14
 * Time:下午11:51
 */

public class HomePresenter extends BasePresenter<HomeContract.IHomeModel,HomeContract.HomeView> {


    @Inject
    public HomePresenter(HomeContract.IHomeModel model, HomeContract.HomeView view) {
        super(model, view);
    }


    public void getTeacherById(String id){

        mModel.getTeacherById(id)
                .compose(RxHttpResponseCompat.<TeacherBean>compatResult())
                .subscribe(new ProgressSubcriber<TeacherBean>(mContext,mView) {
                    @Override
                    public void onNext(TeacherBean teacherBean) {
                        mView.showTeacherInfo(teacherBean);
                    }

                });


    }

//    public void getSignInfo(){
//
//        final ACache aCache = ACache.get(mContext);
//
//        String tId = aCache.getAsString(Constant.TEACHER_ID);
//
//        mModel.signInfo(tId)
//                .compose(RxHttpResponseCompat.<String>compatResult())
//                .subscribe(new ErrorHandlerSubscriber<String>(mContext) {
//
//                    @Override
//                    public void onNext(String s) {
//                        if(s.equals(mContext.getResources().getString(R.string.home_head_sign))){
//                            aCache.put(Constant.SIGN,"");
//                        }else {
//                            aCache.put(Constant.SIGN,"sign");
//                        }
//                        mView.signInfoSuccess(s);
//                    }
//                });
//
//    }
//
//    public void sign(){
//
//        final ACache aCache = ACache.get(mContext);
//
//        String signStr = aCache.getAsString(Constant.SIGN);
//
//        if(!TextUtils.isEmpty(signStr)){
//            mView.signExist();
//            return;
//        }
//
//
//        String tId = aCache.getAsString(Constant.TEACHER_ID);
//
//        mModel.sign(tId)
//                .compose(RxHttpResponseCompat.<String>compatResult())
//                .subscribe(new ErrorHandlerSubscriber<String>(mContext) {
//
//                    @Override
//                    public void onNext(String s) {
//                        mView.signSuccess();
//                        aCache.put(Constant.SIGN,"sign");
//                    }
//
//                });
//
//    }


    /**
     * 统计数据
     * @param id
     */
    public void getStatisticsInfo(String id) {
        mModel.getStatistics(id)
                .compose(RxHttpResponseCompat.<StatisticsBean>compatResult())
                .subscribe(new ErrorHandlerSubscriber<StatisticsBean>(mContext) {
                    @Override
                    public void onNext(StatisticsBean statisticsBean) {
                        mView.statistics(statisticsBean);
                    }
                });
    }
}
