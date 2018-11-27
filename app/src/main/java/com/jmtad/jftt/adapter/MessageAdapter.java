package com.jmtad.jftt.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jmtad.jftt.R;
import com.jmtad.jftt.http.bean.node.Message;

import java.util.List;

/**
 * @description:我的消息页面列表适配器
 * @author: luweiming
 * @create: 2018-10-19 18:36
 **/
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyHolder> {
    private Context context;
    private List<Message> messages;

    public MessageAdapter(Context context, List<Message> messageList) {
        this.context = context;
        this.messages = messageList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message_layout, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Message message = messages.get(position);
        if (null != message.getTitle()) {
            holder.tvTitle.setText(message.getTitle());
        }
        if (null != message.getDetail()) {
            holder.tvContent.setText(message.getDetail());
        }
        //CreateTime 形如 2018-10-24 17:31:21  显示形式 10-24 17:31

        if (null != message.getCreateTime()) {
            String createTime = message.getCreateTime();

            holder.tvDate.setText(createTime.substring(5, createTime.length() - 3));
        }
    }

    @Override
    public int getItemCount() {
        return null == messages ? 0 : messages.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvDate;
        TextView tvContent;

        public MyHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_item_message_title);
            tvDate = itemView.findViewById(R.id.tv_item_message_date);
            tvContent = itemView.findViewById(R.id.tv_item_message_content);
        }
    }
}
