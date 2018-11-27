package com.jmtad.jftt.http.bean.response;

import com.google.gson.annotations.SerializedName;
import com.jmtad.jftt.http.bean.node.CommonPro;

import java.util.List;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-10-26 11:07
 **/
public class QueryCommonProResp extends BaseResponse {
    @SerializedName("data")
    private List<CommonPro> commonPros;

    public List<CommonPro> getCommonPros() {
        return commonPros;
    }

    public void setCommonPros(List<CommonPro> commonPros) {
        this.commonPros = commonPros;
    }
}
