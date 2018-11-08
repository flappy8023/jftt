package com.jmtad.jftt.util;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-10-17 14:37
 **/
public class DisplayUtils {
    public static int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    public static int pxToDp(float px) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, px, Resources.getSystem().getDisplayMetrics());
    }
}
