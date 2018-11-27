package com.jmtad.jftt.http.bean.response;

import com.google.gson.annotations.SerializedName;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-10-25 10:56
 **/
public class QueryOilBalanceResp extends BaseResponse {
    @SerializedName("data")
    private String oilNum;

    public String getOilNum() {
        return oilNum;
    }

    public void setOilNum(String oilNum) {
        this.oilNum = oilNum;
    }

    @Override
    public String toString() {
        return "QueryOilBalanceResp{" +
                "oilNum='" + oilNum + '\'' +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
