package com.zmm.tmsystem.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.zmm.tmsystem.R;
import com.zmm.tmsystem.bean.MoneyBean;
import com.zmm.tmsystem.bean.SpendingBean;
import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.common.utils.DateUtils;
import com.zmm.tmsystem.common.utils.ToastUtils;
import com.zmm.tmsystem.dagger.component.AppComponent;
import com.zmm.tmsystem.dagger.component.DaggerSpendingComponent;
import com.zmm.tmsystem.dagger.module.SpendingModule;
import com.zmm.tmsystem.mvp.presenter.SpendingPresenter;
import com.zmm.tmsystem.mvp.presenter.contract.SpendingContract;
import com.zmm.tmsystem.ui.widget.CustomInfoItemView;
import com.zmm.tmsystem.ui.widget.TitleBar;

import java.text.ParseException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/9/5
 * Email:65489469@qq.com
 */
public class SpendActivity extends BaseActivity<SpendingPresenter> implements
        SpendingContract.SpendingView, Toolbar.OnMenuItemClickListener,
        CustomInfoItemView.OnItemClickListener {

    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.tv_money_surplus)
    TextView tvMoneySurplus;
    @BindView(R.id.tv_money_pay)
    TextView tvMoneyPay;
    @BindView(R.id.tv_money_pay_time)
    TextView tvMoneyPayTime;
    @BindView(R.id.tv_money_deposit)
    TextView tvMoneyDeposit;
    @BindView(R.id.tv_money_deposit_time)
    TextView tvMoneyDepositTime;
    @BindView(R.id.custom_item_pay)
    CustomInfoItemView customItemPay;
    @BindView(R.id.ll_pay)
    LinearLayout llPay;
    @BindView(R.id.ll_deposit)
    LinearLayout llDeposit;
    @BindView(R.id.tv_money_total_deposit)
    TextView tvMoneyTotalDeposit;
    @BindView(R.id.tv_money_total_pay)
    TextView tvMoneyTotalPay;


    private String mMoneyId;
    private MenuItem mMenuItemAdd;


    @Override
    protected int setLayout() {
        return R.layout.activity_spend;
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

        customItemPay.setOnItemClickListener(this,0);

        initToolBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getMoneyById(mMoneyId);

    }

    private void initToolBar() {
        //这里一定要加上，否则menu不显示
        setSupportActionBar(mTitleBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mTitleBar.setCenterTitle("消费余额");
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

        mTitleBar.setOnMenuItemClickListener(this);

    }

    @Override
    public void updateSuccess() {

    }

    @Override
    public void deleteSuccess() {

    }

    @Override
    public void queryAllSpendingList(List<SpendingBean> spendingBeans) {

    }

    @Override
    public void queryMoney(MoneyBean moneyBean) {
        System.out.println("当前消费明细:" + moneyBean);
        int active = moneyBean.getActive();
        if (active == 0) {
            llPay.setVisibility(View.GONE);
            llDeposit.setVisibility(View.GONE);
        } else {

            long createTime = moneyBean.getCreateTime();
            long lastDepositTime = moneyBean.getLastDepositTime();
            long lastPayTime = moneyBean.getLastPayTime();
            if (createTime == lastDepositTime) {
                llDeposit.setVisibility(View.GONE);
            } else {
                llDeposit.setVisibility(View.VISIBLE);
            }

            if (createTime == lastPayTime) {
                llPay.setVisibility(View.GONE);
            } else {
                llPay.setVisibility(View.VISIBLE);
            }

        }

        tvMoneySurplus.setText(moneyBean.getSurplus() + "");
        tvMoneyPay.setText(moneyBean.getLastPay() + "");
        tvMoneyDeposit.setText(moneyBean.getLastDeposit() + "");
        tvMoneyTotalDeposit.setText("总收入："+moneyBean.getTotalDeposit());
        tvMoneyTotalPay.setText("总支出："+moneyBean.getTotalPay());

        try {
            String depositTime = DateUtils.longToString(moneyBean.getLastDepositTime(), "yyyy-MM-dd HH:mm:ss");
            tvMoneyDepositTime.setText(depositTime);

            String payTime = DateUtils.longToString(moneyBean.getLastPayTime(), "yyyy-MM-dd HH:mm:ss");
            tvMoneyPayTime.setText(payTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionbar, menu);
        menu.findItem(R.id.menu_setting).setVisible(false);

        mMenuItemAdd = menu.findItem(R.id.menu_add);

        mMenuItemAdd.setIcon(new IconicsDrawable(this)
                .iconText("消费")
                .sizeDp(30)
                .color(getResources().getColor(R.color.white)
                ));

        mMenuItemAdd.setVisible(true);

        return true;
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {

        Intent intent = new Intent(this,SpendInfoActivity.class);
        intent.putExtra(Constant.MONEY_ID,mMoneyId);
        startActivity(intent);

        return false;
    }

    @Override
    public void itemClick(int type, String name) {
        Intent intent = new Intent(this,SpendDetailActivity.class);
        intent.putExtra(Constant.MONEY_ID,mMoneyId);
        startActivity(intent);
    }
}
