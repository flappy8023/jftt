package com.jmtad.jftt.http.bean.response;

import com.google.gson.annotations.SerializedName;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-10-26 13:47
 **/
public class QueryCardResp extends BaseResponse {
    @SerializedName("data")
    private String cardNo;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
}
