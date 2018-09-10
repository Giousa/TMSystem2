package com.zmm.tmsystem.mvp.presenter;

import com.zmm.tmsystem.bean.MoneyBean;
import com.zmm.tmsystem.bean.SpendingBean;
import com.zmm.tmsystem.mvp.presenter.contract.SpendingContract;
import com.zmm.tmsystem.rx.RxHttpResponseCompat;
import com.zmm.tmsystem.rx.subscriber.ErrorHandlerSubscriber;

import java.util.List;

import javax.inject.Inject;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/9/5
 * Email:65489469@qq.com
 */
public class SpendingPresenter extends BasePresenter<SpendingContract.ISpendingModel,SpendingContract.SpendingView> {


    @Inject
    public SpendingPresenter(SpendingContract.ISpendingModel model, SpendingContract.SpendingView view) {
        super(model, view);
    }

    public void getMoneyById(String moneyId) {
        mModel.getMoneyById(moneyId)
                .compose(RxHttpResponseCompat.<MoneyBean>compatResult())
                .subscribe(new ErrorHandlerSubscriber<MoneyBean>(mContext) {
                    @Override
                    public void onNext(MoneyBean moneyBean) {
                        mView.queryMoney(moneyBean);
                    }
                });
    }


    public void updateSpending(String moneyId, String title, String content, float pay, float deposit) {
        mModel.updateSpending(moneyId,title,content,pay,deposit)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>(mContext) {
                    @Override
                    public void onNext(String s) {
                        mView.updateSuccess();
                    }
                });
    }

    public void getSpendingListByMoneyId(String moneyId) {
        mModel.getSpendingListByMoneyId(moneyId)
                .compose(RxHttpResponseCompat.<List<SpendingBean>>compatResult())
                .subscribe(new ErrorHandlerSubscriber<List<SpendingBean>>(mContext) {
                    @Override
                    public void onNext(List<SpendingBean> spendingBeans) {
                        mView.queryAllSpendingList(spendingBeans);
                    }
                });
    }
}
