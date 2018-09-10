package com.zmm.tmsystem.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.ionicons_typeface_library.Ionicons;
import com.zmm.tmsystem.R;
import com.zmm.tmsystem.bean.ChildcareStudentBean;
import com.zmm.tmsystem.bean.CommentsBean;
import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.ui.widget.GlideCircleTransform;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/17
 * Time:下午4:41
 */

public class CommentAdapter extends BaseQuickAdapter<ChildcareStudentBean,BaseViewHolder>{

    private Context mContext;

    private OnRatingBarClickListener mOnRatingBarClickListener;

    public CommentAdapter(Context context) {
        super(R.layout.item_comment);
        mContext = context;
    }

    public void setOnRatingBarClickListener(OnRatingBarClickListener onRatingBarClickListener) {
        mOnRatingBarClickListener = onRatingBarClickListener;
    }

    public interface OnRatingBarClickListener{

        void OnRatingBarClick(String id,float rating);
    }

    @Override
    protected void convert(BaseViewHolder helper, final ChildcareStudentBean childcareStudentBean) {


        helper.setText(R.id.tv_student_name, childcareStudentBean.getStudent().getName());
        String schoolName = childcareStudentBean.getSchool();
        helper.setText(R.id.tv_student_school, (TextUtils.isEmpty(schoolName)) ? "":schoolName);
        int gender = childcareStudentBean.getStudent().getGender();
//        helper.setText(R.id.tv_student_gender, ((gender == 0)?"女":"男"));
        helper.setText(R.id.tv_student_grade, (TextUtils.isEmpty(childcareStudentBean.getGrade())) ? "":childcareStudentBean.getGrade());

        ImageView imageView = helper.getView(R.id.iv_student_icon);

        String icon = childcareStudentBean.getStudent().getIcon();
        if(TextUtils.isEmpty(icon)){

            if(gender == 0){
                imageView.setImageDrawable(new IconicsDrawable(mContext)
                        .icon(Ionicons.Icon.ion_android_contact)
                        .color(mContext.getResources().getColor(R.color.colorAccent)));

            }else {
                imageView.setImageDrawable(new IconicsDrawable(mContext)
                        .icon(Ionicons.Icon.ion_android_contact)
                        .color(mContext.getResources().getColor(R.color.colorPrimary)));
            }

        }else {
            Glide.with(mContext)
                    .load(Constant.BASE_IMG_URL+icon)
                    .transform(new GlideCircleTransform(mContext))
                    .error(new IconicsDrawable(mContext)
                            .icon(Ionicons.Icon.ion_android_contact)
                            .color(mContext.getResources().getColor(R.color.colorAccent)
                            ))
                    .into(imageView);
            }


        final RatingBar ratingBar = helper.getView(R.id.rating_bar);

        CommentsBean comments = childcareStudentBean.getComments();
        ratingBar.setRating(comments.getLevel()*1.0f/2);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                System.out.println("fromUser = "+fromUser);

                if(fromUser){
                    //此时是用户手动点击，而非直接赋值
                    if(mOnRatingBarClickListener != null){
                        mOnRatingBarClickListener.OnRatingBarClick(childcareStudentBean.getId(),rating);
                    }
                }

            }

        });

    }


}
