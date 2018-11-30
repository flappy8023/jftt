package com.jmtad.jftt.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.jmtad.jftt.App;
import com.jmtad.jftt.event.PopupEvent;
import com.jmtad.jftt.event.ShowPopupEvent;
import com.jmtad.jftt.http.bean.node.Popup;
import com.jmtad.jftt.manager.PopupManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @description:后台任务用于定时弹出活动对话框
 * @author: luweiming
 * @create: 2018-11-27 14:37
 **/
public class PopupService extends Service {
    public static final String TIMER_ACTION = "com.jmtad.jftt.act";
    public static final String EXTRA_KEY_ACT = "act";
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
        EventBus.getDefault().register(this);
    }

    private void startTimer(Popup act) {
        //每次计时前关闭前一个
        stopTimer();
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                EventBus.getDefault().post(new ShowPopupEvent(act));
            }
        };
        timer.schedule(timerTask, act.getPeriod() * 1000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        PopupManager.getInstance().queryActs();
        App.getContext().setAppListener(new App.OnAppListener() {
            @Override
            public void onFront() {
                if (null != PopupManager.getInstance().getNextAct()) {
                    loadAct(PopupManager.getInstance().getNextAct());
                }
            }

            @Override
            public void onBack() {
                stopTimer();
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Subscribe
    public void loadNextAct(PopupEvent event) {
        loadAct(event.getAct());
    }

    private void loadAct(Popup act) {
        startTimer(act);
    }

    private void stopTimer() {
        if (null != timerTask) {
            timerTask.cancel();
            timerTask = null;
        }
        if (null != timer) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
