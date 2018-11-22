package com.jmtad.jftt.customui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-11-06 16:16
 **/
public class MyRecyclerView extends RecyclerView {
    private float mLastX, mLastY;
    private ItemTouchHelper itemTouchHelper;
    /**
     * 移动点的保护范围值
     */
    private int mTouchSlop;

    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = (int) (ViewConfiguration.get(context).getScaledTouchSlop() * 1.5);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return super.onTouchEvent(e);
    }

}
