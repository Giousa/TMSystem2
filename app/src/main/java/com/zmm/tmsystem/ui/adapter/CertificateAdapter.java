package com.zmm.tmsystem.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zmm.tmsystem.R;
import com.zmm.tmsystem.bean.CertificatesBean;
import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.common.utils.DateUtils;

import java.text.ParseException;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/9/4
 * Email:65489469@qq.com
 */
public class CertificateAdapter extends BaseQuickAdapter<CertificatesBean,BaseViewHolder>{

    private Context mContext;

    public CertificateAdapter(Context context){
        super(R.layout.item_certificate);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, CertificatesBean item) {

        helper.setText(R.id.tv_item_title,item.getTitle());
        helper.setText(R.id.tv_item_content,item.getContent());

        long createTime = item.getCreateTime();
        try {
            String s = DateUtils.longToString(createTime, "yyyy-MM-dd");
            helper.setText(R.id.tv_item_time,s);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ImageView imageView1 = helper.getView(R.id.iv_item_1);
        ImageView imageView2 = helper.getView(R.id.iv_item_2);
        ImageView imageView3 = helper.getView(R.id.iv_item_3);

        String pic = item.getPic();
        String[] split = pic.split(",");


        //圆形
//        Glide.with(mContext)
//                .load(Constant.BASE_IMG_URL + split[0])
//                .transform(new GlideCircleTransform(mContext))
//                .into(imageView1);

        Glide.with(mContext)
                .load(Constant.BASE_IMG_URL + split[0])
                .into(imageView1);

       if(split.length == 1){
            imageView2.setVisibility(View.GONE);
            imageView3.setVisibility(View.GONE);

        }else if(split.length == 2){
            imageView2.setVisibility(View.VISIBLE);

            Glide.with(mContext)
                    .load(Constant.BASE_IMG_URL + split[1])
                    .into(imageView2);

        }else if(split.length == 3){
            imageView2.setVisibility(View.VISIBLE);
            imageView3.setVisibility(View.VISIBLE);

           Glide.with(mContext)
                   .load(Constant.BASE_IMG_URL + split[1])
                   .into(imageView2);

           Glide.with(mContext)
                   .load(Constant.BASE_IMG_URL + split[2])
                   .into(imageView3);

        }

        helper.addOnClickListener(R.id.iv_item_1);
        helper.addOnClickListener(R.id.iv_item_2);
        helper.addOnClickListener(R.id.iv_item_3);


    }


}
