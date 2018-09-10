package com.zmm.tmsystem.mvp.presenter;

import android.widget.LinearLayout;

import com.zmm.tmsystem.R;
import com.zmm.tmsystem.bean.TermBean;
import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.common.utils.ACache;
import com.zmm.tmsystem.mvp.presenter.contract.TermContract;
import com.zmm.tmsystem.rx.RxHttpResponseCompat;
import com.zmm.tmsystem.rx.subscriber.ErrorHandlerSubscriber;
import com.zmm.tmsystem.ui.widget.SingleSelectView;
import com.zmm.tmsystem.ui.widget.TermInfoDialog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/17
 * Time:下午7:43
 */

public class TermPresenter extends BasePresenter<TermContract.ITermModel,TermContract.TermView> {


    private LinearLayout mRootView;
    private int mScreenWidth;
    private List<String> mList;
    private String title = null;
    private int type;


    @Inject
    public TermPresenter(TermContract.ITermModel model, TermContract.TermView view) {
        super(model, view);
    }

    /**
     * 查询所有托管周期
     */
    public void queryAllTerm() {
        ACache aCache = ACache.get(mContext);
        String tId = aCache.getAsString(Constant.TEACHER_ID);

        mModel.queryAllTerm(tId)
                .compose(RxHttpResponseCompat.<List<TermBean>>compatResult())
                .subscribe(new ErrorHandlerSubscriber<List<TermBean>>(mContext) {

                    @Override
                    public void onNext(List<TermBean> list) {
                        mView.getAllTerms(list);
                    }
                });
    }



    /**
     * 填充内容
     * @param typeTerm
     * @param rootView
     * @param screenWidth
     */
    public void insertContent(int typeTerm, LinearLayout rootView, int screenWidth) {
        mRootView = rootView;
        mScreenWidth = screenWidth;
        type = typeTerm;
        if(mList != null){
            mList.clear();
            mList = null;
        }

        switch (typeTerm){
            case Constant.TYPE_TERM_YEAR:
                title = "年份";
                mList = new ArrayList<>();
                mList.add("2018年");
                mList.add("2019年");
                mList.add("2020年");
                mList.add("2021年");
                mList.add("2022年");

                selectString();
                break;
            case Constant.TYPE_TERM_MONTH:
                title = "月份";
                mList = new ArrayList<>();
                mList.add("1月");
                mList.add("2月");
                mList.add("3月");
                mList.add("4月");
                mList.add("5月");
                mList.add("6月");
                mList.add("7月");
                mList.add("8月");
                mList.add("9月");
                mList.add("10月");
                mList.add("11月");
                mList.add("12月");

                selectString();
                break;
            case Constant.TYPE_TERM_TERM:
                title = "学期";
                mList = new ArrayList<>();
                mList.add("上学期");
                mList.add("下学期");
                mList.add("暑假");
                mList.add("寒假");
                mList.add("晚托班");
                mList.add("走读");

                selectString();
                break;
        }

    }

    /**
     * 选择框修改
     */
    private void selectString() {


        SingleSelectView singleSelectView = new SingleSelectView(mContext,mRootView,mScreenWidth,title,mList);

        singleSelectView.setOnSelectClickListener(new SingleSelectView.OnSelectClickListener() {
            @Override
            public void onCancel() {

                mView.dismissLoading();
            }

            @Override
            public void onConfirm(int position,String content) {
                mView.dismissLoading();
                mView.insertContentSuccess(type,content);
            }
        });

        mView.showLoading();
    }

    /**
     * 添加托管周期
     * @param termBean
     */
    public void createTerm(TermBean termBean) {
        mModel.createNewTerm(termBean)
                .compose(RxHttpResponseCompat.<TermBean>compatResult())
                .subscribe(new ErrorHandlerSubscriber<TermBean>(mContext) {

                    @Override
                    public void onNext(TermBean termBean) {
                        mView.createSuccess(termBean);
                    }

                });
    }

    /**
     * 更新数据
     * @param termBean
     */
    public void updateTerm(TermBean termBean) {

        mModel.updateTerm(termBean)
                .compose(RxHttpResponseCompat.<TermBean>compatResult())
                .subscribe(new ErrorHandlerSubscriber<TermBean>(mContext) {

                    @Override
                    public void onNext(TermBean termBean) {
                        mView.dismissLoading();
                        mView.updateSuccess(termBean);

                    }

                });
    }


    /**
     * 删除托管周期
     * @param id
     */
    public void delete(final String id) {

        mModel.deleteTerm(id)
                .compose(RxHttpResponseCompat.<String>compatResult())
                .subscribe(new ErrorHandlerSubscriber<String>(mContext) {

                    @Override
                    public void onNext(String s) {
                        mView.deleteSuccess(id);
                    }

                });
    }
}
