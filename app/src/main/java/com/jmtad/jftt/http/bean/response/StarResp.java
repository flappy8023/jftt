package com.jmtad.jftt.http.bean.response;

import com.google.gson.annotations.SerializedName;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-10-26 13:41
 **/
public class StarResp extends BaseResponse {
    public static final String TYPE_STAR = "0";
    public static final String TYPE_UNSTAR = "1";
    /**
     * 操作类型 0:点赞 1:取消点赞
     */
    @SerializedName("data")
    private String oprType;

    public String getOprType() {
        return oprType;
    }

    public void setOprType(String oprType) {
        this.oprType = oprType;
    }
}
