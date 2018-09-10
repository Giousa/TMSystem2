package com.zmm.tmsystem.mvp.presenter.contract;

import com.zmm.tmsystem.bean.BaseBean;
import com.zmm.tmsystem.bean.ChildcareStudentBean;
import com.zmm.tmsystem.mvp.view.BaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/8/10
 * Email:65489469@qq.com
 */
public interface CommentContract {


    interface ICommentModel{

        /**
         * 获取当天学生评价情况
         * @param id
         * @return
         */
        Observable<BaseBean<List<ChildcareStudentBean>>> queryToday(String id);


        /**
         * 评价学生
         * @param s_id
         * @param level
         * @return
         */
        Observable<BaseBean<String>> addCommentStudent(String s_id,int level);


    }

    interface CommentView extends BaseView{


        void queryTodaySuccess(List<ChildcareStudentBean> childcareStudentBeans);

        void commentSuccess(String msg);

        void commentFailure();

    }
}
