package com.jmtad.jftt.http.bean.node;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-10-25 09:36
 **/
public class Banner implements Serializable {
    public static final String STATUS_STARED = "0";
    public static final String STATUS_UNSTARED = "1";
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("imgUrl")
    private String imgUrl;
    @SerializedName("summary")
    private String summary;
    @SerializedName("author")
    private String author;
    @SerializedName("isShowDetails")
    private String isShowDetails;
    @SerializedName("linkUrl")
    private String linkUrl;
    @SerializedName("contentText")
    private String contentText;
    @SerializedName("status")
    private String status;
    @SerializedName("createTime")
    private String createTime;
    @SerializedName("updateTime")
    private String updateTime;
    @SerializedName("praiseVolume")
    private long stars;//点赞量
    @SerializedName("readingVolume")
    private long views;//阅读量
    @SerializedName("isPraise")
    private String starStatus;//0:已点赞  1:未点赞

    public long getStars() {
        return stars;
    }

    public void setStars(long stars) {
        this.stars = stars;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public String getStarStatus() {
        return starStatus;
    }

    public void setStarStatus(String starStatus) {
        this.starStatus = starStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsShowDetails() {
        return isShowDetails;
    }

    public void setIsShowDetails(String isShowDetails) {
        this.isShowDetails = isShowDetails;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", summary='" + summary + '\'' +
                ", author='" + author + '\'' +
                ", isShowDetails='" + isShowDetails + '\'' +
                ", linkUrl='" + linkUrl + '\'' +
                ", contentText='" + contentText + '\'' +
                ", status='" + status + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", stars=" + stars +
                ", views=" + views +
                ", starStatus='" + starStatus + '\'' +
                '}';
    }
}