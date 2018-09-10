package com.zmm.tmsystem.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zmm.tmsystem.R;
import com.zmm.tmsystem.bean.SpendingBean;
import com.zmm.tmsystem.common.utils.DateUtils;

import java.text.ParseException;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/9/4
 * Email:65489469@qq.com
 */
public class SpendingAdapter extends BaseQuickAdapter<SpendingBean,BaseViewHolder>{

    private Context mContext;

    public SpendingAdapter(Context context){
        super(R.layout.item_spending);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, SpendingBean item) {

        helper.setText(R.id.tv_spend_content,item.getContent());
        try {
            String time = DateUtils.longToString(item.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
            helper.setText(R.id.tv_spend_time,time);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        float pay = item.getPay();
        float deposit = item.getDeposit();
        if(pay != 0){
            helper.setText(R.id.tv_spend_value,"-"+pay);
            helper.setTextColor(R.id.tv_spend_value,mContext.getResources().getColor(R.color.black));

        }

        if(deposit != 0){
            helper.setText(R.id.tv_spend_value,"+"+deposit);
            helper.setTextColor(R.id.tv_spend_value,mContext.getResources().getColor(R.color.md_green_A700));

        }


    }
}
