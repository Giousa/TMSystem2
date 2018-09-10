package com.zmm.tmsystem.common;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/5/23
 * Time:下午5:54
 */

public class Constant {



    public static final String BASE_URL="http://192.168.253.7:8082/tms/";
//    public static final String BASE_URL="http://192.168.1.102:8082/tms/";

    public static final String BASE_IMG_URL="http://uog.oss-cn-shanghai.aliyuncs.com/pic/";

    //时间格式
    private static String FORMAT_TYPE = "yyyy-MM-dd";


    public static final String INTENT_PARAM = "INTENT_PARAM";

    public static final String REGISTER_PARAM = "REGISTER_PARAM";
    public static final String MODIFY_PARAM = "MODIFY_PARAM";
    public static final String TEACHER = "TEACHER";
    public static final String TEACHER_ID = "TEACHER_ID";
    public static final String TMS_COOKIE = "tms_cookie";
    public static final String SIGN = "sign";

    public static final String STUDENT = "STUDENT";
    public static final String STUDENT_ID = "STUDENT_ID";
    public static final String CHILDCARE_STUDENT = "CHILDCARE_STUDENT";
    public static final String CHILDCARE_STUDENT_ID = "CHILDCARE_STUDENT_ID";
    public static final String CHILDCARE_STUDENT_GRADE_LEVEL = "CHILDCARE_STUDENT_GRADE_LEVEL";
    public static final String MONEY_ID = "MONEY_ID";
    public static final String SCORE = "SCORE";
    public static final String CERTIFICATE = "CERTIFICATE";


    //-----------------托管周期点击------------------
    public static final int TYPE_TERM_YEAR = 0;
    public static final int TYPE_TERM_MONTH = 1;
    public static final int TYPE_TERM_TERM = 2;



    //-----------------教师用户信息点击------------------
    public static final int TYPE_ICON = 0;
    public static final int TYPE_NAME = 1;
    public static final int TYPE_GENDER = 2;
    public static final int TYPE_PHONE = 3;
    public static final int TYPE_CHILDCARE_NAME = 4;
    public static final int TYPE_SCHOOL = 5;
    public static final int TYPE_GRADE = 6;
    public static final int TYPE_COURSE = 7;
    public static final int TYPE_ADDRESS = 8;
    public static final int TYPE_MODIFY_PHONE = 9;
    public static final int TYPE_MODIFY_PASSWORD = 10;
    public static final int TYPE_QR_CODE = 11;
    public static final int TYPE_CHANGE = 12;
    public static final int TYPE_VERSION = 13;

    //缓存托管中心id
    public static final String TERM_CLICKED = "TERM_CLICKED";
    public static final String TERM = "TERM";

    //Rxbus消息
    public static final String UPDATE_TEACHER = "UPDATE_TEACHER";
    public static final String UPDATE_TITLE = "UPDATE_TITLE";
    public static final String UPDATE_TERM = "UPDATE_TERM";
    public static final String UPDATE_STUDENT = "UPDATE_STUDENT";
    public static final String UPDATE_STUDENT_CHILDCARE = "UPDATE_STUDENT_CHILDCARE";
    public static final String ADD_CHILDCARE_STUDENT = "ADD_CHILDCARE_STUDENT";


    public static final String ITEM_COMMENTS = "ITEM_COMMENTS";


    //-----------------学生信息点击------------------
    public static final int TYPE_STUDENT_ICON = 0;
    public static final int TYPE_STUDENT_NAME = 1;
    public static final int TYPE_STUDENT_GENDER = 2;
    public static final int TYPE_STUDENT_PHONE = 3;
    public static final int TYPE_STUDENT_ADDRESS = 4;
    public static final int TYPE_STUDENT_GUARDIAN1 = 5;
    public static final int TYPE_STUDENT_GUARDIANPHONE1 = 6;
    public static final int TYPE_STUDENT_GUARDIAN2 = 7;
    public static final int TYPE_STUDENT_GUARDIANPHONE2 = 8;
    public static final int TYPE_STUDENT_SCHOOL = 9;
    public static final int TYPE_STUDENT_GRADE = 10;
    public static final int TYPE_STUDENT_TEACHER = 11;
    public static final int TYPE_STUDENT_TEACHER_PHONE = 12;
    public static final int TYPE_STUDENT_INFO = 13;
    public static final int TYPE_STUDENT_CERTIFICATES = 14;
    public static final int TYPE_STUDENT_PAY = 15;
    public static final int TYPE_STUDENT_BIRTHDAY = 16;
    public static final int TYPE_STUDENT_SCORE = 17;
    //学生当前生日
    public static final String STUDENT_BIRTHDAY = "STUDENT_BIRTHDAY";


    //-----------------学生信息点击------------------
    public static final String CHILDCARE_STUDENT_COUNT = "CHILDCARE_STUDENT_COUNT";
    public static final String CRAM_STUDENT_COUNT = "CRAM_STUDENT_COUNT";
    public static final String EVALUATE_STUDENT_COUNT = "EVALUATE_STUDENT_COUNT";



}
