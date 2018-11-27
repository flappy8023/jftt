package com.jmtad.jftt.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.jmtad.jftt.R;
import com.jmtad.jftt.http.bean.node.Banner;
import com.jmtad.jftt.util.CollectionUtil;
import com.jmtad.jftt.util.GlideUtil;

import java.util.List;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-11-05 09:26
 **/
public class MainAdapter extends RecyclerView.Adapter<MainHolder> {
    private Context context;
    private List<Banner> banners;
    private HomeListener homeListener;
    private ItemTouchHelper itemTouchHelper;
    private int touchSlop;

    public MainAdapter(Context context, List<Banner> bannerList) {
        this.context = context;
        this.banners = bannerList;
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void setItemTouchHelper(ItemTouchHelper itemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper;

    }

    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_layout, parent, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainHolder holder, int position, @NonNull List<Object> payloads) {
        if (!CollectionUtil.isEmpty(payloads)) {
            Banner banner = banners.get(position);
            //只刷新收藏数和浏览量
            if (TextUtils.equals(Banner.CollectStatus.COLLECTED, banner.getStarStatus())) {
                holder.ivStar.setColorFilter(context.getResources().getColor(R.color.red_1));
            } else {
                holder.ivStar.setColorFilter(context.getResources().getColor(R.color.white));
            }
            holder.tvStars.setText(banner.getStars() + "");
            holder.tvViews.setText(String.format(context.getString(R.string.home_news_view_format), banner.getViews()));
        } else {
            onBindViewHolder(holder, position);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MainHolder holder, int position) {
        Banner banner = banners.get(position);
        if (!TextUtils.isEmpty(banner.getImgUrl())) {
            GlideUtil.loadImage(context, banner.getImgUrl(), holder.ivPoster);
        } else {
            holder.ivPoster.setImageDrawable(null);
        }
        if (!TextUtils.isEmpty(banner.getAuthor())) {
            holder.tvAuthor.setText(banner.getAuthor());
        } else {
            holder.tvAuthor.setText("");
        }
        if (!TextUtils.isEmpty(banner.getTitle())) {
            holder.tvTitle.setText(banner.getTitle());
        } else {
            holder.tvTitle.setText("");
        }
        //设置收藏状态图标
        if (TextUtils.equals(Banner.CollectStatus.COLLECTED, banner.getStarStatus())) {
            holder.ivStar.setColorFilter(context.getResources().getColor(R.color.red_1));
        } else {
            holder.ivStar.setColorFilter(context.getResources().getColor(R.color.white));
        }
        holder.tvStars.setText(banner.getStars() + "");
        holder.tvViews.setText(String.format(context.getString(R.string.home_news_view_format), banner.getViews()));
        holder.ivShare.setOnClickListener(view -> {
            if (null != homeListener) {
                homeListener.share(banner);
            }
        });
        holder.ivPoster.getParent().getParent().requestDisallowInterceptTouchEvent(false);
        holder.likeLayout.setOnClickListener(view -> {
            if (null != homeListener) {
                homeListener.starOrUnstar(banner, view);
            }
        });
        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            private float lastX, lastY;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                holder.itemView.getParent().getParent().requestDisallowInterceptTouchEvent(false);
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    lastX = event.getX();
                    lastY = event.getY();
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    float deltX = event.getX() - lastX;
                    float deltY = event.getY() - lastY;
                    //排除点击事件
                    if (Math.abs(deltX) > touchSlop || Math.abs(deltY) > touchSlop) {
                        itemTouchHelper.startSwipe(holder);
                    }
                }
                //为了解决华东和点击事件的冲突,在onTouch
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    float deltX = event.getX() - lastX;
                    float deltY = event.getY() - lastY;
                    if (Math.abs(deltX) < touchSlop && Math.abs(deltY) < touchSlop) {
                        homeListener.toDetail(banner);
                    }
                }
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return null == banners ? 0 : banners.size();
    }


    public void setHomeListener(HomeListener listener) {
        homeListener = listener;
    }

    public interface HomeListener {
        void share(Banner banner);

        void starOrUnstar(Banner banner, View view);

        void toDetail(Banner banner);
    }
}
