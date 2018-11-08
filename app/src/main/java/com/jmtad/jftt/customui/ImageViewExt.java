package com.jmtad.jftt.customui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-11-07 11:29
 **/
public class ImageViewExt extends AppCompatImageView {
    private float lastX, lastY;
    private int mTouchSlop;

    public ImageViewExt(Context context) {
        super(context);
    }

    public ImageViewExt(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = (int) (ViewConfiguration.get(context).getScaledTouchSlop() * 1.5);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getX();
                lastY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float deltX = event.getX() - lastX;
                float deltY = event.getY() - lastY;
                if (Math.abs(deltX) > mTouchSlop || Math.abs(deltY) > mTouchSlop) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                    return false;
                }
                break;

        }
        return super.onTouchEvent(event);
    }
}
