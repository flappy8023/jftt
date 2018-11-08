package com.jmtad.jftt.event;

import com.jmtad.jftt.wechat.model.WXUserInfo;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-10-16 17:04
 **/
public class WXLoginEvent {
    private WXUserInfo userInfo;

    public WXLoginEvent(WXUserInfo info) {
        this.userInfo = info;
    }

    public WXUserInfo getUserInfo() {
        return userInfo;
    }
}
