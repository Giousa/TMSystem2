package com.zmm.tmsystem.bean;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2018/8/10
 * Email:65489469@qq.com
 */
public class CommentsBean extends BaseEntity{


    private int level;

    private String content;


    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CommentsBean{" +
                "level=" + level +
                ", content='" + content + '\'' +
                '}';
    }
}
