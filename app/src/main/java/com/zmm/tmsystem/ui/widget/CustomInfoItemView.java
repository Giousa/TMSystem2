package com.zmm.tmsystem.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.zmm.tmsystem.R;
import com.zmm.tmsystem.common.Constant;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/7
 * Time:下午3:39
 */

public class CustomInfoItemView extends LinearLayout{

    private Context mContext;
    private TextView mTvItemTitle;
    private TextView mTvItemContent;
    private boolean isShowPic = false;
    private boolean isShowArrow = true;

    private int type;

    private OnItemClickListener mItemClickListener;
    private RelativeLayout mRl_item;
    private ImageView mIvIcon;
    private ImageView mArrow;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener,int type) {
        mItemClickListener = onItemClickListener;
        this.type = type;
    }

    public interface OnItemClickListener{
        void itemClick(int type,String name);
    }



    public CustomInfoItemView(Context context) {
        this(context,null);
//        System.out.println("111");
    }

    public CustomInfoItemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
//        System.out.println("222");

        //获取设置的自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomInfoItemView);
        String titlename = typedArray.getString(R.styleable.CustomInfoItemView_title);
//        final int isShow = typedArray.getInteger(0,R.styleable.CustomInfoItemView_isShowPic);
        String isShow = typedArray.getString(R.styleable.CustomInfoItemView_isShowPic);

        if(isShow.equals("1")){
            isShowPic = true;
        }else {
            isShowPic = false;
        }
//        final int showArrow = typedArray.getInteger(0,R.styleable.CustomInfoItemView_isShowArrow);
//        if(showArrow == 0){
//            isShowArrow = true;
//        }else {
//            isShowArrow = false;
//        }

        //开始初始化控件
        initView(context);

        typedArray.recycle();

        //将获取的属性值赋予控件从而展示
        mTvItemTitle.setText(titlename);

        mRl_item.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mItemClickListener != null){
                    if(isShowPic){
                        mItemClickListener.itemClick(type,"");
                    }else {
                        mItemClickListener.itemClick(type,getContent());
                    }

                }
            }
        });

        if(isShowPic){
            mIvIcon.setImageDrawable(new IconicsDrawable(mContext)
                    .icon(Ionicons.Icon.ion_android_contact)
                    .color(getResources().getColor(R.color.md_blue_500)
                    ));
        }

    }

    public CustomInfoItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        System.out.println("333");
    }

    private void initView(Context context) {

        mContext = context;

        View view;
        if(isShowPic){
            view = View.inflate(getContext(), R.layout.info_item_pic_view, this);
            mIvIcon = (ImageView) view.findViewById(R.id.iv_item_pic);

        }else {
            view = View.inflate(getContext(), R.layout.info_item_view, this);
            mTvItemContent = (TextView) view.findViewById(R.id.tv_item_content);
        }

        mTvItemTitle = (TextView) view.findViewById(R.id.tv_item_title);
        mRl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
//        mArrow = (ImageView) view.findViewById(R.id.iv_item_arrow);
//
//        if(isShowArrow) {
//            mArrow.setVisibility(VISIBLE);
//        }else {
//            mArrow.setVisibility(GONE);
//        }

    }

    public void setContent(String content){
        if(!isShowPic){
            mTvItemContent.setText(content);
        }
    }

    public String getContent(){
        return mTvItemContent.getText().toString();
    }


    /**
     * 设置网络图片
     * @param path
     */
    public void setIcon(String path,int gender){

        if(TextUtils.isEmpty(path)){
            return;
        }
        if(isShowPic){

            if(gender == 0){
                Glide.with(mContext)
                        .load(Constant.BASE_IMG_URL+path)
                        .transform(new GlideCircleTransform(mContext))
                        .error(new IconicsDrawable(mContext)
                                .icon(Ionicons.Icon.ion_android_contact)
                                .color(getResources().getColor(R.color.colorAccent)
                                ))
                        .into(mIvIcon);
            }else {
                Glide.with(mContext)
                        .load(Constant.BASE_IMG_URL+path)
                        .transform(new GlideCircleTransform(mContext))
                        .error(new IconicsDrawable(mContext)
                                .icon(Ionicons.Icon.ion_android_contact)
                                .color(getResources().getColor(R.color.colorPrimary)
                                ))
                        .into(mIvIcon);
            }

        }
    }

    /**
     * 设置本地图片
     * @param path
     */
    public void setLocalIcon(String path){
        if(TextUtils.isEmpty(path)){
            return;
        }
        if(isShowPic){
            Glide.with(mContext)
                    .load(path)
                    .transform(new GlideCircleTransform(mContext))
                    .error(new IconicsDrawable(mContext)
                            .icon(Ionicons.Icon.ion_android_contact)
                            .color(getResources().getColor(R.color.md_blue_500)
                            ))
                    .into(mIvIcon);
        }
    }

}
