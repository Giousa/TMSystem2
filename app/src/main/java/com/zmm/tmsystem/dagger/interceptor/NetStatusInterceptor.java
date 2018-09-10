package com.zmm.tmsystem.dagger.interceptor;

import android.content.Context;
import android.text.TextUtils;

import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.common.utils.ACache;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;


/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/14
 * Time:下午5:28
 */

public class NetStatusInterceptor implements Interceptor {

    private Context context;

    public NetStatusInterceptor(Context context) {
        super();
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {


        return null;
    }
}
