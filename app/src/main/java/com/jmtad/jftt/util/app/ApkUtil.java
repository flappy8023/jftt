package com.jmtad.jftt.util.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-10-26 10:30
 **/
public class ApkUtil {
    /**
     * 获取apk自身版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
        return packageInfo.versionCode;
    }

    /**
     * 获取当前设备型号
     *
     * @return
     */
    public static String getDevice() {
        return Build.DEVICE;
    }
}
