package com.zmm.tmsystem.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/7/8
 * Time:下午12:09
 */

public class PicUtils {

    /**
     *  以最省内存的方式读取本地资源的图片
     *  @param context
     *  @param resId
     *  @return
     */
    public static Bitmap readBitMap(Context context, int resId){

        BitmapFactory.Options opt = new  BitmapFactory.Options();

        opt.inPreferredConfig =  Bitmap.Config.RGB_565;

        opt.inPurgeable = true;

        opt.inInputShareable = true;

        //  获取资源图片

        InputStream is =  context.getResources().openRawResource(resId);

        return  BitmapFactory.decodeStream(is, null, opt);

    }
}
