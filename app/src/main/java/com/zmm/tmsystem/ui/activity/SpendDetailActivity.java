package com.zmm.tmsystem.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.zmm.tmsystem.R;
import com.zmm.tmsystem.bean.MoneyBean;
import com.zmm.tmsystem.bean.SpendingBean;
import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.dagger.component.AppComponent;
import com.zmm.tmsystem.dagger.component.DaggerSpendingComponent;
import com.zmm.tmsystem.dagger.module.SpendingModule;
import com.zmm.tmsystem.mvp.presenter.SpendingPresenter;
import com.zmm.tmsystem.mvp.presenter.contract.SpendingContract;
import com.zmm.tmsystem.ui.adapter.SpendingAdapter;
import com.zmm.tmsystem.ui.widget.TitleBar;

import java.util.List;

import butterknife.BindView;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/9/5
 * Email:65489469@qq.com
 */
public class SpendDetailActivity extends BaseActivity<SpendingPresenter> implements SpendingContract.SpendingView {

    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.empty)
    RelativeLayout mEmpty;
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;

    private String mMoneyId;
    private SpendingAdapter mSpendingAdapter;


    @Override
    protected int setLayout() {
        return R.layout.activity_spend_detail;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerSpendingComponent.builder()
                .appComponent(appComponent)
                .spendingModule(new SpendingModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void init() {
        mMoneyId = this.getIntent().getStringExtra(Constant.MONEY_ID);

        initToolBar();

        initListData();
    }

    private void initToolBar() {
        //这里一定要加上，否则menu不显示
        setSupportActionBar(mTitleBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mTitleBar.setCenterTitle("消费明细");
        mTitleBar.setNavigationIcon(new IconicsDrawable(this)
                .icon(Ionicons.Icon.ion_android_arrow_back)
                .sizeDp(20)
                .color(getResources().getColor(R.color.white)
                ));
        mTitleBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initListData() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mSpendingAdapter = new SpendingAdapter(this);

        mRecyclerView.setAdapter(mSpendingAdapter);

        mPresenter.getSpendingListByMoneyId(mMoneyId);
    }

    @Override
    public void updateSuccess() {

    }

    @Override
    public void deleteSuccess() {

    }

    @Override
    public void queryAllSpendingList(List<SpendingBean> spendingBeans) {
        if(spendingBeans != null && spendingBeans.size() > 0){
            mEmpty.setVisibility(View.GONE);
        }else {
            mEmpty.setVisibility(View.VISIBLE);
        }
        mSpendingAdapter.setNewData(spendingBeans);
    }

    @Override
    public void queryMoney(MoneyBean moneyBean) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void dismissLoading() {

    }
}
