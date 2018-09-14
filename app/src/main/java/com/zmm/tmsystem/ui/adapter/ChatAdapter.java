package com.zmm.tmsystem.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.zmm.tmsystem.R;
import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.common.utils.DateUtils;
import com.zmm.tmsystem.ui.widget.GlideCircleTransform;

import java.text.ParseException;

import cn.jpush.im.android.api.content.CustomContent;
import cn.jpush.im.android.api.content.PromptContent;
import cn.jpush.im.android.api.content.TextContent;
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

        TextView unReadText = helper.getView(R.id.tv_chat_unread);


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

            ContentType contentType = latestMessage.getContentType();
            String contentStr;

            switch (contentType) {
                case image:
                    contentStr = mContext.getString(R.string.type_picture);
                    break;
                case voice:
                    contentStr = mContext.getString(R.string.type_voice);
                    break;
                case location:
                    contentStr = mContext.getString(R.string.type_location);
                    break;
                case file:
                    String extra = latestMessage.getContent().getStringExtra("video");
                    if (!TextUtils.isEmpty(extra)) {
                        contentStr = mContext.getString(R.string.type_smallvideo);
                    } else {
                        contentStr = mContext.getString(R.string.type_file);
                    }
                    break;
                case video:
                    contentStr = mContext.getString(R.string.type_video);
                    break;
                case custom:
                    CustomContent customContent = (CustomContent) latestMessage.getContent();
                    Boolean isBlackListHint = customContent.getBooleanValue("blackList");
                    if (isBlackListHint != null && isBlackListHint) {
                        contentStr = mContext.getString(R.string.jmui_server_803008);
                    } else {
                        contentStr = mContext.getString(R.string.type_custom);
                    }
                    break;
                case prompt:
                    contentStr = ((PromptContent) latestMessage.getContent()).getPromptText();
                    break;
                default:
                    contentStr = ((TextContent) latestMessage.getContent()).getText();
                    break;
            }


            helper.setText(R.id.tv_chat_content, contentStr);

        }else {
            helper.setText(R.id.tv_chat_time, "");
            helper.setText(R.id.tv_chat_content, "");

        }

        int unReadMsgCnt = conversation.getUnReadMsgCnt();
        if(unReadMsgCnt == 0){
            unReadText.setVisibility(View.INVISIBLE);
        }else {
            unReadText.setText(unReadMsgCnt+"");
        }


        ImageView imageView = helper.getView(R.id.iv_chat_icon);

        //聊天头像
//        imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.jmui_head_icon));

        Glide.with(mContext)
                .load(R.drawable.jmui_head_icon)
                .transform(new GlideCircleTransform(mContext))
                .error(new IconicsDrawable(mContext)
                        .icon(Ionicons.Icon.ion_android_contact)
                        .color(mContext.getResources().getColor(R.color.colorPrimary)
                        ))
                .into(imageView);
    }


}
