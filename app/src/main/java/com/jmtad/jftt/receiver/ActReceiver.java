package com.jmtad.jftt.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jmtad.jftt.R;
import com.jmtad.jftt.customui.dialog.CommonDialog;

/**
 * @description:活动广播接收器，用于弹出活动对话框
 * @author: luweiming
 * @create: 2018-11-27 14:58
 **/
public class ActReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        CommonDialog dialog = new CommonDialog(context, R.style.BaseDialog, "活动");
        dialog.getWindow().setWindowAnimations(R.style.DialogBottomOutAndInStyle);
        dialog.show();
    }
}
