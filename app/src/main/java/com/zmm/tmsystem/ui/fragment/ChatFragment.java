package com.zmm.tmsystem.ui.fragment;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import com.zmm.tmsystem.R;
import com.zmm.tmsystem.bean.TeacherBean;
import com.zmm.tmsystem.bean.TermBean;
import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.dagger.component.AppComponent;
import com.zmm.tmsystem.rx.RxBus;
import com.zmm.tmsystem.ui.adapter.ChatAdapter;

import java.util.List;

import butterknife.BindView;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;
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

        mRecyclerView.setAdapter(mChatAdapter);

        List<Conversation> conversationList = JMessageClient.getConversationList();

        mChatAdapter.setNewData(conversationList);
    }


    @Override
    public void onResume() {
        super.onResume();
        System.out.println("聊天界面");


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
