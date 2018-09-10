package com.zmm.tmsystem.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zmm.tmsystem.R;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/15
 * Time:下午11:41
 */

public class SimpleScoreSelectDialog extends Dialog {


    private TextView mTv1;
    private TextView mTv2;
    private TextView mTv3;
    private TextView mTv4;
    private TextView mTv5;
    private TextView mTv6;
    private CustomPayItemView.OnTagClickListener mOnTagClickListener;



    public void setOnTagClickListener(CustomPayItemView.OnTagClickListener onTagClickListener) {
        mOnTagClickListener = onTagClickListener;
    }

    public interface OnTagClickListener{
        void OnTagClick(String name);
    }


    public SimpleScoreSelectDialog(Context context) {
        super(context, R.style.SimpleDialog);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_simple_score_select_dialog);
        //按空白处可以取消dialog
        setCanceledOnTouchOutside(true);

        initView();

        initEvent();

    }


    private void initView() {
        mTv1 = (TextView) findViewById(R.id.tv1);
        mTv2 = (TextView) findViewById(R.id.tv2);
        mTv3 = (TextView) findViewById(R.id.tv3);
        mTv4 = (TextView) findViewById(R.id.tv4);
        mTv5 = (TextView) findViewById(R.id.tv5);
        mTv6 = (TextView) findViewById(R.id.tv6);
    }


    private void initEvent() {
        mTv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnTagClickListener != null){
                    mOnTagClickListener.OnTagClick(mTv1.getText().toString());
                }
            }
        });

        mTv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnTagClickListener != null){
                    mOnTagClickListener.OnTagClick(mTv2.getText().toString());
                }
            }
        });

        mTv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnTagClickListener != null){
                    mOnTagClickListener.OnTagClick(mTv3.getText().toString());
                }
            }
        });

        mTv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnTagClickListener != null){
                    mOnTagClickListener.OnTagClick(mTv4.getText().toString());
                }
            }
        });

        mTv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnTagClickListener != null){
                    mOnTagClickListener.OnTagClick(mTv5.getText().toString());
                }
            }
        });

        mTv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnTagClickListener != null){
                    mOnTagClickListener.OnTagClick(mTv6.getText().toString());
                }
            }
        });
    }


}
