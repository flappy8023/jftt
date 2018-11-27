package com.jmtad.jftt.event;

import com.jmtad.jftt.wechat.model.WXUserInfo;

/**
 * @description:微信登陆完毕的事件
 * @author: luweiming
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
