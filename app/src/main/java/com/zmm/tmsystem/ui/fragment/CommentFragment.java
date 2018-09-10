package com.zmm.tmsystem.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.zmm.tmsystem.R;
import com.zmm.tmsystem.bean.ChildcareStudentBean;
import com.zmm.tmsystem.bean.TermBean;
import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.common.utils.ACache;
import com.zmm.tmsystem.common.utils.ToastUtils;
import com.zmm.tmsystem.dagger.component.AppComponent;
import com.zmm.tmsystem.dagger.component.DaggerCommentComponent;
import com.zmm.tmsystem.dagger.module.CommentModule;
import com.zmm.tmsystem.mvp.presenter.CommentPresenter;
import com.zmm.tmsystem.mvp.presenter.contract.CommentContract;
import com.zmm.tmsystem.rx.RxBus;
import com.zmm.tmsystem.ui.adapter.CommentAdapter;

import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/15
 * Time:上午10:02
 */

public class CommentFragment extends ProgressFragment<CommentPresenter> implements CommentContract.CommentView {

    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.empty)
    RelativeLayout empty;

    private ACache mACache;
    private CommentAdapter mCommentAdapter;
    private TermBean mTermBean;


    @Override
    protected int setLayout() {
        return R.layout.fragment_comment;
    }

    @Override
    protected void setupAcitivtyComponent(AppComponent appComponent) {
        DaggerCommentComponent.builder()
                .appComponent(appComponent)
                .commentModule(new CommentModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void init() {

        mACache = ACache.get(getActivity());
        mTermBean = (TermBean) mACache.getAsObject(Constant.TERM);


        initData();

        operateBus();
    }


    private void initData() {

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mCommentAdapter = new CommentAdapter(getActivity());
        mCommentAdapter.setOnRatingBarClickListener(new CommentAdapter.OnRatingBarClickListener() {
            @Override
            public void OnRatingBarClick(String id, float rating) {
                System.out.println("id = " + id);
                System.out.println("rating = " + rating);

                mPresenter.addComments(id, rating);
            }
        });

        mRecyclerView.setAdapter(mCommentAdapter);

        if (mTermBean != null) {
            mPresenter.queryTodayStudents(mTermBean.getId());
        }


    }

    @Override
    public void queryTodaySuccess(List<ChildcareStudentBean> childcareStudentBeans) {

        if(empty != null){
            if(childcareStudentBeans != null && childcareStudentBeans.size() > 0){
                empty.setVisibility(View.GONE);
            }else {
                empty.setVisibility(View.VISIBLE);
            }
        }

        mCommentAdapter.setNewData(childcareStudentBeans);
    }


    @Override
    public void commentSuccess(String msg) {

    }

    @Override
    public void commentFailure() {
        ToastUtils.SimpleToast(getActivity(), "评论失败，请稍后再试！");
    }


    /**
     * RxBus  这里是更新选中的托管项目
     */
    private void operateBus() {
        RxBus.getDefault().toObservable()
                .map(new Function<Object, String>() {
                    @Override
                    public String apply(Object o) throws Exception {
                        return (String) o;
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (!TextUtils.isEmpty(s) && s.equals(Constant.ITEM_COMMENTS)) {
                            if (mTermBean != null) {
                                mPresenter.queryTodayStudents(mTermBean.getId());
                            }
                        }
                    }
                });
    }


}
