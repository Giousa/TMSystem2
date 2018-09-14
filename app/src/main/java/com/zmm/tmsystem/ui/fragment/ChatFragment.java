package com.zmm.tmsystem.ui.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.zmm.tmsystem.R;
import com.zmm.tmsystem.bean.Event;
import com.zmm.tmsystem.common.utils.ACache;
import com.zmm.tmsystem.dagger.component.AppComponent;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.ConversationRefreshEvent;
import cn.jpush.im.android.api.event.MessageReceiptStatusChangeEvent;
import cn.jpush.im.android.api.event.MessageRetractEvent;
import cn.jpush.im.android.api.event.OfflineMessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.eventbus.EventBus;


/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/15
 * Time:上午10:02
 */

public class ChatFragment extends ProgressFragment {


    private NetworkReceiver mReceiver;
    private Activity mContext;
    private ACache mACache;


    @Override
    protected int setLayout() {
        return R.layout.fragment_chat;
    }

    @Override
    protected void setupAcitivtyComponent(AppComponent appComponent) {

    }

    @Override
    protected void init() {
        mContext = this.getActivity();
        mACache = ACache.get(mContext);

        initReceiver();
    }


    @Override
    public void onResume() {
        super.onResume();

        int allUnReadMsgCount = JMessageClient.getAllUnReadMsgCount();
        System.out.println("未读消息 onresume：" + allUnReadMsgCount);
    }

    /**
     * 判断网络状态
     */
    private void initReceiver() {
        mReceiver = new NetworkReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mContext.registerReceiver(mReceiver, filter);
    }

    //监听网络状态的广播
    private class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeInfo = manager.getActiveNetworkInfo();
                if (null == activeInfo) {
//                    mConvListView.showHeaderView();
                } else {
//                    mConvListView.dismissHeaderView();
                }
            }
        }
    }

    /**
     * 收到消息
     */
//    public void onEvent(MessageEvent event) {
//
//        int allUnReadMsgCount = JMessageClient.getAllUnReadMsgCount();
//        System.out.println("未读消息：" + allUnReadMsgCount);
//
//        mContext.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                RxBus.getDefault().post(Constant.UN_READ_MSG_COUNT);
//            }
//        });
////        mACache.put(Constant.UN_READ_MSG_COUNT, allUnReadMsgCount+"");
//
//
////        Message msg = event.getMessage();
////        final UserInfo userInfo = (UserInfo) msg.getTargetInfo();
////        String targetId = userInfo.getUserName();
////        System.out.println("未读消息：人名：" + targetId);
////
////        Conversation conv = JMessageClient.getSingleConversation(targetId, userInfo.getAppKey());
////        if (conv != null) {
////        }
//    }

    /**
     * 接收离线消息
     *
     * @param event 离线消息事件
     */
    public void onEvent(OfflineMessageEvent event) {
        Conversation conv = event.getConversation();
        if (!conv.getTargetId().equals("feedback_Android")) {
        }
    }

    /**
     * 消息撤回
     */
    public void onEvent(MessageRetractEvent event) {
        Conversation conversation = event.getConversation();
    }

    /**
     * 消息已读事件
     */
    public void onEventMainThread(MessageReceiptStatusChangeEvent event) {
        //regresh
    }

    /**
     * 消息漫游完成事件
     *
     * @param event 漫游完成后， 刷新会话事件
     */
    public void onEvent(ConversationRefreshEvent event) {
        Conversation conv = event.getConversation();
        if (!conv.getTargetId().equals("feedback_Android")) {
//            mBackgroundHandler.sendMessage(mBackgroundHandler.obtainMessage(REFRESH_CONVERSATION_LIST, conv));
//            //多端在线未读数改变时刷新
//            if (event.getReason().equals(ConversationRefreshEvent.Reason.UNREAD_CNT_UPDATED)) {
//                mBackgroundHandler.sendMessage(mBackgroundHandler.obtainMessage(REFRESH_CONVERSATION_LIST, conv));
//            }
        }
    }


    public void onEventMainThread(Event event) {
        System.out.println("事件：event = " + event);
        switch (event.getType()) {
            case createConversation:
                Conversation conv = event.getConversation();
                if (conv != null) {
//                    mConvListController.getAdapter().addNewConversation(conv);
                }
                break;
            case deleteConversation:
                conv = event.getConversation();
                if (null != conv) {
//                    mConvListController.getAdapter().deleteConversation(conv);
                }
                break;
            //收到保存为草稿事件
            case draft:
                conv = event.getConversation();
                String draft = event.getDraft();
                //如果草稿内容不为空，保存，并且置顶该会话
                if (!TextUtils.isEmpty(draft)) {
//                    mConvListController.getAdapter().putDraftToMap(conv, draft);
//                    mConvListController.getAdapter().setToTop(conv);
                    //否则删除
                } else {
//                    mConvListController.getAdapter().delDraftFromMap(conv);
                }
                break;
            case addFriend:
                break;
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        mContext.unregisterReceiver(mReceiver);
        super.onDestroy();
    }
}
