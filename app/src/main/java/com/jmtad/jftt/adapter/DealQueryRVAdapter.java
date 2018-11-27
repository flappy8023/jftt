package com.jmtad.jftt.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jmtad.jftt.R;
import com.jmtad.jftt.http.bean.node.OilRecord;
import com.jmtad.jftt.module.mine.RecordFragment;
import com.jmtad.jftt.util.timeutil.DateCalendarUtils;

import java.util.List;

/**
 * @description:交易查询页面条目列表适配器
 * @author: luweiming
 * @create: 2018-10-22 11:44
 **/
public class DealQueryRVAdapter extends RecyclerView.Adapter<DealQueryRVAdapter.MyHolder> {
    private Context context;
    private List<OilRecord> records;

    public DealQueryRVAdapter(Context context, List<OilRecord> records) {
        this.context = context;
        this.records = records;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_deal_query, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        OilRecord record = records.get(position);
        String time = DateCalendarUtils.formatDate(Long.valueOf(record.getOperationTime()), "yyyy-MM-dd HH:mm");
        holder.tvDate.setText(time);
        String type = record.getType();
        //收入和支出展示不同的样式
        SpannableString oilNum = null;
        if (TextUtils.equals(RecordFragment.TYPE_INCOME, type)) {
            oilNum = new SpannableString("+ " + record.getOilNum());
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.color_income));
            oilNum.setSpan(colorSpan, 0, oilNum.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        } else if (TextUtils.equals(RecordFragment.TYPE_EXPEND, type)) {
            oilNum = new SpannableString("- " + record.getOilNum());
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.color_expend));
            oilNum.setSpan(colorSpan, 0, oilNum.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        } else {
            oilNum = new SpannableString(record.getOilNum());
        }
        holder.tvDetail.setText(oilNum);
        holder.tvDes.setText(record.getFromName());
    }

    @Override
    public int getItemCount() {
        return records == null ? 0 : records.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvDes, tvDetail;

        public MyHolder(View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tv_item_deal_query_date);
            tvDes = itemView.findViewById(R.id.tv_item_deal_query_des);
            tvDetail = itemView.findViewById(R.id.tv_item_deal_query_detail);
        }
    }
}
