package com.zmm.tmsystem.ui.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;

import com.zmm.tmsystem.R;
import com.zmm.tmsystem.bean.Event;
import com.zmm.tmsystem.dagger.component.AppComponent;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.event.ConversationRefreshEvent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.MessageReceiptStatusChangeEvent;
import cn.jpush.im.android.api.event.MessageRetractEvent;
import cn.jpush.im.android.api.event.OfflineMessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
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
    private BackgroundHandler mBackgroundHandler;
    private HandlerThread mThread;

    private static final int REFRESH_CONVERSATION_LIST = 0x3000;
    private static final int DISMISS_REFRESH_HEADER = 0x3001;
    private static final int ROAM_COMPLETED = 0x3002;

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


        mThread = new HandlerThread("MainActivity");
        mThread.start();
        mBackgroundHandler = new BackgroundHandler(mThread.getLooper());

        initReceiver();
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
    public void onEvent(MessageEvent event) {

        int allUnReadMsgCount = JMessageClient.getAllUnReadMsgCount();
        System.out.println("未读消息："+allUnReadMsgCount);

        Message msg = event.getMessage();
        final UserInfo userInfo = (UserInfo) msg.getTargetInfo();
        String targetId = userInfo.getUserName();
        Conversation conv = JMessageClient.getSingleConversation(targetId, userInfo.getAppKey());
        if (conv != null) {
            mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (TextUtils.isEmpty(userInfo.getAvatar())) {
                        userInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                            @Override
                            public void gotResult(int responseCode, String responseMessage, Bitmap avatarBitmap) {
                                if (responseCode == 0) {
                                    //refresh
                                }
                            }
                        });
                    }
                }
            });
            mBackgroundHandler.sendMessage(mBackgroundHandler.obtainMessage(REFRESH_CONVERSATION_LIST, conv));
        }
    }

    /**
     * 接收离线消息
     *
     * @param event 离线消息事件
     */
    public void onEvent(OfflineMessageEvent event) {
        Conversation conv = event.getConversation();
        if (!conv.getTargetId().equals("feedback_Android")) {
            mBackgroundHandler.sendMessage(mBackgroundHandler.obtainMessage(REFRESH_CONVERSATION_LIST, conv));
        }
    }

    /**
     * 消息撤回
     */
    public void onEvent(MessageRetractEvent event) {
        Conversation conversation = event.getConversation();
        mBackgroundHandler.sendMessage(mBackgroundHandler.obtainMessage(REFRESH_CONVERSATION_LIST, conversation));
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
            mBackgroundHandler.sendMessage(mBackgroundHandler.obtainMessage(REFRESH_CONVERSATION_LIST, conv));
            //多端在线未读数改变时刷新
            if (event.getReason().equals(ConversationRefreshEvent.Reason.UNREAD_CNT_UPDATED)) {
                mBackgroundHandler.sendMessage(mBackgroundHandler.obtainMessage(REFRESH_CONVERSATION_LIST, conv));
            }
        }
    }

    private class BackgroundHandler extends Handler {
        public BackgroundHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REFRESH_CONVERSATION_LIST:
                    Conversation conv = (Conversation) msg.obj;
//                    mConvListController.getAdapter().setToTop(conv);
                    break;
                case DISMISS_REFRESH_HEADER:
                    mContext.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            mConvListView.dismissLoadingHeader();
                        }
                    });
                    break;
                case ROAM_COMPLETED:
                    conv = (Conversation) msg.obj;
//                    mConvListController.getAdapter().addAndSort(conv);
                    break;
            }
        }
    }

    public void onEventMainThread(Event event) {
        System.out.println("事件：event = "+event);
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
        mBackgroundHandler.removeCallbacksAndMessages(null);
        mThread.getLooper().quit();
        super.onDestroy();
    }
}
