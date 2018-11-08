package com.jmtad.jftt.customui;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jmtad.jftt.R;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-10-23 11:19
 **/
public class SharePopwindow extends PopupWindow {
    private Context mContext;
    private View.OnClickListener listener;

    public SharePopwindow(Context context, View.OnClickListener listener) {
        super(context);
        this.mContext = context;
        this.listener = listener;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.share_popwindow_layout, null);
        LinearLayout session = view.findViewById(R.id.wechat_share_session);
        LinearLayout timeline = view.findViewById(R.id.wechat_share_timeline);
        TextView tvCancel = view.findViewById(R.id.share_cancle);
        tvCancel.setOnClickListener(view1 -> dismiss());
        session.setOnClickListener(listener);
        timeline.setOnClickListener(listener);

        //设置SelectPicPopupWindow的View
        this.setContentView(view);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置PopupWindow可触摸
        this.setTouchable(true);
        //设置非PopupWindow区域是否可触摸
        this.setOutsideTouchable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
//    this.setAnimationStyle(R.style.select_anim);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        backgroundAlpha(mContext, 0.5f);//0.0-1.0
        this.setOnDismissListener(() -> {
            backgroundAlpha(mContext, 1f);
        });
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(Context context, float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ((Activity) context).getWindow().setAttributes(lp);
    }
}
