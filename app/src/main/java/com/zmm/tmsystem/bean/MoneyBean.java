package com.zmm.tmsystem.bean;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/9/5
 * Email:65489469@qq.com
 */
public class MoneyBean extends BaseEntity{


    /**
     * id : d0fc47a0-6dfe-4c79-8b1e-f23f191eb0a8
     * studentId : 4fdd4fec-9c8b-4086-8894-1144be9487c6
     * surplus : 0
     * lastPay : 0
     * lastPayTime : 1536137273234
     * lastDeposit : 0
     * lastDepositTime : 1536137273234
     * totalPay : 0
     * totalDeposit : 0
     * createTime : 1536137273234
     * updateTime : 1536137273234
     * active : 0
     */

    private String id;
    private String studentId;
    private float surplus;
    private float lastPay;
    private long lastPayTime;
    private float lastDeposit;
    private long lastDepositTime;
    private float totalPay;
    private float totalDeposit;
    private long createTime;
    private long updateTime;
    private int active;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public float getSurplus() {
        return surplus;
    }

    public void setSurplus(float surplus) {
        this.surplus = surplus;
    }

    public float getLastPay() {
        return lastPay;
    }

    public void setLastPay(float lastPay) {
        this.lastPay = lastPay;
    }

    public long getLastPayTime() {
        return lastPayTime;
    }

    public void setLastPayTime(long lastPayTime) {
        this.lastPayTime = lastPayTime;
    }

    public float getLastDeposit() {
        return lastDeposit;
    }

    public void setLastDeposit(float lastDeposit) {
        this.lastDeposit = lastDeposit;
    }

    public long getLastDepositTime() {
        return lastDepositTime;
    }

    public void setLastDepositTime(long lastDepositTime) {
        this.lastDepositTime = lastDepositTime;
    }

    public float getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(float totalPay) {
        this.totalPay = totalPay;
    }

    public float getTotalDeposit() {
        return totalDeposit;
    }

    public void setTotalDeposit(float totalDeposit) {
        this.totalDeposit = totalDeposit;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "MoneyBean{" +
                "id='" + id + '\'' +
                ", studentId='" + studentId + '\'' +
                ", surplus=" + surplus +
                ", lastPay=" + lastPay +
                ", lastPayTime=" + lastPayTime +
                ", lastDeposit=" + lastDeposit +
                ", lastDepositTime=" + lastDepositTime +
                ", totalPay=" + totalPay +
                ", totalDeposit=" + totalDeposit +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", active=" + active +
                '}';
    }
}
