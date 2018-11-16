package com.jmtad.jftt.module.push;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jmtad.jftt.http.bean.node.Banner;
import com.jmtad.jftt.module.banner.BannerDetailActivity;
import com.jmtad.jftt.module.banner.BannerLinkActivity;
import com.jmtad.jftt.module.login.ui.SplashActivity;
import com.jmtad.jftt.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-11-13 14:25
 **/
public class CustomReceiver extends BroadcastReceiver {
    private static final String TAG = "CustomReceiver";
    private Gson gson = new Gson();

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            LogUtil.debug(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                LogUtil.debug(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                LogUtil.debug(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                processCustomMessage(context, bundle);

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                LogUtil.debug(TAG, "[MyReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                LogUtil.debug(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                LogUtil.debug(TAG, "[MyReceiver] 用户点击打开了通知");
                String extraParams = bundle.getString(JPushInterface.EXTRA_EXTRA);
                //如果配置了扩展参数
                if (!TextUtils.isEmpty(extraParams)) {
                    ExtraData extraData = gson.fromJson(extraParams, ExtraData.class);
                    if (null != extraData) {
                        String type = extraData.getType();
                        switch (type) {
                            //打开图文
                            case "1":
                                if (appOnForeground(context)) {
                                    openBannerDetail(context, extraData);
                                } else {
                                    Intent intent1 = new Intent(context, BannerDetailActivity.class);
                                    Banner banner = new Banner();
                                    banner.setId(extraData.getId());
                                    intent1.putExtra(BannerDetailActivity.KEY_BANNER, banner);
                                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                                    stackBuilder.addParentStack(BannerDetailActivity.class);
                                    stackBuilder.addNextIntent(intent1);
                                    stackBuilder.startActivities();
                                }
                                break;
                            //跳转URL
                            case "2":
                                if (appOnForeground(context)) {
                                    openUrl(context, extraData);
                                } else {
                                    Intent intent1 = new Intent(context, BannerLinkActivity.class);
                                    intent1.putExtra(BannerLinkActivity.KEY_LINK_URL, extraData.getUrl());
                                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                                    stackBuilder.addParentStack(BannerLinkActivity.class);
                                    stackBuilder.addNextIntent(intent1);
                                    stackBuilder.startActivities();
                                }
                                break;
                            case "0":
                            default:
                                if (!appOnForeground(context)) {
                                    openHome(context);
                                }
                                break;
                        }
                    }
                } else {
                    openHome(context);
                }


            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                LogUtil.debug(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                LogUtil.info(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                LogUtil.debug(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {

        }
    }

    private void openUrl(Context context, ExtraData extraData) {
        String url = extraData.getUrl();
        Intent i = new Intent(context, BannerLinkActivity.class);
        i.putExtra(BannerLinkActivity.KEY_LINK_URL, url);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    private void openBannerDetail(Context context, ExtraData extraData) {
        String id = extraData.getId();
        Intent i = new Intent(context, BannerDetailActivity.class);
        Banner banner = new Banner();
        banner.setId(id);
        i.putExtra(BannerDetailActivity.KEY_BANNER, banner);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    /**
     * 打开应用首页
     *
     * @param context
     */
    private void openHome(Context context) {
        Intent i = new Intent(context, SplashActivity.class);
        //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(i);
    }

    /**
     * @param context
     * @param data
     */
    private void openHomeWithData(Context context, ExtraData data) {

    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    LogUtil.info(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    LogUtil.error(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.get(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
    }


    private boolean appOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();

        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName()) && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }
}
