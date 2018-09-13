package com.zmm.tmsystem.bean;

import java.util.Date;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/6/6
 * Time:上午11:34
 */

public class TeacherBean extends BaseEntity{


    /**
     * id : bbb3e845-0dbe-4379-a7e9-1386ced39cd5
     * phone : 13764503367
     * name : null
     * icon : null
     * gender : null
     * birthday : null
     * courseName : null
     * gradeName : null
     * schoolName : null
     * childcareName : null
     * signDays : null
     * address : null
     * createTime : 1528732800000
     * updateTime : 1528732800000
     * phoneNum1 : null
     * phoneNum2 : null
     */

    private String id;
    private String phone;
    private String name;
    private String icon;
    private int gender;
    private long birthday;
    private String courseName;
    private String gradeName;
    private String schoolName;
    private String childcareName;
    private int signDays;
    private String address;
    private String phoneNum1;
    private String phoneNum2;
    private String appkey;

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getChildcareName() {
        return childcareName;
    }

    public void setChildcareName(String childcareName) {
        this.childcareName = childcareName;
    }

    public int getSignDays() {
        return signDays;
    }

    public void setSignDays(int signDays) {
        this.signDays = signDays;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNum1() {
        return phoneNum1;
    }

    public void setPhoneNum1(String phoneNum1) {
        this.phoneNum1 = phoneNum1;
    }

    public String getPhoneNum2() {
        return phoneNum2;
    }

    public void setPhoneNum2(String phoneNum2) {
        this.phoneNum2 = phoneNum2;
    }

}
