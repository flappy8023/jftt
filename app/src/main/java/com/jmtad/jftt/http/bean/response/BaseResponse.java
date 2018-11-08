package com.jmtad.jftt.http.bean.response;

import com.google.gson.annotations.SerializedName;


/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.pukka.ydepg.common.http.v6bean.v6response.BaseResponse.java
 * @author: yh
 * @date: 2017-05-31 14:30
 */

public class BaseResponse {
    public static final String CODE_0 = "0";
    public static final String CODE_1 = "1";
    @SerializedName("code")
    protected String code;

    @SerializedName("msg")
    protected String msg;


    public BaseResponse() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
