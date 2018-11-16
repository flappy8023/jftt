package com.jmtad.jftt.customui.pullextend;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jmtad.jftt.R;
import com.jmtad.jftt.adapter.HomeHeaderAdapter;
import com.jmtad.jftt.http.bean.node.Banner;
import com.jmtad.jftt.util.DisplayUtils;

import java.util.List;


/**
 * 这个类封装了下拉刷新的布局
 */
public class ExtendListHeader extends ExtendLayout {


    float containerHeight = DisplayUtils.dpToPx(60);
    float listHeight = DisplayUtils.dpToPx(120);
    boolean arrivedListHeight = false;
    //收藏列表
    private RecyclerView mRvCollects;
    //最近浏览列表
    private RecyclerView mRvRecent;
    private LinearLayout llNodata;
    private LinearLayout llContent;
    private LinearLayout llRecent;
    private LinearLayout llCollects;
    private HomeHeaderAdapter recentAdapter;
    private HomeHeaderAdapter collectsAdapter;

    /**
     * 原点
     */

    private ExpendPoint mExpendPoint;

    /**
     * 构造方法
     *
     * @param context context
     */
    public ExtendListHeader(Context context) {
        super(context);

    }

    public void setListHeight(float height) {
        this.listHeight = height;
    }

    /**
     * 构造方法
     *
     * @param context context
     * @param attrs   attrs
     */
    public ExtendListHeader(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void bindView(View container) {
        mRvCollects = findViewById(R.id.list_collect);
        mRvRecent = findViewById(R.id.list_recent);
        mExpendPoint = findViewById(R.id.expend_point);
        llNodata = findViewById(R.id.ll_home_header_nodata);
        llContent = findViewById(R.id.ll_home_header_content);
        llRecent = findViewById(R.id.ll_header_recent);
        llCollects = findViewById(R.id.ll_header_collect);
    }


    @Override
    protected View createLoadingView(Context context, AttributeSet attrs, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.extend_header, viewGroup, false);
    }

    public void showRecent(List<Banner> bannerList) {
        if (VISIBLE == llNodata.getVisibility()) {
            llNodata.setVisibility(GONE);
        }
        llRecent.setVisibility(VISIBLE);
        recentAdapter = new HomeHeaderAdapter(getContext(), bannerList);
        mRvRecent.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRvRecent.setAdapter(recentAdapter);

    }

    public void showCollects(List<Banner> bannerList) {
        if (VISIBLE == llNodata.getVisibility()) {
            llNodata.setVisibility(GONE);
        }
        llCollects.setVisibility(VISIBLE);
        collectsAdapter = new HomeHeaderAdapter(getContext(), bannerList);
        mRvCollects.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRvCollects.setAdapter(collectsAdapter);
    }

    @Override
    public int getContentSize() {
        return (int) (containerHeight);
    }

    @Override
    public int getListSize() {
        return (int) (listHeight);
    }


    @Override
    protected void onReset() {
        mExpendPoint.setVisibility(VISIBLE);
        mExpendPoint.setAlpha(1);
        mExpendPoint.setTranslationY(0);
        this.setTranslationY(0);
        arrivedListHeight = false;
    }

    @Override
    protected void onReleaseToRefresh() {
    }

    @Override
    protected void onPullToRefresh() {

    }

    @Override
    protected void onArrivedListHeight() {
        arrivedListHeight = true;
    }

    @Override
    protected void onRefreshing() {
    }

    @Override
    public void onPull(int offset) {
        if (!arrivedListHeight) {
            mExpendPoint.setVisibility(VISIBLE);
            float percent = Math.abs(offset) / containerHeight;
            int moreOffset = Math.abs(offset) - (int) containerHeight;
            if (percent <= 1.0f) {
                mExpendPoint.setPercent(percent);
                mExpendPoint.setTranslationY(-Math.abs(offset) / 2 + mExpendPoint.getHeight() / 2);
                this.setTranslationY(-containerHeight);
            } else {
                float subPercent = (moreOffset) / (listHeight - containerHeight);
                subPercent = Math.min(1.0f, subPercent);
                mExpendPoint.setTranslationY(-(int) containerHeight / 2 + mExpendPoint.getHeight() / 2 + (int) containerHeight * subPercent / 2);
                mExpendPoint.setPercent(1.0f);
                float alpha = (1 - subPercent * 2);
                mExpendPoint.setAlpha(Math.max(alpha, 0));
                this.setTranslationY(-(1 - subPercent) * containerHeight);
            }
        }
        if (Math.abs(offset) >= listHeight) {
            mExpendPoint.setVisibility(INVISIBLE);
            this.setTranslationY(-(Math.abs(offset) - listHeight) / 2);
        }
    }


    public void showHint() {
        llContent.setVisibility(INVISIBLE);
        llNodata.setVisibility(VISIBLE);
    }
}
