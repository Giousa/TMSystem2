package com.zmm.tmsystem.ui.fragment;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.zmm.tmsystem.R;
import com.zmm.tmsystem.dagger.component.AppComponent;
import com.zmm.tmsystem.ui.adapter.ChatAdapter;

import java.util.List;

import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;


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

    }

    private void initData() {

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mChatAdapter = new ChatAdapter(getActivity());

        mRecyclerView.setAdapter(mChatAdapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        System.out.println("聊天界面");

        List<Conversation> conversationList = JMessageClient.getConversationList();

        mChatAdapter.setNewData(conversationList);
    }


}
