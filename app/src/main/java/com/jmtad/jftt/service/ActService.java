package com.jmtad.jftt.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.jmtad.jftt.App;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @description:后台任务用于定时弹出活动对话框
 * @author: luweiming
 * @create: 2018-11-27 14:37
 **/
public class ActService extends Service {
    public static final String TIMER_ACTION = "com.jmtad.jftt.act";
    Timer timer;
    TimerTask timerTask;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        App.getContext().setAppListener(new App.OnAppListener() {
            @Override
            public void onFront() {
                startTimer();
            }

            @Override
            public void onBack() {
                if (null != timerTask) {
                    timerTask.cancel();
                    timerTask = null;
                }
                if (null != timer) {
                    timer.cancel();
                    timer = null;
                }
            }
        });
    }

    private void startTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setAction(TIMER_ACTION);

                sendBroadcast(intent);
            }
        };
        timer.schedule(timerTask, 10 * 1000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startTimer();
        return super.onStartCommand(intent, flags, startId);
    }
}
