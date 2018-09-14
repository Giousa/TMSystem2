package com.zmm.tmsystem.ui.activity;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.zmm.tmsystem.R;
import com.zmm.tmsystem.bean.TermBean;
import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.common.utils.ACache;
import com.zmm.tmsystem.common.utils.DateUtils;
import com.zmm.tmsystem.common.utils.ToastUtils;
import com.zmm.tmsystem.dagger.component.AppComponent;
import com.zmm.tmsystem.rx.RxBus;
import com.zmm.tmsystem.ui.fragment.FragmentFactory;
import com.zmm.tmsystem.ui.fragment.ProgressFragment;
import com.zmm.tmsystem.ui.widget.BottomBar;
import com.zmm.tmsystem.ui.widget.TitleBar;

import java.text.ParseException;
import java.util.List;

import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.MessageContent;
import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class MainActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener, BottomBarLayout.OnItemSelectedListener {



    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.fl_container)
    FrameLayout mFlContainer;
    @BindView(R.id.bottom_bar)
    BottomBar mBottomBar;
    @BindView(R.id.bbl)
    BottomBarLayout mBottomBarLayout;


    private MenuItem mMenuItemSetting;
    private MenuItem mMenuItemAdd;
    private int index = 0;
    private ACache mACache;
    private long time = 0;


    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {

        //缓存工具类
        mACache = ACache.get(this);

        initToolBar();

        initTablayout();

        operateBus();


        //订阅接收消息,子类只要重写onEvent就能收到消息
        JMessageClient.registerEventReceiver(this);

        changeFragment(FragmentFactory.createFragment(index));


    }

    private void initToolBar() {
        //这里一定要加上，否则menu不显示
        setSupportActionBar(mTitleBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mTitleBar.setCenterTitle(getResources().getString(R.string.main_title_home));

        mTitleBar.setOnMenuItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int allUnReadMsgCount = JMessageClient.getAllUnReadMsgCount();
        System.out.println("MainActivity 未读消息onresume：" + allUnReadMsgCount);

        mBottomBarLayout.setUnread(2,allUnReadMsgCount);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JMessageClient.unRegisterEventReceiver(this);

    }

    /**
     * 收到消息
     */
    public void onEventMainThread(MessageEvent event) {

        int allUnReadMsgCount = JMessageClient.getAllUnReadMsgCount();
        System.out.println("MainActivity 未读消息：" + allUnReadMsgCount);

//        Message msg = event.getMessage();
//        final UserInfo userInfo = (UserInfo) msg.getTargetInfo();
//        String targetId = userInfo.getUserName();
//        System.out.println("MainActivity 未读消息 人名：" + targetId);

        mBottomBarLayout.setUnread(2,allUnReadMsgCount);

        RxBus.getDefault().post(Constant.UN_READ_MSG_COUNT);

    }


    private void initTablayout() {


        mBottomBarLayout.setOnItemSelectedListener(this);



//        mBottomBar.setContainer(R.id.fl_container)
//                .setTitleBeforeAndAfterColor("#999999", "#ff5d5e")
//                .addItem(HomeFragment.class,
//                        "我的",
//                        R.drawable.item2_before,
//                        R.drawable.item2_after)
//                .addItem(ManageFragment.class,
//                        "托管",
//                        R.drawable.item1_before,
//                        R.drawable.item1_after)
//                .addItem(ChatFragment.class,
//                        "聊天",
//                        R.drawable.item3_before,
//                        R.drawable.item3_after)
//                .addItem(CommentFragment.class,
//                        "评价",
//                        R.drawable.item4_before,
//                        R.drawable.item4_after)
//                .build();
//
//        mBottomBar.setOnSwitchFragmentListener(this);

    }

    @Override
    public void onItemSelected(BottomBarItem bottomBarItem, int prePosition, int currentPosition) {

        this.index = currentPosition;

        changeFragment(FragmentFactory.createFragment(index));

        switch (index) {

            case 0:
                mTitleBar.setCenterTitle(getResources().getString(R.string.main_title_home));
                mMenuItemSetting.setVisible(true);
                mMenuItemAdd.setVisible(false);

                mTitleBar.setSubtitle("");

                break;

            case 1:

                TermBean termBean = (TermBean) mACache.getAsObject(Constant.TERM);
                if (termBean == null || termBean.getId() == null) {
                    mTitleBar.setCenterTitle(getResources().getString(R.string.main_title_childcare));
                } else {
                    mTitleBar.setCenterTitle(termBean.getTitle());
                }
                mMenuItemSetting.setVisible(true);
                mMenuItemAdd.setVisible(true);

                String count = mACache.getAsString(Constant.CHILDCARE_STUDENT_COUNT);
                if (TextUtils.isEmpty(count)) {
                    mTitleBar.setSubtitle("");
                } else {
                    mTitleBar.setSubtitle(count);
                }
                mTitleBar.setSubtitleTextColor(getResources().getColor(R.color.white));


                break;

            case 2:
                mTitleBar.setCenterTitle(getResources().getString(R.string.main_title_chat));
                mMenuItemSetting.setVisible(false);
                mMenuItemAdd.setVisible(false);
                mTitleBar.setSubtitle("");
//                String count2 = mACache.getAsString(Constant.CRAM_STUDENT_COUNT);
//                if(TextUtils.isEmpty(count2)){
//                    mTitleBar.setSubtitle("");
//                }else {
//                    mTitleBar.setSubtitle(count2);
//                }
//                mTitleBar.setSubtitleTextColor(getResources().getColor(R.color.white));

                break;

            case 3:
                mTitleBar.setCenterTitle(getResources().getString(R.string.main_title_comment));
                mMenuItemSetting.setVisible(false);
                mMenuItemAdd.setVisible(false);
                mTitleBar.setSubtitle("");

                RxBus.getDefault().post(Constant.ITEM_COMMENTS);

                break;

        }

    }


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
    }

