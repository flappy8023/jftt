package com.jmtad.jftt.http.bean.response;

import com.google.gson.annotations.SerializedName;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-10-26 13:41
 **/
public class CollectResp extends BaseResponse {
    public static final String TYPE_STAR = "0";
    public static final String TYPE_UNSTAR = "1";
    /**
     * 操作类型 0:收藏 1:取消收藏
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
