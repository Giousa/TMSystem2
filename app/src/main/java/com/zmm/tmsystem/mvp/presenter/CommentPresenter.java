package com.zmm.tmsystem.mvp.presenter;

import com.zmm.tmsystem.bean.ChildcareStudentBean;
import com.zmm.tmsystem.mvp.presenter.contract.CommentContract;
import com.zmm.tmsystem.rx.RxHttpResponseCompat;
import com.zmm.tmsystem.rx.subscriber.ErrorHandlerSubscriber;

import java.util.List;

import javax.inject.Inject;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/8/20
 * Email:65489469@qq.com
 */
public class CommentPresenter extends BasePresenter<CommentContract.ICommentModel,CommentContract.CommentView> {


    @Inject
    public CommentPresenter(CommentContract.ICommentModel model, CommentContract.CommentView view) {
        super(model, view);
    }

    /**
     * 获取当前管理周期评价学生
     * @param id
     */
    public void queryTodayStudents(String id) {
        mModel.queryToday(id)
                .compose(RxHttpResponseCompat.<List<ChildcareStudentBean>>compatResult())
                .subscribe(new ErrorHandlerSubscriber<List<ChildcareStudentBean>>(mContext) {
                    @Override
                    public void onNext(List<ChildcareStudentBean> childcareStudentBeans) {
                        mView.queryTodaySuccess(childcareStudentBeans);
                    }
                });
    }

    /**
     * 添加或修改评论
     * @param id
     * @param rating
     */
    public void addComments(String id, float rating) {
        mModel.addCommentStudent(id,(int)(rating*2))
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>(mContext) {
                    @Override
                    public void onNext(String s) {
                        mView.commentSuccess(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.commentFailure();
                    }
                });
    }
}
