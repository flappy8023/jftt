package com.jmtad.jftt.http.bean.response;

import com.google.gson.annotations.SerializedName;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-10-26 13:36
 **/
public class AddReadVolumeResp extends BaseResponse {
    @SerializedName("data")
    private long views;

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }
}
