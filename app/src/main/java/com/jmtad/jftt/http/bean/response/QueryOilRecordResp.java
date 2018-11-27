package com.jmtad.jftt.http.bean.response;

import com.google.gson.annotations.SerializedName;
import com.jmtad.jftt.http.bean.node.QueryOilRecordData;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-10-25 11:00
 **/
public class QueryOilRecordResp extends BaseResponse {
    @SerializedName("data")
    private QueryOilRecordData data;

    public QueryOilRecordData getData() {
        return data;
    }

    public void setData(QueryOilRecordData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "QueryOilRecordResp{" +
                "data=" + data +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }


}
