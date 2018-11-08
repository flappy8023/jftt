package com.jmtad.jftt.http.bean.node;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-10-25 19:01
 **/
public class QueryOilRecordData {
    @SerializedName("list")
    private List<OilRecord> oilRecords;

    public List<OilRecord> getOilRecords() {
        return oilRecords;
    }

    public void setOilRecords(List<OilRecord> oilRecords) {
        this.oilRecords = oilRecords;
    }

    @Override
    public String toString() {
        return "QueryOilRecordData{" +
                "oilRecords=" + oilRecords +
                '}';
    }
}
