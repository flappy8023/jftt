package com.jmtad.jftt.customui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

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
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = new LinearLayout(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.share_popwindow_layout, linearLayout);
        LinearLayout session = view.findViewById(R.id.wechat_share_session);
        LinearLayout timeline = view.findViewById(R.id.wechat_share_timeline);
        ImageView tvCancel = view.findViewById(R.id.share_cancel);
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
        Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.mine_menu_content_bg);
        Drawable drawable = new BitmapDrawable(mContext.getResources(), bmp);
// 不带参的方法已经deprecated
        this.setBackgroundDrawable(drawable);
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
