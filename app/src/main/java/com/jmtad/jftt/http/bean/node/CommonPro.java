package com.jmtad.jftt.http.bean.node;

import com.google.gson.annotations.SerializedName;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-10-26 11:05
 **/
public class CommonPro {
    @SerializedName("id")
    private String id;
    @SerializedName("description")
    private String description;
    @SerializedName("type")
    private String type;
    @SerializedName("createTime")
    private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
