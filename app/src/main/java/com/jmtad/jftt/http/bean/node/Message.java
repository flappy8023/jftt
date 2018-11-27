package com.jmtad.jftt.http.bean.node;

import com.google.gson.annotations.SerializedName;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-10-25 10:38
 **/
public class Message {
    @SerializedName("id")
    private String id;
    @SerializedName("userId")
    private String userId;
    @SerializedName("msgTitle")
    private String title;
    @SerializedName("msgDetail")
    private String detail;
    @SerializedName("createTime")
    private String createTime;
    @SerializedName("type")
    private String type;
    /**
     * 1:已读
     */
    @SerializedName("readFlag")
    private String readFlag;
    @SerializedName("readTime")
    private String readTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(String readFlag) {
        this.readFlag = readFlag;
    }

    public String getReadTime() {
        return readTime;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                ", createTime='" + createTime + '\'' +
                ", type='" + type + '\'' +
                ", readFlag='" + readFlag + '\'' +
                ", readTime='" + readTime + '\'' +
                '}';
    }
}
