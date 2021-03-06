package com.zmm.tmsystem.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
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

public class SimpleSelectDialog extends Dialog {

    private Button mCancel;
    private Button mConfirm;




    private OnClickListener mOnClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public SimpleSelectDialog(Context context) {
        super(context, R.style.SimpleDialog);
    }

    public interface OnClickListener{

        void onCancel();

        void onConfirm();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_simple_select_dialog);
        //按空白处不能取消dialog
        setCanceledOnTouchOutside(false);

        initView();

        initEvent();

    }


    private void initView() {
        mCancel = (Button) findViewById(R.id.btn_dialog_cancel);
        mConfirm = (Button) findViewById(R.id.btn_dialog_confirm);

    }


    private void initEvent() {
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnClickListener != null){
                    mOnClickListener.onCancel();
                }
            }
        });

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnClickListener != null){
                    mOnClickListener.onConfirm();
                }
            }
        });

    }

}
