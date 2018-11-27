package com.jmtad.jftt.customui.slide;

import android.support.v7.widget.RecyclerView;

import java.util.List;



public interface OnSlideListener<T> {

    void onSliding(RecyclerView.ViewHolder viewHolder, float ratio, int direction);

    void onSlided(RecyclerView.ViewHolder viewHolder, T t, int direction, int position);

    void onClear(List<T> temp);

}
