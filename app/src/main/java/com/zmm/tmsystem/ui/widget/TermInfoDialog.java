package com.zmm.tmsystem.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zmm.tmsystem.R;
import com.zmm.tmsystem.common.utils.ToastUtils;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/15
 * Time:下午11:41
 */

public class TermInfoDialog extends Dialog {

    private Context mContext;
    private Button mCancel;
    private Button mConfirm;

    private TextView mDialogTop;
    private EditText mEtTitle;
    private EditText mEtYear;
    private EditText mEtMonth;
    private EditText mEtTerm;

    private String top;
    private String title;
    private String year;
    private String month;
    private String term;




    private OnClickListener mOnClickListener;


    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }


    public TermInfoDialog(Context context, String top, String title, String year, String month, String term) {
        super(context, R.style.SimpleDialog);
        System.out.println("dialog 构造方法");
        mContext = context;
        this.top = top;
        this.title = title;
        this.year = year;
        this.month = month;
        this.term = term;
    }

    public interface OnClickListener{

        void onCancel();

        void onConfirm(String title,int year,int month,String term);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_term_info_dialog);
        //按空白处不能取消dialog
        setCanceledOnTouchOutside(false);

        System.out.println("初始化dialog");

        initView();

        initEvent();

    }


    private void initView() {

        mDialogTop = (TextView) findViewById(R.id.tv_dialog_top);

        mCancel = (Button) findViewById(R.id.btn_dialog_cancel);
        mConfirm = (Button) findViewById(R.id.btn_dialog_confirm);

        mEtTitle = (EditText) findViewById(R.id.et_dialog_title);
        mEtYear = (EditText) findViewById(R.id.et_dialog_year);
        mEtMonth = (EditText) findViewById(R.id.et_dialog_month);
        mEtTerm = (EditText) findViewById(R.id.et_dialog_term);


        if(!TextUtils.isEmpty(top)){
            mDialogTop.setText(top);
        }

        if(!TextUtils.isEmpty(title)){
            mEtTitle.setText(title);
            mEtTitle.setSelection(title.length());
        }

        if(!TextUtils.isEmpty(year)){
            mEtYear.setText(year);
            mEtTitle.setSelection(year.length());
        }

        if(!TextUtils.isEmpty(month)){
            mEtMonth.setText(month);
            mEtTitle.setSelection(month.length());
        }

        if(!TextUtils.isEmpty(term)){
            mEtTerm.setText(term);
            mEtTitle.setSelection(term.length());
        }



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

                    String title = mEtTitle.getText().toString().trim();
                    String year = mEtYear.getText().toString().trim();
                    String month = mEtMonth.getText().toString().trim();
                    String term = mEtTerm.getText().toString().trim();

                    if(TextUtils.isEmpty(title)){
                        ToastUtils.SimpleToast(mContext,"标题不能为空");
                        return;
                    }

                    if(TextUtils.isEmpty(year)){
                        ToastUtils.SimpleToast(mContext,"年份不能为空");
                        return;
                    }

                    if(TextUtils.isEmpty(month)){
                        ToastUtils.SimpleToast(mContext,"月份不能为空");
                        return;
                    }

                    if(TextUtils.isEmpty(term)){
                        ToastUtils.SimpleToast(mContext,"周期不能为空");
                        return;
                    }


                    mOnClickListener.onConfirm(title,Integer.parseInt(year),Integer.parseInt(month),term);
                }
            }
        });

    }


}
