package com.zmm.tmsystem.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zmm.tmsystem.R;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/7/9
 * Time:上午10:27
 */

public class CustomButtonTitleView extends LinearLayout {

    private Context mContext;
    private OnButtonTitleClickListener mOnButtonTitleClickListener;


    public void setOnButtonTitleClickListener(OnButtonTitleClickListener onButtonTitleClickListener) {
        mOnButtonTitleClickListener = onButtonTitleClickListener;
    }

    public interface OnButtonTitleClickListener{
        void onButtonTitleSelected(boolean flag);
    }

    public CustomButtonTitleView(Context context) {
        this(context,null);
    }

    public CustomButtonTitleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
        mContext = context;
        init();
    }

    public CustomButtonTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private void init() {

        final View view = View.inflate(mContext, R.layout.custom_buttom_title, this);
        final Button btnYes = (Button) view.findViewById(R.id.btn_select_yes);
        final Button btnNo = (Button) view.findViewById(R.id.btn_select_no);
        btnYes.setSelected(true);

        btnYes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                btnYes.setSelected(true);
                btnNo.setSelected(false);
                if(mOnButtonTitleClickListener != null){
                    mOnButtonTitleClickListener.onButtonTitleSelected(true);
                }
            }
        });

        btnNo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                btnYes.setSelected(false);
                btnNo.setSelected(true);
                if(mOnButtonTitleClickListener != null){
                    mOnButtonTitleClickListener.onButtonTitleSelected(false);
                }
            }
        });
    }
}
