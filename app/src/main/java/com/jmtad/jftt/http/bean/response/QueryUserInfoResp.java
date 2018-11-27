package com.jmtad.jftt.http.bean.response;

import com.google.gson.annotations.SerializedName;
import com.jmtad.jftt.http.bean.node.User;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-10-25 10:21
 **/
public class QueryUserInfoResp extends BaseResponse {
    @SerializedName("data")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User data) {
        this.user = data;
    }


}
