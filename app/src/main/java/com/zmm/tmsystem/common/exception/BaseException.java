package com.zmm.tmsystem.common.exception;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/5/25
 * Time:上午10:29
 */

public class BaseException extends Exception {

    //200：成功
    //201：异常捕获 显示：服务器繁忙
    //500：参数不能为空
    //501：用户已存在
    //502：用户不存在
    //503：密码错误
    //504：已签到
    //505：未签到
    //400：验证码已发送
    //401：验证码过期
    //402：验证码错误
    //403：图片上传失败

    //服务器异常捕获
    public static final int ERROR_API_SYSTEM = 201;
    //参数id异常
    public static final int ERROR_API_PARAM_ID_ERROR = 499;
    //参数不能为空
    public static final int ERROR_API_PARAM_EMPTY = 500;
    //用户已存在
    public static final int ERROR_API_USER_EXIST = 501;
    //用户不存在
    public static final int ERROR_API_USER_NONE = 502;
    //密码错误
    public static final int ERROR_API_PSW_ERROR = 503;
    //已签到
    public static final int ERROR_API_SIGN_EXIST= 504;
    //未签到
    public static final int ERROR_API_SINE_NONE = 505;
    //学生已存在
    public static final int ERROR_API_STUDENT_EXIST = 506;
    //学生已添加
    public static final int ERROR_API_STUDENT_ADD = 507;
    //无法删除，存在托管或补习学生
    public static final int ERROR_API_STUDENT_OTHER_EXIST = 508;
    //学生不存在
    public static final int ERROR_API_STUDENT_EXIST_NONE = 509;




    //验证码已发送
    public static final int ERROR_API_VERIFY_SEND = 400;
    //验证码过期
    public static final int ERROR_API_VERIFY_TIMEOUT = 401;
    //验证码错误
    public static final int ERROR_API_VERIFY_ERROR = 402;


    //图片上传失败
    public static final int ERROR_API_PIC_UPLOAD_FAILURE = 403;


    //Base Exception
    /*连接网络超时*/
    public static final int SOCKET_TIMEOUT_ERROR = 10004;
    /*无网络连接*/
    public static final int SOCKET_ERROR = 10003;
    /*未知错误*/
    public static final int UNKNOWN_ERROR = 10002;



    private int code;

    private  String displayMessage;

    public BaseException() {
    }

    public BaseException(int code, String displayMessage) {
        this.code = code;
        this.displayMessage = displayMessage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }
}
