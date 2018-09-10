package com.zmm.tmsystem.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zmm.tmsystem.R;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/7
 * Time:下午3:39
 */

public class CustomPayItemView extends LinearLayout{

    private OnTagClickListener mOnTagClickListener;
    private TextView mTv1;
    private TextView mTv2;
    private TextView mTv3;
    private TextView mTv4;
    private TextView mTv5;
    private TextView mTv6;
    private TextView mTv7;
    private TextView mTv8;
    private TextView mTv9;
    private TextView mTv10;
    private TextView mTv11;
    private TextView mTv12;

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        mOnTagClickListener = onTagClickListener;
    }

    public interface OnTagClickListener{
        void OnTagClick(String name);
    }


    public CustomPayItemView(Context context) {
        this(context,null);
    }

    public CustomPayItemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomPayItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {

        View view = View.inflate(getContext(), R.layout.info_item_pay_view, this);
        mTv1 = (TextView) view.findViewById(R.id.tv1);
        mTv2 = (TextView) view.findViewById(R.id.tv2);
        mTv3 = (TextView) view.findViewById(R.id.tv3);
        mTv4 = (TextView) view.findViewById(R.id.tv4);
        mTv5 = (TextView) view.findViewById(R.id.tv5);
        mTv6 = (TextView) view.findViewById(R.id.tv6);
        mTv7 = (TextView) view.findViewById(R.id.tv7);
        mTv8 = (TextView) view.findViewById(R.id.tv8);
        mTv9 = (TextView) view.findViewById(R.id.tv9);
        mTv10 = (TextView) view.findViewById(R.id.tv10);
        mTv11 = (TextView) view.findViewById(R.id.tv11);
        mTv12 = (TextView) view.findViewById(R.id.tv12);

        mTv1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnTagClickListener != null){
                    mOnTagClickListener.OnTagClick(mTv1.getText().toString());
                }
            }
        });

        mTv2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnTagClickListener != null){
                    mOnTagClickListener.OnTagClick(mTv2.getText().toString());
                }
            }
        });

        mTv3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnTagClickListener != null){
                    mOnTagClickListener.OnTagClick(mTv3.getText().toString());
                }
            }
        });

        mTv4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnTagClickListener != null){
                    mOnTagClickListener.OnTagClick(mTv4.getText().toString());
                }
            }
        });

        mTv5.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnTagClickListener != null){
                    mOnTagClickListener.OnTagClick(mTv5.getText().toString());
                }
            }
        });

        mTv6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnTagClickListener != null){
                    mOnTagClickListener.OnTagClick(mTv6.getText().toString());
                }
            }
        });

        mTv7.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnTagClickListener != null){
                    mOnTagClickListener.OnTagClick(mTv7.getText().toString());
                }
            }
        });

        mTv8.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnTagClickListener != null){
                    mOnTagClickListener.OnTagClick(mTv8.getText().toString());
                }
            }
        });

        mTv9.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnTagClickListener != null){
                    mOnTagClickListener.OnTagClick(mTv9.getText().toString());
                }
            }
        });

        mTv10.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnTagClickListener != null){
                    mOnTagClickListener.OnTagClick(mTv10.getText().toString());
                }
            }
        });

        mTv11.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnTagClickListener != null){
                    mOnTagClickListener.OnTagClick(mTv11.getText().toString());
                }
            }
        });

        mTv12.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnTagClickListener != null){
                    mOnTagClickListener.OnTagClick(mTv12.getText().toString());
                }
            }
        });

    }

}
