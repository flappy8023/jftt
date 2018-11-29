package com.jmtad.jftt.http.bean.response;

import com.google.gson.annotations.SerializedName;
import com.jmtad.jftt.http.bean.node.Popup;

import java.util.List;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-11-29 15:49
 **/
public class QueryPopupResp extends BaseResponse {
    @SerializedName("data")
    private List<Popup> popups;

    public List<Popup> getPopups() {
        return popups;
    }

    public void setPopups(List<Popup> popups) {
        this.popups = popups;
    }
}
