package com.zmm.tmsystem.http.cookie;

import android.content.Context;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/14
 * Time:下午4:58
 */

public class CookiesManager implements CookieJar {

    private Context mContext;

    private PersistentCookieStore cookieStore;

    public CookiesManager(Context context) {
        mContext = context;
        cookieStore = new PersistentCookieStore(mContext);
    }


    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        return cookies;
    }
}
