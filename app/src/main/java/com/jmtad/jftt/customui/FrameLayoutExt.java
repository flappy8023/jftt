package com.jmtad.jftt.customui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-10-16 18:08
 **/
public class FrameLayoutExt extends RelativeLayout {
    private float downX, downY;//按下时的坐标
    public SlideListener mSlideListener;
    private Context context;

    public void setSlideListener(SlideListener listener) {
        mSlideListener = listener;
    }

    public FrameLayoutExt(Context context) {
        super(context);
        this.context = context;
    }

    public FrameLayoutExt(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public FrameLayoutExt(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            float x = ev.getX();
            float y = ev.getY();
            downX = x;
            downY = y;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            float x = ev.getX();
            float y = ev.getY();
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = x;
                downY = y;
                break;
            case MotionEvent.ACTION_UP:
                float dx = x - downX;
                float dy = y - downY;
                double minSlop = ViewConfiguration.get(context).getScaledTouchSlop() * 0.7;
                if (Math.abs(dx) > minSlop && Math.abs(dy) > minSlop) {
                    //先判断是否可以滑动,在处理滑动
                    if (mSlideListener.canSlide()) {
                        if (Math.abs(dx) > Math.abs(dy)) {
                            if (dx > 0) {
                                mSlideListener.slideRight();
                            } else {
                                mSlideListener.slideLeft();
                            }
                        } else {
                            if (dy > 0) {
                                mSlideListener.slideDown();
                            } else {
                                mSlideListener.slideUp();
                            }
                        }
                    }
                } else {
                    mSlideListener.onClick();
                }
        }
        return false;
    }

    public interface SlideListener {
        void slideDown();

        void slideUp();

        void slideLeft();

        void slideRight();

        /**
         * 是否可以滑动
         */
        boolean canSlide();

        void onClick();

        /**
         * 滑动结束
         */
        void onSlideDone();
    }
}
