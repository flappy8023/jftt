package com.jmtad.jftt.customui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import cn.qqtheme.framework.util.LogUtils;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-10-30 20:37
 **/
public class MyFrameLayout extends FrameLayout implements View.OnTouchListener, GestureDetector.OnGestureListener {
    private float mPosX, mPosY, mCurPosX, mCurPosY;
    private static final int FLING_MIN_DISTANCE = 20;// 移动最小距离
    private static final int FLING_MIN_VELOCITY = 200;// 移动最大速度
    //构建手势探测器
    GestureDetector mygesture = new GestureDetector(this);

    public MyFrameLayout(@NonNull Context context) {
        super(context);
    }

    public MyFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setOnTouchListener(this);
        //允许长按
        this.setLongClickable(true);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float v, float v1) {
        if (e1.getY() - e2.getY() > FLING_MIN_DISTANCE) {
//                     && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
            LogUtils.debug("down");
        }
        //向上
        if (e2.getY() - e1.getY() > FLING_MIN_DISTANCE
                && Math.abs(v1) > FLING_MIN_VELOCITY) {
            LogUtils.debug("up");
        }
        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return mygesture.onTouchEvent(motionEvent);
    }
}
