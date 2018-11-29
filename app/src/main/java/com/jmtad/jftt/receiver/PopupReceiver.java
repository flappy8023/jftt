package com.jmtad.jftt.receiver;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jmtad.jftt.R;
import com.jmtad.jftt.customui.dialog.CommonDialog;
import com.jmtad.jftt.http.bean.node.Popup;
import com.jmtad.jftt.manager.PopupManager;
import com.jmtad.jftt.service.PopupService;

/**
 * @description:活动广播接收器，用于弹出活动对话框
 * @author: luweiming
 * @create: 2018-11-27 14:58
 **/
public class PopupReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Popup act = (Popup) intent.getSerializableExtra(PopupService.EXTRA_KEY_ACT);
        CommonDialog dialog = new CommonDialog(context, R.style.BaseDialog, "活动").setTitle(act.getTitle()).setListener(new CommonDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                dialog.dismiss();
            }
        });
        //窗口关闭后开始准备下一个弹窗
        dialog.setOnDismissListener(dialogInterface -> PopupManager.getInstance().next());
        dialog.getWindow().setWindowAnimations(R.style.DialogBottomOutAndInStyle);
        dialog.show();
        //弹窗展示后立马上报
        PopupManager.getInstance().saveRecord(act.getId());
    }
}
