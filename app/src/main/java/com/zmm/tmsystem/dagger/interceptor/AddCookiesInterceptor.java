package com.zmm.tmsystem.dagger.interceptor;

import android.content.Context;
import android.text.TextUtils;

import com.zmm.tmsystem.common.Constant;
import com.zmm.tmsystem.common.utils.ACache;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;


/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/14
 * Time:下午5:28
 */

public class AddCookiesInterceptor implements Interceptor {

    private Context context;

    public AddCookiesInterceptor(Context context) {
        super();
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        ACache aCache = ACache.get(context);
        String cookieStr = aCache.getAsString(Constant.TMS_COOKIE);

        System.out.println("cookieStr22 = "+cookieStr);
        if (!TextUtils.isEmpty(cookieStr)) {
            return chain.proceed(chain.request().newBuilder().header("Cookie", cookieStr).build());
        }
        return chain.proceed(chain.request());
    }
}
