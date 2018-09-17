package com.zmm.tmsystem.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.zmm.tmsystem.R;
import com.zmm.tmsystem.bean.TermBean;
import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.common.utils.ACache;
import com.zmm.tmsystem.dagger.component.AppComponent;
import com.zmm.tmsystem.dagger.component.DaggerTermComponent;
import com.zmm.tmsystem.dagger.module.TermModule;
import com.zmm.tmsystem.mvp.presenter.TermPresenter;
import com.zmm.tmsystem.mvp.presenter.contract.TermContract;
import com.zmm.tmsystem.rx.RxBus;
import com.zmm.tmsystem.ui.adapter.TermAdapter;
import com.zmm.tmsystem.ui.widget.TitleBar;

import java.util.List;

import butterknife.BindView;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/17
 * Time:下午2:34
 */

public class TermActivity extends BaseActivity<TermPresenter> implements TermContract.TermView, Toolbar.OnMenuItemClickListener {

    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;

    private MenuItem mMenuItemAdd;
    private TermAdapter mTermAdapter;
    private ACache mACache;
    private String mTermId;
    private TermBean mTermBeanCache;


    @Override
    protected int setLayout() {
        return R.layout.activity_term;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerTermComponent.builder()
                .appComponent(appComponent)
                .termModule(new TermModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void init() {

        mACache = ACache.get(this);
        mTermBeanCache = (TermBean) mACache.getAsObject(Constant.TERM);

        if(mTermBeanCache == null){
            mTermId = null;
        }else {
            mTermId = mTermBeanCache.getId();
        }

        initToolBar();

        initData();

        operateBus();

    }

    private void initToolBar() {

        //这里一定要加上，否则menu不显示
        setSupportActionBar(mTitleBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mTitleBar.setCenterTitle("托管周期管理");
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


    private void initData() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mTermAdapter = new TermAdapter(this,mTermId);
        mTermAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(TermActivity.this,TermInfoActivity.class);
                intent.putExtra(Constant.TERM_CLICKED,mTermAdapter.getItem(position));
                startActivity(intent);

            }
        });

        mTermAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                TermBean termBean = mTermAdapter.getItem(position);
                mACache.put(Constant.TERM,termBean);
                mTermAdapter.setChecked(termBean.getId());

                RxBus.getDefault().post(Constant.UPDATE_TITLE);
            }
        });

        mRecyclerView.setAdapter(mTermAdapter);

        mPresenter.queryAllTerm();
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
    public void insertContentSuccess(int type, String content) {

    }

    @Override
    public void createSuccess(TermBean termBean) {
    }

    @Override
    public void updateSuccess(TermBean termBean) {

    }

    @Override
    public void deleteSuccess(String id) {

    }

    @Override
    public void getAllTerms(List<TermBean> list) {

        mTermAdapter.setNewData(list);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionbar, menu);
        menu.findItem(R.id.menu_setting).setVisible(false);
        mMenuItemAdd = menu.findItem(R.id.menu_add);

        mMenuItemAdd.setIcon(new IconicsDrawable(this)
                .icon(Ionicons.Icon.ion_android_add)
                .sizeDp(20)
                .color(getResources().getColor(R.color.white)
                ));

        mMenuItemAdd.setVisible(true);
        return true;
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {

        startActivity(TermInfoActivity.class,false);

        return false;
    }

    /**
     * RxBus  托管周期更新
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
                        if(!TextUtils.isEmpty(s) && s.equals(Constant.UPDATE_TERM)){
                            mPresenter.queryAllTerm();
                        }
                    }
                });
    }
}
