package com.zmm.tmsystem.mvp.presenter.contract;

import com.zmm.tmsystem.bean.BaseBean;
import com.zmm.tmsystem.bean.TermBean;
import com.zmm.tmsystem.mvp.view.BaseView;

import java.util.List;

import io.reactivex.Observable;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/17
 * Time:下午7:22
 */

public interface TermContract {

    interface ITermModel{

        Observable<BaseBean<TermBean>> createNewTerm(TermBean termBean);

        Observable<BaseBean<List<TermBean>>> queryAllTerm(String tId);

        Observable<BaseBean<TermBean>> queryTermById(String id);

        Observable<BaseBean<TermBean>> updateTerm(TermBean termBean);

        Observable<BaseBean<String>> deleteTerm(String id);

    }

    interface TermView extends BaseView{

        void insertContentSuccess(int type,String content);
        void createSuccess(TermBean termBean);
        void updateSuccess(TermBean termBean);
        void deleteSuccess(String id);
        void getAllTerms(List<TermBean> list);

    }
}
