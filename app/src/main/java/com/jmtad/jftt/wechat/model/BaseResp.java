package com.jmtad.jftt.wechat.model;

import com.google.gson.annotations.SerializedName;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-10-16 17:49
 **/
public class BaseResp {
    public static final String CODE_OK = "0";
    @SerializedName("errcode")
    private String errcode;
    @SerializedName("errmsg")
    private String errmsg;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    @Override
    public String toString() {
        return "BaseResp{" +
                "errcode='" + errcode + '\'' +
                ", errmsg='" + errmsg + '\'' +
                '}';
    }
}
