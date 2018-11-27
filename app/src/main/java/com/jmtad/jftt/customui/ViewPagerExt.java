package com.jmtad.jftt.customui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-10-11 17:40
 **/
public class ViewPagerExt extends ViewPager {
    private float downX, downY;//按下时的坐标
    private Context context;
    private boolean isSlide = true;

    public ViewPagerExt(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public ViewPagerExt(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    /**
     * 设置是否可以滑动
     *
     * @param isSlide true:可以滑动，falase:不可滑动
     */
    public void setSlide(boolean isSlide) {
        this.isSlide = isSlide;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //禁止滑动
        if (!isSlide) {
            return false;
        }
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

}
