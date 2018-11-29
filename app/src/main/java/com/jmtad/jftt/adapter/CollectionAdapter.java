package com.jmtad.jftt.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jmtad.jftt.R;
import com.jmtad.jftt.http.bean.node.Banner;
import com.jmtad.jftt.manager.BannerManager;
import com.jmtad.jftt.util.GlideUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:收藏页面图文列表适配器
 * @author: luweiming
 * @create: 2018-11-26 11:24
 **/
public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.MyHolder> {
    Context mContext;
    List<Banner> mDatas;
    private boolean isDeleteMode = false;//是否处于选择删除状态下
    private List<Banner> deletingBanners = new ArrayList<>();//待删除列表

    public CollectionAdapter(Context context, List<Banner> bannerList) {
        mContext = context;
        mDatas = bannerList;
    }

    public void setDeleteMode(boolean deleteMode) {
        isDeleteMode = deleteMode;
    }

    public boolean isDeleteMode() {
        return isDeleteMode;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_collections, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Banner banner = mDatas.get(position);
        holder.tvTitle.setText(banner.getTitle());
        holder.tvDes.setText(banner.getSummary());
        GlideUtil.loadImage(mContext, banner.getImgUrl(), holder.ivPoster);
        if (isDeleteMode) {
            holder.cbSelect.setVisibility(View.VISIBLE);
            if (banner.isSelected) {
                holder.cbSelect.setImageResource(R.drawable.select_1);
            } else {
                holder.cbSelect.setImageResource(R.drawable.select_0);
            }
        } else {
            holder.cbSelect.setVisibility(View.GONE);
        }
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //长按item进入删除模式
                if (!isDeleteMode) {
                    isDeleteMode = true;
                    notifyDataSetChanged();
                    banner.isSelected = true;
                    deletingBanners.add(banner);
                    holder.cbSelect.setImageResource(R.drawable.select_1);
                    if (null != itemListener) {
                        itemListener.onDeleteMode();
                    }
                }
                return true;
            }
        });
        holder.itemView.setOnClickListener(view -> {
            //点击item时，如果当前处于删除状态，判断该item还未被选中，将该item添加到待删除列表，已选中则从待删除列表移除；如果未处于删除状态，则直接进入图文详情
            if (isDeleteMode) {
                if (!banner.isSelected) {
                    banner.isSelected = true;
                    deletingBanners.add(banner);
                    holder.cbSelect.setImageResource(R.drawable.select_1);
                } else {
                    banner.isSelected = false;
                    deletingBanners.remove(banner);
                    holder.cbSelect.setImageResource(R.drawable.select_0);
                }
            } else {
                BannerManager.clickBanner(banner, mContext);

            }
        });
    }

    /**
     * 或取待删除的图文列表
     *
     * @return
     */
    public List<Banner> getDeletingBanners() {
        return deletingBanners;
    }

    @Override
    public int getItemCount() {
        return null == mDatas ? 0 : mDatas.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDes;
        ImageView ivPoster;
        ImageView cbSelect;

        public MyHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            tvDes = itemView.findViewById(R.id.tv_item_collection_des);
            tvTitle = itemView.findViewById(R.id.tv_item_collection_title);
            ivPoster = itemView.findViewById(R.id.iv_item_collection_poster);
            cbSelect = itemView.findViewById(R.id.iv_item_collection_select);
        }
    }

    private OnItemListener itemListener;

    public void setItemListener(OnItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public interface OnItemListener {
        void onDeleteMode();
    }
}
