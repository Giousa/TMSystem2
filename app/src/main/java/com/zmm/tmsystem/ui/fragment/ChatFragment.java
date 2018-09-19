package com.zmm.tmsystem.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zmm.tmsystem.R;
import com.zmm.tmsystem.bean.TeacherBean;
import com.zmm.tmsystem.bean.TermBean;
import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.common.utils.ToastUtils;
import com.zmm.tmsystem.dagger.component.AppComponent;
import com.zmm.tmsystem.rx.RxBus;
import com.zmm.tmsystem.ui.activity.ChatActivity;
import com.zmm.tmsystem.ui.adapter.ChatAdapter;
import com.zmm.tmsystem.ui.widget.chat.ui.ChatActivity2;

import java.util.List;

import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;


/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/15
 * Time:上午10:02
 */

public class ChatFragment extends ProgressFragment {


    @BindView(R.id.empty)
    RelativeLayout empty;
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;


    private Activity mContext;
    private ChatAdapter mChatAdapter;

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

        initData();

        operateBus();


    }

    private void initData() {

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mChatAdapter = new ChatAdapter(getActivity());

        mChatAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Conversation conversation = (Conversation) adapter.getData().get(position);
                String userName = conversation.getLatestMessage().getFromUser().getUserName();
                String targetId = ((UserInfo) conversation.getTargetInfo()).getUserName();

                Intent intent = new Intent(mContext,ChatActivity2.class);
                intent.putExtra(Constant.TARGET_ID, targetId);
                intent.putExtra(Constant.TARGET_NAME, userName);
                intent.putExtra(Constant.TARGET_APP_KEY, conversation.getTargetAppKey());
                startActivity(intent);


            }
        });

        mRecyclerView.setAdapter(mChatAdapter);

        List<Conversation> conversationList = JMessageClient.getConversationList();

        mChatAdapter.setNewData(conversationList);
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

                        System.out.println("Chat 消息："+s);
                        if(!TextUtils.isEmpty(s)){

                            if(s.equals(Constant.UN_READ_MSG_COUNT)){

                                if(mChatAdapter != null){
                                    List<Conversation> conversationList = JMessageClient.getConversationList();
                                    mChatAdapter.setNewData(conversationList);
                                }

                            }
                        }
                    }
                });
    }


}
