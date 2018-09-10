package com.zmm.tmsystem.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.zmm.tmsystem.R;
import com.zmm.tmsystem.bean.TermBean;
import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.common.utils.ACache;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/17
 * Time:下午4:41
 */

public class TermAdapter extends BaseQuickAdapter<TermBean,BaseViewHolder>{


    private Context mContext;
    private String mTermId = null;

    public TermAdapter(Context context,String id) {
        super(R.layout.item_term);
        mContext = context;
        mTermId = id;
    }

    @Override
    protected void convert(BaseViewHolder helper, TermBean item) {


        helper.setText(R.id.tv_term_title, item.getTitle());
        helper.setText(R.id.tv_term_year, "年份："+item.getYear()+"");
        helper.setText(R.id.tv_term_month, "月份："+item.getMonth()+"");
        helper.setText(R.id.tv_term_content, "学期："+item.getTerm());

        helper.addOnClickListener(R.id.iv_term_checked);

        ImageView imageView = helper.getView(R.id.iv_term_checked);

        if(item.getId().equals(mTermId)){
            imageView.setImageDrawable(new IconicsDrawable(mContext)
                    .icon(Ionicons.Icon.ion_android_checkbox_outline)
                    .color(mContext.getResources().getColor(R.color.colorAccent)));
        }else {
            imageView.setImageDrawable(new IconicsDrawable(mContext)
                    .icon(Ionicons.Icon.ion_android_checkbox_outline)
                    .color(mContext.getResources().getColor(R.color.chart_text)));
        }
    }

    public void setChecked(String id) {

        mTermId = id;
        notifyDataSetChanged();
    }
}
