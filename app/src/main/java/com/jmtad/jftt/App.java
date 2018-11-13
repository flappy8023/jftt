package com.jmtad.jftt;

import android.app.Application;

import com.jmtad.jftt.util.wechat.WechatUtil;

import cn.jpush.android.api.JPushInterface;

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
        //初始化极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        //向微信注册
        WechatUtil.getInstance().register();
    }

    public static App getContext() {
        return app;
    }
}
