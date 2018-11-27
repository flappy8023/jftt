package com.jmtad.jftt.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jmtad.jftt.R;
import com.jmtad.jftt.customui.CircleImageView;
import com.jmtad.jftt.http.bean.node.Banner;
import com.jmtad.jftt.util.GlideUtil;

import java.util.List;

/**
 * @description:首页头部收藏和最近浏览列表适配器
 * @author: luweiming
 * @create: 2018-11-06 11:14
 **/
public class HomeHeaderAdapter extends RecyclerView.Adapter<HomeHeaderAdapter.MyHolder> {
    private Context context;
    private List<Banner> banners;
    private HeaderClickListener listener;

    public HomeHeaderAdapter(Context context, List<Banner> bannerList) {
        this.context = context;
        this.banners = bannerList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_header_layout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Banner banner = banners.get(position);
        holder.tvAuthor.setText(banner.getAuthor());
        GlideUtil.loadImage(context, banner.getImgUrl(), holder.img);
        holder.itemView.setOnLongClickListener(view -> {
            if (null != listener) {
                listener.onLongClick(banner);
            }
            return false;
        });
        holder.itemView.setOnClickListener(view -> {
            if (null != listener) {
                listener.onClick(banner);
            }
        });
    }

    @Override
    public int getItemCount() {
        return null == banners ? 0 : banners.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView tvAuthor;

        public MyHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.item_home_header_img);
            tvAuthor = itemView.findViewById(R.id.item_home_header_author);
        }
    }

    public void setListener(HeaderClickListener clickListener) {
        this.listener = clickListener;
    }

    public interface HeaderClickListener {
        void onClick(Banner banner);

        void onLongClick(Banner banner);
    }
}
