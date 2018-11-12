package com.jmtad.jftt.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jmtad.jftt.R;

/**
 * @description:引导页viewpager适配器
 * @author: flappy8023
 * @create: 2018-10-22 09:37
 **/
public class WelcomeAdapter extends PagerAdapter {
    private Context context;
    private ClickListener listener;

    public WelcomeAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(params);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        switch (position) {
            case 0:
                imageView.setImageResource(R.drawable.welcome_1);
                break;
            case 1:
                imageView.setImageResource(R.drawable.welcome_2);
                break;
            case 2:
                imageView.setImageResource(R.drawable.welcome_3);
                break;
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(position);
            }
        });
        container.addView(imageView);
        return imageView;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    }

    public void setClickListener(ClickListener clickListener) {
        this.listener = clickListener;
    }

    public interface ClickListener {
        void onClick(int position);
    }
}
