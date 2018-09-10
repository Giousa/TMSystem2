package com.zmm.tmsystem.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zmm.tmsystem.R;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/15
 * Time:下午11:41
 */

public class SimplePhoneDialog extends Dialog {

    private TextView mTitle;
    private TextView mPhoneNum;
    private Button mCancel;
    private Button mConfirm;
    private String teacherName;
    private String teacherNum;




    private OnClickListener mOnClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public SimplePhoneDialog(Context context, String teacherName,String teacherNum) {
        super(context, R.style.SimpleDialog);
        this.teacherName = teacherName;
        this.teacherNum = teacherNum;
    }

    public interface OnClickListener{

        void onCancel();

        void onConfirm(String num);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_simple_phone_dialog);
        //按空白处不能取消dialog
        setCanceledOnTouchOutside(false);

        initView();

        initEvent();

    }


    private void initView() {
        mTitle = (TextView) findViewById(R.id.tv_dialog_title);
        mPhoneNum = (TextView) findViewById(R.id.tv_dialog_num);
        mCancel = (Button) findViewById(R.id.btn_dialog_cancel);
        mConfirm = (Button) findViewById(R.id.btn_dialog_confirm);

        mTitle.setText("您将拨打 "+teacherName+" 老师的电话：");
        mPhoneNum.setText(teacherNum);
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
                    mOnClickListener.onConfirm(teacherNum);
                }
            }
        });

    }

}
