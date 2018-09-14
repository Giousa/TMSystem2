package com.zmm.tmsystem.common.utils;

import android.content.Context;

import com.zmm.tmsystem.bean.TeacherBean;
import com.zmm.tmsystem.common.Constant;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/16
 * Time:下午10:51
 */

public class TeacherCacheUtil {

    public static void save(Context context, TeacherBean teacherBean){

        ACache aCache = ACache.get(context);

        aCache.put(Constant.TEACHER_ID,teacherBean.getId());
        aCache.put(Constant.TEACHER,teacherBean);
    }

    public static void clear(Context context){
        ACache aCache = ACache.get(context);

        aCache.put(Constant.TEACHER_ID,"");
        aCache.put(Constant.TEACHER,"");
        aCache.put(Constant.CHILDCARE_STUDENT_COUNT,"");
        aCache.put(Constant.CRAM_STUDENT_COUNT,"");
        aCache.put(Constant.SIGN,"");
        aCache.put(Constant.STUDENT,"");
        aCache.put(Constant.TERM,"");

    }
}
