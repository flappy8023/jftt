package com.jmtad.jftt.http.bean.node;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @description:活动实体类
 * @author: luweiming
 * @create: 2018-11-28 10:52
 **/
public class Popup implements Serializable {

    /**
     * 活动对话框弹出延时时间
     */
    @SerializedName("time")
    private long delay;
    @SerializedName("id")
    private String id;
    /**
     * 活动链接地址
     */
    @SerializedName("linkUrl")
    private String linkUrl;
    /**
     * 活动海报
     */
    @SerializedName("imgUrl")
    private String imgUrl;
    /**
     * 对话框弹出动画类型
     */
    @SerializedName("style")
    private int AnimaType;
    /**
     * 活动标题
     */
    @SerializedName("name")
    private String title;
    private long period;
    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getAnimaType() {
        return AnimaType;
    }

    public void setAnimaType(int animaType) {
        AnimaType = animaType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    @Override
    public String toString() {
        return "Popup{" +
                "delay=" + delay +
                ", id='" + id + '\'' +
                ", linkUrl='" + linkUrl + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", AnimaType=" + AnimaType +
                ", title='" + title + '\'' +
                '}';
    }
}
