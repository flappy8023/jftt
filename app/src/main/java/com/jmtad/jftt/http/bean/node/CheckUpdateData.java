package com.jmtad.jftt.http.bean.node;

import com.google.gson.annotations.SerializedName;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-10-26 10:18
 **/
public class CheckUpdateData {
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("explainText")
    private String explainText;
    /**
     * 是否强制更新（0 否 ； 1 是）
     */
    @SerializedName("isAuto")
    private String isAuto;
    @SerializedName("versionName")
    private String versionName;
    @SerializedName("idenNumber")
    private int idenNumber;
    @SerializedName("downloadUrl")
    private String downloadUrl;
    @SerializedName("updateTime")
    private String updateTime;

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

    public String getExplainText() {
        return explainText;
    }

    public void setExplainText(String explainText) {
        this.explainText = explainText;
    }

    public String getIsAuto() {
        return isAuto;
    }

    public void setIsAuto(String isAuto) {
        this.isAuto = isAuto;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getIdenNumber() {
        return idenNumber;
    }

    public void setIdenNumber(int idenNumber) {
        this.idenNumber = idenNumber;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "CheckUpdateData{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", explainText='" + explainText + '\'' +
                ", isAuto='" + isAuto + '\'' +
                ", versionName='" + versionName + '\'' +
                ", idenNumber=" + idenNumber +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
