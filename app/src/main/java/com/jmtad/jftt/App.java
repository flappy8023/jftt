package com.jmtad.jftt;

import android.app.Application;

import com.jmtad.jftt.util.wechat.WechatUtil;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-10-11 09:17
 **/
public class App extends Application {
    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        //向微信注册
        WechatUtil.getInstance().register();
    }

    public static App getContext() {
        return app;
    }
}
