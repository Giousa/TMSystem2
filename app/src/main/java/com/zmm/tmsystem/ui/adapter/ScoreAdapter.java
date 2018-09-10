package com.zmm.tmsystem.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zmm.tmsystem.R;
import com.zmm.tmsystem.bean.ScoreBean;
import com.zmm.tmsystem.common.utils.DateUtils;

import java.text.ParseException;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/9/4
 * Email:65489469@qq.com
 */
public class ScoreAdapter extends BaseQuickAdapter<ScoreBean,BaseViewHolder>{

    private int mGradeLevel;

    public ScoreAdapter(int gradeLevel){
        super(R.layout.item_score);
        mGradeLevel = gradeLevel;
    }

    @Override
    protected void convert(BaseViewHolder helper, ScoreBean item) {

        if(mGradeLevel >= 7){
            helper.getView(R.id.tv_score_physics_title).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_score_chemistry_title).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_score_physics).setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_score_chemistry).setVisibility(View.VISIBLE);

            helper.setText(R.id.tv_score_physics,item.getPhysics()+"");
            helper.setText(R.id.tv_score_chemistry,item.getChemistry()+"");
        }

        helper.setText(R.id.tv_score_chinese,item.getChinese()+"")
                .setText(R.id.tv_score_math,item.getMaths()+"")
                .setText(R.id.tv_score_english,item.getEnglish()+"")
                .setText(R.id.tv_score_title,item.getTitle());


        long createTime = item.getCreateTime();
        try {
            String time = DateUtils.longToString(createTime, null);
            helper.setText(R.id.tv_score_time,time);

        } catch (ParseException e) {
            e.printStackTrace();
        }




    }
}
