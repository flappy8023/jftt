package com.jmtad.jftt.http.bean.response;


import com.google.gson.annotations.SerializedName;
import com.jmtad.jftt.http.bean.node.QueryBannerListData;


/**
 * @description:查询最近浏览图文列表响应
 * @author: flappy8023
 * @create: 2018-10-25 10:05
 **/
public class QueryCollectsResp extends BaseResponse {
    @SerializedName("data")
    private QueryBannerListData data;

    public QueryBannerListData getData() {
        return data;
    }

    public void setData(QueryBannerListData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "QueryCollectsResp{" +
                "data=" + data +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
