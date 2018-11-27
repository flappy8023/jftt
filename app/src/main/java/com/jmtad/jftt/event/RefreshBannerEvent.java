package com.jmtad.jftt.event;

import com.jmtad.jftt.http.bean.node.Banner;

/**
 * @description:首页刷新当前图文内容的触发事件
 * @author: luweiming
 * @create: 2018-11-09 09:41
 **/
public class RefreshBannerEvent {
    private Banner currentBanner;

    public Banner getCurrentBanner() {
        return currentBanner;
    }

    public void setCurrentBanner(Banner currentBanner) {
        this.currentBanner = currentBanner;
    }
}
