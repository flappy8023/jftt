package com.jmtad.jftt.http.bean.response;

import com.google.gson.annotations.SerializedName;
import com.jmtad.jftt.http.bean.node.SaveAuthRespData;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-10-27 13:37
 **/
public class SaveAuthResp extends BaseResponse {
    @SerializedName("data")
    private SaveAuthRespData data;

    public SaveAuthRespData getData() {
        return data;
    }

    public void setData(SaveAuthRespData data) {
        this.data = data;
    }
}
