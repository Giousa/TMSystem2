package com.zmm.tmsystem.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/15
 * Time:下午3:58
 */

public class CheckUtils {

    /**
     * 判断是否是数字
     * @param number
     * @return
     */
    public static boolean isOnlyPointNumber(String number) {
        Pattern pattern = Pattern.compile("^\\d+\\.?\\d{0,1}$");
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }
}
