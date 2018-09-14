package com.zmm.tmsystem.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.zmm.tmsystem.R;
import com.zmm.tmsystem.common.utils.DateUtils;

import java.text.ParseException;

import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/17
 * Time:下午4:41
 */

public class ChatAdapter extends BaseQuickAdapter<Conversation,BaseViewHolder>{

    private Context mContext;

    public ChatAdapter(Context context) {
        super(R.layout.item_chat);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final Conversation conversation) {

        Message latestMessage = conversation.getLatestMessage();

        if(latestMessage != null){
            UserInfo fromUser = latestMessage.getFromUser();
            System.out.println("from username = "+ fromUser.getUserName());
            System.out.println("from nickname = "+ fromUser.getNickname());

            if(TextUtils.isEmpty(fromUser.getNickname())){
                //姓名
                helper.setText(R.id.tv_chat_name, fromUser.getUserName());
            }else {
                helper.setText(R.id.tv_chat_name, fromUser.getNickname());

            }

            long createTime = latestMessage.getCreateTime();
            helper.setText(R.id.tv_chat_time, DateUtils.isShowTime(createTime));
        }


        ContentType latestType = conversation.getLatestType();
        switch (latestType){

            case text:
                String latestText = conversation.getLatestText();
                helper.setText(R.id.tv_chat_content, latestText);

                break;

            case image:
                System.out.println("最后一条是图片");
                helper.setText(R.id.tv_chat_content, "[图片]");

                break;
        }


        int unReadMsgCnt = conversation.getUnReadMsgCnt();
        helper.setText(R.id.tv_chat_unread, unReadMsgCnt+"");


        ImageView imageView = helper.getView(R.id.iv_chat_icon);

        //聊天头像
        imageView.setImageDrawable(new IconicsDrawable(mContext)
                .icon(Ionicons.Icon.ion_android_contact)
                .color(mContext.getResources().getColor(R.color.colorPrimary)));
    }


}
