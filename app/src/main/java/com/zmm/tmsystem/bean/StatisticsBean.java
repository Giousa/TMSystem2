package com.zmm.tmsystem.bean;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/9/3
 * Email:65489469@qq.com
 */
public class StatisticsBean extends BaseEntity{

    private String title;
    private int total;
    private int male;
    private int female;
    private int primary;
    private int middle;
    private int rongguang;
    private int wuchu;
    private int zhucai;
    private int shiyan;
    private int wuxiao;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getMale() {
        return male;
    }

    public void setMale(int male) {
        this.male = male;
    }

    public int getFemale() {
        return female;
    }

    public void setFemale(int female) {
        this.female = female;
    }

    public int getPrimary() {
        return primary;
    }

    public void setPrimary(int primary) {
        this.primary = primary;
    }

    public int getMiddle() {
        return middle;
    }

    public void setMiddle(int middle) {
        this.middle = middle;
    }

    public int getRongguang() {
        return rongguang;
    }

    public void setRongguang(int rongguang) {
        this.rongguang = rongguang;
    }

    public int getWuchu() {
        return wuchu;
    }

    public void setWuchu(int wuchu) {
        this.wuchu = wuchu;
    }

    public int getZhucai() {
        return zhucai;
    }

    public void setZhucai(int zhucai) {
        this.zhucai = zhucai;
    }

    public int getShiyan() {
        return shiyan;
    }

    public void setShiyan(int shiyan) {
        this.shiyan = shiyan;
    }

    public int getWuxiao() {
        return wuxiao;
    }

    public void setWuxiao(int wuxiao) {
        this.wuxiao = wuxiao;
    }

    @Override
    public String toString() {
        return "StatisticsBean{" +
                "title='" + title + '\'' +
                ", total=" + total +
                ", male=" + male +
                ", female=" + female +
                ", primary=" + primary +
                ", middle=" + middle +
                ", rongguang=" + rongguang +
                ", wuchu=" + wuchu +
                ", zhucai=" + zhucai +
                ", shiyan=" + shiyan +
                ", wuxiao=" + wuxiao +
                '}';
    }
}
