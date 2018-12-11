package com.jmtad.jftt;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.jmtad.jftt.util.LogUtil;
import com.jmtad.jftt.util.wechat.WechatUtil;
import com.tencent.bugly.crashreport.CrashReport;

import cn.jpush.android.api.JPushInterface;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-10-11 09:17
 **/
public class App extends Application {
    private static final String TAG = "App";
    private static App app;
    private OnAppListener appListener;
    private boolean isOnback = false;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        //初始化极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        //向微信注册
        WechatUtil.getInstance().register();
        //监听前后台切换
        registerActivityLifecycleCallbacks(callbacks);
        boolean b = BuildConfig.DEBUG;
        //初始化腾讯bugly
        CrashReport.initCrashReport(getApplicationContext(), "62dd9f041a", b);
    }

    private ActivityLifecycleCallbacks callbacks = new ActivityLifecycleCallbacks() {
        private int activityStartCount = 0;

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityStarted(Activity activity) {
            activityStartCount++;
            //数值从0到1说明切换到前台
            if (activityStartCount == 1) {
                LogUtil.debug(TAG, "switch to front , activity name:" + activity.getClass().getSimpleName());
                isOnback = false;
                if (null != appListener) {
                    appListener.onFront();
                }
            }
        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            activityStartCount--;
            //数值从1变为0说明切换到后台
            if (activityStartCount == 0) {
                isOnback = true;
                LogUtil.debug(TAG, "switch to back, activity name: " + activity.getClass().getSimpleName());
                if (null != appListener) {
                    appListener.onBack();
                }
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };

    public static App getContext() {
        return app;
    }

    public boolean isOnBack() {
        return isOnback;
    }

    public void setAppListener(OnAppListener appListener) {
        this.appListener = appListener;
    }

    public interface OnAppListener {
        void onFront();//切换到前台

        void onBack();//切换到后台
    }
}
