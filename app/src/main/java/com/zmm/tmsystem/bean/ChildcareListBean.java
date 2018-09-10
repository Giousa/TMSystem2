package com.zmm.tmsystem.bean;

import java.util.List;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/7/11
 * Time:上午11:23
 */

public class ChildcareListBean extends BaseEntity {

    private List<TermBean> mTermBeanList;

    public List<TermBean> getTermBeanList() {
        return mTermBeanList;
    }

    public void setTermBeanList(List<TermBean> termBeanList) {
        mTermBeanList = termBeanList;
    }
}
