package com.zmm.tmsystem.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.zmm.tmsystem.R;
import com.zmm.tmsystem.bean.ChildcareStudentBean;
import com.zmm.tmsystem.bean.StudentBean;
import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.common.utils.AgeUtils;
import com.zmm.tmsystem.ui.widget.GlideCircleTransform;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/17
 * Time:下午4:41
 */

public class ChildcareStudentAdapter extends BaseQuickAdapter<ChildcareStudentBean, BaseViewHolder> {

    private Context mContext;

    public ChildcareStudentAdapter(Context context) {
        super(R.layout.item_student_childcare);
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ChildcareStudentBean childcareStudentBean) {


        //姓名
        helper.setText(R.id.tv_student_name, childcareStudentBean.getStudent().getName());

        //学校
        String schoolName = childcareStudentBean.getSchool();

        if(TextUtils.isEmpty(schoolName)){
            helper.setText(R.id.tv_student_school,"");
            helper.getView(R.id.tv_line_grade_show).setVisibility(View.GONE);
        }else {
            helper.setText(R.id.tv_student_school, "学校：" + schoolName );
            helper.getView(R.id.tv_line_grade_show).setVisibility(View.VISIBLE);
        }


//        helper.setText(R.id.tv_student_gender, ((gender == 0)?"性别：女":"性别：男"));
//        long birthday = childcareStudentBean.getStudent().getBirthday();
//        LinearLayout llAge = helper.getView(R.id.ll_age);
//        if(birthday == 0){
//            llAge.setVisibility(View.GONE);
//        }else {
//            llAge.setVisibility(View.VISIBLE);
//            helper.setText(R.id.tv_student_age,"年龄："+AgeUtils.getAgeFromBirthTime(birthday));
//        }


        //班级
        LinearLayout llGrade = helper.getView(R.id.ll_grade);
        if (TextUtils.isEmpty(childcareStudentBean.getGrade())) {
            llGrade.setVisibility(View.GONE);
        } else {
            llGrade.setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_student_grade, "班级：" + childcareStudentBean.getGrade());
        }


        //性别
        int gender = childcareStudentBean.getStudent().getGender();
        ImageView genderImage = helper.getView(R.id.iv_student_gender);

        if(gender == 0){
            genderImage.setImageDrawable(new IconicsDrawable(mContext)
                    .icon(Ionicons.Icon.ion_female)
                    .color(mContext.getResources().getColor(R.color.colorAccent)));
        }else {
            genderImage.setImageDrawable(new IconicsDrawable(mContext)
                    .icon(Ionicons.Icon.ion_male)
                    .color(mContext.getResources().getColor(R.color.colorPrimary)));
        }

        //头像
        ImageView imageView = helper.getView(R.id.iv_student_icon);

        String icon = childcareStudentBean.getStudent().getIcon();
        if (TextUtils.isEmpty(icon)) {

            if (gender == 0) {
                imageView.setImageDrawable(new IconicsDrawable(mContext)
                        .icon(Ionicons.Icon.ion_android_contact)
                        .color(mContext.getResources().getColor(R.color.colorAccent)));

            } else {
                imageView.setImageDrawable(new IconicsDrawable(mContext)
                        .icon(Ionicons.Icon.ion_android_contact)
                        .color(mContext.getResources().getColor(R.color.colorPrimary)));
            }

        } else {

            if (gender == 0) {
                Glide.with(mContext)
                        .load(Constant.BASE_IMG_URL + icon)
                        .transform(new GlideCircleTransform(mContext))
                        .error(new IconicsDrawable(mContext)
                                .icon(Ionicons.Icon.ion_android_contact)
                                .color(mContext.getResources().getColor(R.color.colorAccent)
                                ))
                        .into(imageView);

            } else{

                Glide.with(mContext)
                        .load(Constant.BASE_IMG_URL + icon)
                        .transform(new GlideCircleTransform(mContext))
                        .error(new IconicsDrawable(mContext)
                                .icon(Ionicons.Icon.ion_android_contact)
                                .color(mContext.getResources().getColor(R.color.colorPrimary)
                                ))
                        .into(imageView);
            }

        }


    }


}
