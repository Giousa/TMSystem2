package com.zmm.tmsystem.mvp.presenter.contract;

import com.zmm.tmsystem.bean.BaseBean;
import com.zmm.tmsystem.bean.CertificatesBean;
import com.zmm.tmsystem.bean.MoneyBean;
import com.zmm.tmsystem.bean.SpendingBean;
import com.zmm.tmsystem.mvp.view.BaseView;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/9/4
 * Email:65489469@qq.com
 */
public interface SpendingContract {

    interface ISpendingModel{


        Observable<BaseBean<List<SpendingBean>>> getSpendingListByMoneyId(String moneyId);

        Observable<BaseBean<String>> deleteSpending(String spendingId, float pay, float deposit);

        Observable<BaseBean<String>> updateSpending(String moneyId, String title,String content,float pay, float deposit);

        Observable<BaseBean<MoneyBean>> getMoneyById(String moneyId);

    }


    interface SpendingView extends BaseView{

        void updateSuccess();

        void deleteSuccess();

        void queryAllSpendingList(List<SpendingBean> spendingBeans);

        void queryMoney(MoneyBean moneyBean);
    }
}
