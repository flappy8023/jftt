package com.jmtad.jftt.http.bean.response;

import com.google.gson.annotations.SerializedName;
import com.jmtad.jftt.http.bean.node.CheckUpdateData;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-10-26 10:18
 **/
public class CheckUpdateResp extends BaseResponse {
    @SerializedName("data")
    private CheckUpdateData data;

    public CheckUpdateData getData() {
        return data;
    }

    public void setData(CheckUpdateData data) {
        this.data = data;
    }
}
