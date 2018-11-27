package com.jmtad.jftt.util;

import android.content.Context;
import android.widget.Toast;

/**
 * @description: 对Toast简单封装
 * @author: luweiming
 * @create: 2018-10-15 11:53
 **/
public class MyToast {
    public static Toast mToast = null;

    public static void showLongToast(Context context, String msg) {
//        if (null == mToast) {
        mToast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
//        } else {
//            mToast.setText(msg);
//        }
        mToast.show();
    }

    public static void showShortToast(Context context, String msg) {
//        if (null == mToast) {
        mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
//        } else {
//            mToast.setText(msg);
//        }
        mToast.show();
    }
}
