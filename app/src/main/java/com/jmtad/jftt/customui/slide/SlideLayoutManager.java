package com.jmtad.jftt.customui.slide;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.jmtad.jftt.module.banner.BannerDetailActivity;


public class SlideLayoutManager extends RecyclerView.LayoutManager {


    private RecyclerView mRecyclerView;
    private ItemTouchHelper mItemTouchHelper;
    private Context context;

    public SlideLayoutManager(@NonNull RecyclerView recyclerView, @NonNull ItemTouchHelper itemTouchHelper, Context context) {
        this.mRecyclerView = checkIsNull(recyclerView);
        this.mItemTouchHelper = checkIsNull(itemTouchHelper);
        this.context = context;
    }

    private <T> T checkIsNull(T t) {
        if (t == null) {
            throw new NullPointerException();
        }
        return t;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }


    @Override
    public void onLayoutChildren(final RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        int itemCount = getItemCount();
        //预加载一个
        if (itemCount > ItemConfig.DEFAULT_SHOW_ITEM) {
            for (int position = ItemConfig.DEFAULT_SHOW_ITEM; position >= 0; position--) {
                final View view = recycler.getViewForPosition(position);
                addView(view);
                measureChildWithMargins(view, 0, 0);
                layoutDecoratedWithMargins(view, 0, 0,
                        getDecoratedMeasuredWidth(view),
                        getDecoratedMeasuredHeight(view));
            }
            mRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRecyclerView.setVisibility(View.VISIBLE);
                }
            }, 120);
        } else {
            for (int position = itemCount - 1; position >= 0; position--) {
                final View view = recycler.getViewForPosition(position);
                addView(view);
                measureChildWithMargins(view, 0, 0);
                layoutDecoratedWithMargins(view, 0, 0,
                        getDecoratedMeasuredWidth(view),
                        getDecoratedMeasuredHeight(view));
            }
        }
    }

    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {
        private float lastX, lastY;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder(v);
            v.getParent().getParent().requestDisallowInterceptTouchEvent(false);
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                lastX = event.getX();
                lastY = event.getY();
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                mItemTouchHelper.startSwipe(childViewHolder);
            }
            //为了解决滑动和点击事件的冲突,在onTouch
            if (event.getAction() == MotionEvent.ACTION_UP) {
                float deltX = event.getX() - lastX;
                float deltY = event.getY() - lastY;
                if (Math.abs(deltX) < 20 && Math.abs(deltY) < 20) {
                    context.startActivity(new Intent(context, BannerDetailActivity.class));
                }
            }
            return true;
        }
    };

}
