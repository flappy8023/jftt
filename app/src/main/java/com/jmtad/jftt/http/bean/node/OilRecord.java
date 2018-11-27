package com.jmtad.jftt.http.bean.node;

import com.google.gson.annotations.SerializedName;

/**
 * @description:交易记录详情
 * @author: luweiming
 * @create: 2018-10-25 11:14
 **/
public class OilRecord {
    public static final String TYPE_ALL = "0";
    public static final String TYPE_INCOME = "1";
    public static final String TYPE_EXPEND = "2";
    @SerializedName("oilNum")
    private String oilNum;
    @SerializedName("fromName")
    private String fromName;
    @SerializedName("operationType")
    private String type;
    @SerializedName("operationTime")
    private String operationTime;

    public String getOilNum() {
        return oilNum;
    }

    public void setOilNum(String oilNum) {
        this.oilNum = oilNum;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(String operationTime) {
        this.operationTime = operationTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "OilRecord{" +
                "oilNum='" + oilNum + '\'' +
                ", fromName='" + fromName + '\'' +
                ", type='" + type + '\'' +
                ", operationTime='" + operationTime + '\'' +
                '}';
    }
}
