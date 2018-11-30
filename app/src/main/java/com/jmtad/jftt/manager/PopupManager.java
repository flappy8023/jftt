package com.jmtad.jftt.manager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.jmtad.jftt.R;
import com.jmtad.jftt.customui.dialog.CommonDialog;
import com.jmtad.jftt.event.PopupEvent;
import com.jmtad.jftt.http.HttpApi;
import com.jmtad.jftt.http.RxCallBack;
import com.jmtad.jftt.http.bean.node.Popup;
import com.jmtad.jftt.http.bean.response.BaseResponse;
import com.jmtad.jftt.http.bean.response.QueryPopupResp;
import com.jmtad.jftt.module.banner.BannerLinkActivity;
import com.jmtad.jftt.util.CollectionUtil;
import com.jmtad.jftt.util.SharedPreferenceUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @description:活动管理类
 * @author: luweiming
 * @create: 2018-11-29 14:12
 **/
public class PopupManager {
    private static final String TAG = "PopupManager";
    private static PopupManager manager = new PopupManager();
    private int index = 0;
    /**
     * 所有未展示的活动列表
     */
    private List<Popup> actList = new ArrayList<>();
    /**
     * 即将要展示的活动
     */
    private Popup nextAct = null;

    private PopupManager() {
    }

    public static PopupManager getInstance() {
        return manager;
    }

    public void queryActs() {
        HttpApi.getInstance().service.queryPopupsByUserId(SharedPreferenceUtil.getInstance().getUserId()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new RxCallBack<QueryPopupResp>() {
            @Override
            public void onSuccess(QueryPopupResp queryPopupResp) {
                if (TextUtils.equals(queryPopupResp.getCode(), BaseResponse.CODE_0) && !CollectionUtil.isEmpty(queryPopupResp.getPopups())) {
                    actList = handlePopups(queryPopupResp.getPopups());
                    nextAct = actList.get(index);
                    EventBus.getDefault().post(new PopupEvent(nextAct));
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    /**
     * 计算每个弹窗遇上一个的时间间隔，方便后续定时任务
     *
     * @param popups
     * @return
     */
    private List<Popup> handlePopups(List<Popup> popups) {
        //第一个弹窗的间隔就是配置的延时时间
        popups.get(0).setPeriod(popups.get(0).getDelay());
        for (int i = 1; i < popups.size(); i++) {
            Popup prePop = popups.get(i - 1);
            Popup popup = popups.get(i);
            popup.setPeriod(popup.getDelay() - prePop.getDelay());
        }
        return popups;
    }

    /**
     * 保存弹窗记录，下次返回未展示的弹窗列表
     *
     * @param popId
     */
    public void saveRecord(String popId) {
        HttpApi.getInstance().service.addPopupRecord(SharedPreferenceUtil.getInstance().getUserId(), popId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new RxCallBack<BaseResponse>() {
            @Override
            public void onSuccess(BaseResponse baseResponse) {
                if (TextUtils.equals(baseResponse.getCode(), BaseResponse.CODE_0)) {
                    Log.i(TAG, "save popup record suc");
                }
            }

            @Override
            public void onFail(Throwable e) {
                Log.e(TAG, "save popup record fail ," + e);
            }
        });
    }

    public void showPopup(Context context, Popup act) {
        CommonDialog dialog = new CommonDialog(context, R.style.BaseDialog, "").setTitle(act.getTitle()).setListener(new CommonDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    Intent intent1 = new Intent(context, BannerLinkActivity.class);
                    intent1.putExtra(BannerLinkActivity.KEY_LINK_URL, act.getLinkUrl());
                    intent1.putExtra(BannerLinkActivity.KEY_TITLE, act.getTitle());
                    context.startActivity(intent1);
                }
                dialog.dismiss();
            }
        });
        //窗口关闭后开始准备下一个弹窗
        dialog.setOnDismissListener(dialogInterface -> PopupManager.getInstance().next());
        //获取指定的弹出动画
        int styleId = getStyle(act);
        if (styleId != 0) {
            dialog.getWindow().setWindowAnimations(styleId);
        }
        dialog.show();
    }

    private int getStyle(Popup act) {
        switch (act.getAnimaType()) {

            case 1:
                return R.style.DialogTopOutAndInStyle;
            case 2:
                return R.style.DialogBottomOutAndInStyle;
            case 3:
                return R.style.DialogLeftOutAndInStyle;
            case 4:
                return R.style.DialogRightOutAndInStyle;
            case 0:
            default:
                return 0;
        }
    }

    public void next() {
        index++;
        if (index < actList.size()) {
            nextAct = actList.get(index);
        } else {
            nextAct = null;
        }
        if (null != nextAct) {
            EventBus.getDefault().post(new PopupEvent(nextAct));
        }
    }

    public Popup getNextAct() {
        return nextAct;
    }
}
