package com.jmtad.jftt.customui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.jmtad.jftt.App;
import com.jmtad.jftt.R;
import com.jmtad.jftt.http.bean.node.Popup;
import com.jmtad.jftt.manager.PopupManager;
import com.jmtad.jftt.module.banner.ActDetailActivity;
import com.jmtad.jftt.util.GlideUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-11-30 15:18
 **/
public class ActDialog extends Dialog {
    private Context mContext;
    private ImageView ivImg;
    private Popup act;
    private Timer timer;
    private TimerTask timerTask;

    public ActDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    public ActDialog(@NonNull Context context, int themeResId, Popup act) {
        super(context, themeResId);
        mContext = context;
        this.act = act;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_act_layout);
        setCanceledOnTouchOutside(true);
        initView();
    }

    @Override
    public void show() {
        super.show();
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                //仅应用在前台的时候倒计时结束关闭弹窗，开始下一个活动的计时
                if (!App.getContext().isOnBack()) {
                    dismiss();
                } else {
                    PopupManager.getInstance().setNeedClose(true);
                }
            }
        };
        timer.schedule(timerTask, 10 * 1000);
    }

    private void initView() {
        ivImg = findViewById(R.id.iv_img);
        GlideUtil.loadImage(mContext, act.getImgUrl(), ivImg);
        ivImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ActDetailActivity.class);
                intent.putExtra(ActDetailActivity.KEY_TITLE, act.getTitle());
                intent.putExtra(ActDetailActivity.KEY_LINK_URL, act.getLinkUrl());
                mContext.startActivity(intent);
                //点击弹窗内容不再继续计时（加载下一条的时机只有在对话框关闭的时候）
                if (null != timerTask) {
                    timerTask.cancel();
                    timerTask = null;
                }
                if (null != timer) {
                    timer.cancel();
                    timer = null;
                }
            }
        });
    }
}
