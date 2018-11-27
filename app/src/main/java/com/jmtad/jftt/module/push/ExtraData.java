package com.jmtad.jftt.module.push;

import com.google.gson.annotations.SerializedName;

/**
 * @description:自定义推送扩展参数实体类
 * @author: luweiming
 * @create: 2018-11-15 11:29
 **/
public class ExtraData {
    @SerializedName("type")
    private String type;
    @SerializedName("id")
    private String id;
    @SerializedName("url")
    private String url;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
