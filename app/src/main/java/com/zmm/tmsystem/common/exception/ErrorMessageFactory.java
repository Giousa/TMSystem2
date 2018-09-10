package com.zmm.tmsystem.common.exception;

import android.content.Context;

import com.zmm.tmsystem.R;


/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/5/25
 * Time:下午1:30
 */

public class ErrorMessageFactory {


    public static  String create(Context context, int code){




        String errorMsg = null ;


        switch (code){

            case BaseException.SOCKET_TIMEOUT_ERROR:

                errorMsg =  context.getResources().getString(R.string.error_socket_timeout);

                break;
            case BaseException.SOCKET_ERROR:

                errorMsg =  context.getResources().getString(R.string.error_socket_error);

                break;

            case ApiException.ERROR_API_SYSTEM:
                errorMsg = context.getResources().getString(R.string.error_system);
                break;

            case ApiException.ERROR_API_PARAM_EMPTY:
                errorMsg = context.getResources().getString(R.string.error_param_empty);
                break;


            case ApiException.ERROR_API_USER_EXIST:
                errorMsg = context.getResources().getString(R.string.error_user_exist);
                break;

            case ApiException.ERROR_API_USER_NONE:
                errorMsg = context.getResources().getString(R.string.error_user_none);
                break;

            case ApiException.ERROR_API_PSW_ERROR:
                errorMsg = context.getResources().getString(R.string.error_psw_error);
                break;


            case ApiException.ERROR_API_SIGN_EXIST:
                errorMsg = context.getResources().getString(R.string.error_sign_exist);
                break;

            case ApiException.ERROR_API_SINE_NONE:
                errorMsg = context.getResources().getString(R.string.error_sign_none);
                break;

            case ApiException.ERROR_API_STUDENT_EXIST:
                errorMsg = context.getResources().getString(R.string.error_student_exist);
                break;

            case ApiException.ERROR_API_STUDENT_EXIST_NONE:
                errorMsg = context.getResources().getString(R.string.error_student_exist_none);
                break;

            case ApiException.ERROR_API_STUDENT_ADD:
                errorMsg = context.getResources().getString(R.string.error_student_add);
                break;

            case ApiException.ERROR_API_STUDENT_OTHER_EXIST:
                errorMsg = context.getResources().getString(R.string.error_student_other_exist);
                break;

            case ApiException.ERROR_API_VERIFY_TIMEOUT:
                errorMsg = context.getResources().getString(R.string.error_verify_timeout);
                break;

            case ApiException.ERROR_API_VERIFY_SEND:
                errorMsg = context.getResources().getString(R.string.error_verify_send);
                break;

            case ApiException.ERROR_API_VERIFY_ERROR:
                errorMsg = context.getResources().getString(R.string.error_verify_error);
                break;

            case ApiException.ERROR_API_PIC_UPLOAD_FAILURE:
                errorMsg = context.getResources().getString(R.string.error_pic_upload_failure);
                break;

            case ApiException.ERROR_API_PARAM_ID_ERROR:
                errorMsg = context.getResources().getString(R.string.error_param_id_error);
                break;


            default:
                errorMsg=context.getResources().getString(R.string.error_unkown);
                break;


        }


        return  errorMsg;


    }
}
