package com.jmtad.jftt.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jmtad.jftt.R;

/**
 * @description:个人中心卡片列表适配器
 * @author: luweiming
 * @create: 2018-10-17 14:06
 **/
public class CardAdapter extends PagerAdapter {
    Context context;
    private boolean hasCard = false;
    private ClickBindCallback bindCallback;

    public CardAdapter(Context context) {
        this.context = context;
    }

    private String cardBalance;
    private String cardNo;

    @Override
    public int getCount() {
        return 1;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mine_card, null);
        RelativeLayout bindCard = view.findViewById(R.id.rl_card_no_bind);
        if (hasCard) {
            bindCard.setVisibility(View.GONE);
            TextView tvBalance = view.findViewById(R.id.tv_card_balance);
            TextView tvCardNo = view.findViewById(R.id.card_no);
            if (!TextUtils.isEmpty(cardNo)) {
                tvCardNo.setText(cardNo);
            }
            if (!TextUtils.isEmpty(cardBalance)) {
                tvBalance.setText(cardBalance);
            }
        } else {
            bindCard.setVisibility(View.VISIBLE);
            TextView tvBind = view.findViewById(R.id.tv_card_bind_now);
            tvBind.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != bindCallback) {
                        bindCallback.goBind();
                    }
                }
            });
        }


        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    public void setNoCard() {
        hasCard = false;
        notifyDataSetChanged();
    }

    public void showCard(String cardNo) {
        this.cardNo = cardNo;
        hasCard = true;
        notifyDataSetChanged();
    }

    public boolean isHasCard() {
        return hasCard;
    }

    public void setBalance(String balance) {
        this.cardBalance = balance;
        notifyDataSetChanged();
    }

    public void setClickBindCallBack(ClickBindCallback callBack) {
        this.bindCallback = callBack;
    }

    public interface ClickBindCallback {
        void goBind();
    }
}
