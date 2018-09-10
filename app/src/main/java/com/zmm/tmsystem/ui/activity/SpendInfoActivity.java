package com.zmm.tmsystem.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.zmm.tmsystem.R;
import com.zmm.tmsystem.bean.MoneyBean;
import com.zmm.tmsystem.bean.SpendingBean;
import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.common.utils.CheckUtils;
import com.zmm.tmsystem.common.utils.ToastUtils;
import com.zmm.tmsystem.dagger.component.AppComponent;
import com.zmm.tmsystem.dagger.component.DaggerSpendingComponent;
import com.zmm.tmsystem.dagger.module.SpendingModule;
import com.zmm.tmsystem.mvp.presenter.SpendingPresenter;
import com.zmm.tmsystem.mvp.presenter.contract.SpendingContract;
import com.zmm.tmsystem.ui.widget.CustomPayItemView;
import com.zmm.tmsystem.ui.widget.TitleBar;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/9/5
 * Email:65489469@qq.com
 */
public class SpendInfoActivity extends BaseActivity<SpendingPresenter> implements CustomPayItemView.OnTagClickListener,SpendingContract.SpendingView {

    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.custom_item_pay_view)
    CustomPayItemView customItemPayView;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.rb_1)
    RadioButton rb1;
    @BindView(R.id.rb_2)
    RadioButton rb2;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;

    private String mMoneyId;
    private int flag = 0;

    @Override
    protected int setLayout() {
        return R.layout.activity_spend_info;
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

        initToolBar();


        mMoneyId = this.getIntent().getStringExtra(Constant.MONEY_ID);

        customItemPayView.setOnTagClickListener(this);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId == R.id.rb_1){
                    flag = 1;
                }else {
                    flag = 2;
                }
            }
        });

    }

    private void initToolBar() {
        //这里一定要加上，否则menu不显示
        setSupportActionBar(mTitleBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mTitleBar.setCenterTitle("消费计算");
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


    @OnClick(R.id.btn_select_confirm)
    public void onViewClicked() {

        if(flag == 0){
            ToastUtils.SimpleToast(this,"请选择支出或者收入");
            return;
        }


        String moneyValue = etMoney.getText().toString();
        String content = etContent.getText().toString();

        if (!CheckUtils.isOnlyPointNumber(moneyValue)) {
            ToastUtils.SimpleToast(this, "金额格式不正确");
            return;
        }

        if(TextUtils.isEmpty(content)){
            ToastUtils.SimpleToast(this,"请选择或者输入描述");
            return;
        }


        float v = Float.parseFloat(moneyValue);
        float pay;
        float deposit;
        if(flag == 1){
            //支出
            pay = v;
            deposit = 0;
        }else {
            //收入
            pay = 0;
            deposit = v;
        }

        mPresenter.updateSpending(mMoneyId,"",content,pay,deposit);

    }

    @Override
    public void OnTagClick(String name) {
        etContent.setText(name);
        etContent.setSelection(name.length());
    }

    @Override
    public void updateSuccess() {
        finish();
    }

    @Override
    public void deleteSuccess() {

    }

    @Override
    public void queryAllSpendingList(List<SpendingBean> spendingBeans) {

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
