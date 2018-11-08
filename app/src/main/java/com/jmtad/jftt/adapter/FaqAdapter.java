package com.jmtad.jftt.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jmtad.jftt.R;
import com.jmtad.jftt.http.bean.node.CommonPro;
import com.jmtad.jftt.http.bean.node.FaqDetail;

import java.util.List;

/**
 * @description:常见问题列表适配器
 * @author: flappy8023
 * @create: 2018-10-24 19:22
 **/
public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.MyHolder> {
    private Context context;
    private List<CommonPro> faqDetails;
    private ItemClickListener listener;

    public FaqAdapter(Context context, List<CommonPro> faqs) {
        this.context = context;
        this.faqDetails = faqs;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_faq_layout, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        CommonPro detail = faqDetails.get(position);
        holder.tvTitle.setText(detail.getDescription());
    }

    @Override
    public int getItemCount() {
        return faqDetails == null ? 0 : faqDetails.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;

        public MyHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_faq_title);
        }
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public interface ItemClickListener {
        void onClick(FaqDetail detail);
    }
}