//    @Override
//    public void onSwitchFragment(int index) {
//
//        this.index = index;
//
//        switch (index) {
//
//            case 0:
//                mTitleBar.setCenterTitle(getResources().getString(R.string.main_title_home));
//                mMenuItemSetting.setVisible(true);
//                mMenuItemAdd.setVisible(false);
//
//                mTitleBar.setSubtitle("");
//
//                break;
//
//            case 1:
//
//                TermBean termBean = (TermBean) mACache.getAsObject(Constant.TERM);
//                if (termBean == null || termBean.getId() == null) {
//                    mTitleBar.setCenterTitle(getResources().getString(R.string.main_title_childcare));
//                } else {
//                    mTitleBar.setCenterTitle(termBean.getTitle());
//                }
//                mMenuItemSetting.setVisible(true);
//                mMenuItemAdd.setVisible(true);
//
//                String count = mACache.getAsString(Constant.CHILDCARE_STUDENT_COUNT);
//                if (TextUtils.isEmpty(count)) {
//                    mTitleBar.setSubtitle("");
//                } else {
//                    mTitleBar.setSubtitle(count);
//                }
//                mTitleBar.setSubtitleTextColor(getResources().getColor(R.color.white));
//
//
//                break;
//
//            case 2:
//                mTitleBar.setCenterTitle(getResources().getString(R.string.main_title_chat));
//                mMenuItemSetting.setVisible(true);
//                mMenuItemAdd.setVisible(false);
//
////                String count2 = mACache.getAsString(Constant.CRAM_STUDENT_COUNT);
////                if(TextUtils.isEmpty(count2)){
////                    mTitleBar.setSubtitle("");
////                }else {
////                    mTitleBar.setSubtitle(count2);
////                }
////                mTitleBar.setSubtitleTextColor(getResources().getColor(R.color.white));
//
//                break;
//
//            case 3:
//                mTitleBar.setCenterTitle(getResources().getString(R.string.main_title_comment));
//                mMenuItemSetting.setVisible(false);
//                mMenuItemAdd.setVisible(false);
//
//                String count3 = mACache.getAsString(Constant.EVALUATE_STUDENT_COUNT);
//                if (TextUtils.isEmpty(count3)) {
//                    mTitleBar.setSubtitle("");
//                } else {
//                    mTitleBar.setSubtitle(count3);
//                }
//                mTitleBar.setSubtitleTextColor(getResources().getColor(R.color.white));
//
//                RxBus.getDefault().post(Constant.ITEM_COMMENTS);
//
//                break;
//
//        }
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionbar, menu);
        mMenuItemSetting = menu.findItem(R.id.menu_setting);
        mMenuItemAdd = menu.findItem(R.id.menu_add);
        mMenuItemSetting.setIcon(new IconicsDrawable(this)
                .icon(Ionicons.Icon.ion_android_settings)
                .sizeDp(20)
                .color(getResources().getColor(R.color.white)
                ));

        mMenuItemAdd.setIcon(new IconicsDrawable(this)
                .icon(Ionicons.Icon.ion_person_add)
                .sizeDp(20)
                .color(getResources().getColor(R.color.white)
                ));

        mMenuItemAdd.setVisible(false);
        return true;
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.menu_setting:
                if (index == 0) {
                    startActivity(SettingActivity.class, false);
                } else if (index == 1) {
//                    System.out.println("托管中心  设置界面");
                    startActivity(TermActivity.class, false);
                } else if (index == 2) {
//                    System.out.println("补习班中心  设置界面");
                }
                break;

            case R.id.menu_add:

                if (index == 1) {
//                    System.out.println("托管中心  添加学生界面");
//                    RxBus.getDefault().post(Constant.ADD_TERM_STUDENT);
                    TermBean termBean = (TermBean) mACache.getAsObject(Constant.TERM);

                    if (termBean != null && termBean.getId() != null) {
//                        startActivity(StudentActivity.class,false);
                        Intent intent1 = new Intent(MainActivity.this, StudentActivity.class);
                        intent1.putExtra(Constant.INTENT_PARAM, 1);
                        startActivity(intent1);

                    } else {
                        ToastUtils.SimpleToast(this, "请点击右边设置，选择托管周期");
                    }

                } else if (index == 2) {
//                    System.out.println("补习班中心  添加学生界面");
                    Intent intent2 = new Intent(MainActivity.this, StudentActivity.class);
                    intent2.putExtra(Constant.INTENT_PARAM, 2);
                    startActivity(intent2);
                }

                break;
        }

        return false;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == event.KEYCODE_BACK) {
            if (System.currentTimeMillis() - time > 2000) {
                time = System.currentTimeMillis();
                ToastUtils.SimpleToast(this, "再次点击，退出应用");
            } else {
                removeAllActivity();
            }
        }

        return true;
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
                        if (!TextUtils.isEmpty(s) && mTitleBar != null) {

                            if (s.equals(Constant.UPDATE_TITLE)) {
                                TermBean termBean = (TermBean) mACache.getAsObject(Constant.TERM);

                                if (termBean == null || termBean.getTitle() == null) {
                                    mTitleBar.setCenterTitle(getResources().getString(R.string.main_title_childcare));
                                } else {
                                    mTitleBar.setCenterTitle(termBean.getTitle());

                                }

                            } else if (s.equals(Constant.ADD_CHILDCARE_STUDENT)) {
                                String count = mACache.getAsString(Constant.CHILDCARE_STUDENT_COUNT);
                                System.out.println("count = " + count);
                                System.out.println("mTitleBar = " + mTitleBar);
                                if (TextUtils.isEmpty(count)) {
                                    mTitleBar.setSubtitle("");
                                } else {
                                    mTitleBar.setSubtitle(count);
                                }
                                mTitleBar.setSubtitleTextColor(getResources().getColor(R.color.white));

                            }


                        }
                    }
                });
    }


    private void changeFragment(ProgressFragment targetFragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_container, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }


}
