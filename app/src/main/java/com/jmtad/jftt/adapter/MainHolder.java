package com.jmtad.jftt.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jmtad.jftt.R;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-11-12 11:49
 **/
public class MainHolder extends RecyclerView.ViewHolder {
    public ImageView ivPoster;
    public ImageView ivStar;
    public TextView tvAuthor;
    public TextView tvTitle;
    public TextView tvStars;
    public TextView tvViews;
    public LinearLayout likeLayout;
    public ImageView ivShare;

    public MainHolder(View itemView) {
        super(itemView);
        initView(itemView);
    }

    private void initView(View itemView) {
        ivPoster = itemView.findViewById(R.id.iv_home_frag_poster);
        tvAuthor = itemView.findViewById(R.id.tv_home_news_author);
        tvTitle = itemView.findViewById(R.id.tv_home_news_title);
        ivStar = itemView.findViewById(R.id.iv_star);
        tvStars = itemView.findViewById(R.id.tv_home_news_likes);
        tvViews = itemView.findViewById(R.id.tv_home_news_views);
        likeLayout = itemView.findViewById(R.id.ll_home_news_like_container);
        ivShare = itemView.findViewById(R.id.iv_home_news_share);
    }
}
