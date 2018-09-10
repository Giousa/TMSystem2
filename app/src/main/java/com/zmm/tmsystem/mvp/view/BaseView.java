package com.zmm.tmsystem.mvp.view;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/6
 * Time:上午11:26
 */

public interface BaseView {

    void showLoading();
    void showError(String msg);
    void dismissLoading();
}
