package com.jmtad.jftt.http.bean.response;

import com.google.gson.annotations.SerializedName;
import com.jmtad.jftt.http.bean.node.Banner;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-11-08 11:58
 **/
public class QueryBannerDetailResp extends BaseResponse {
    @SerializedName("data")
    private Banner banner;

    public Banner getBanner() {
        return banner;
    }

    public void setBanner(Banner banner) {
        this.banner = banner;
    }
}
