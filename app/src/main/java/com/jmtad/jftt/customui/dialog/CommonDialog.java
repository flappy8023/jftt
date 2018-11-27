package com.jmtad.jftt.customui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jmtad.jftt.R;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-11-12 10:12
 **/
public class CommonDialog extends Dialog implements View.OnClickListener {
    private TextView contentTxt;
    private TextView titleTxt;
    private TextView submitTxt;
    private TextView cancelTxt;
    private int cancelBgId = -1;
    private int submitBgId = -1;
    private int cancelTextColor = -1;
    private int submitTextColor = -1;
    private Context mContext;
    private String content;
    private OnCloseListener listener;
    private String positiveName;
    private String negativeName;
    private String title;
    private boolean showCancelTv = false;

    public CommonDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public CommonDialog(Context context, int themeResId, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
    }

    public CommonDialog(Context context, int themeResId, String content, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }

    protected CommonDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public CommonDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public CommonDialog setContent(String content) {
        this.content = content;
        return this;
    }

    public CommonDialog setPositiveButton(String name) {
        this.positiveName = name;
        return this;
    }

    public CommonDialog setListener(OnCloseListener listener) {
        this.listener = listener;
        return this;
    }

    public CommonDialog setNegativeButton(String name) {
        this.negativeName = name;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common);
        setCanceledOnTouchOutside(true);
        initView();
    }

    private void initView() {
        contentTxt = (TextView) findViewById(R.id.content);
        titleTxt = (TextView) findViewById(R.id.title);
        submitTxt = (TextView) findViewById(R.id.submit);
        cancelTxt = findViewById(R.id.cancel);
        submitTxt.setOnClickListener(this);
        cancelTxt.setOnClickListener(this);
        if (showCancelTv) {
            cancelTxt.setVisibility(View.VISIBLE);
        }
        contentTxt.setText(content);
        if (!TextUtils.isEmpty(positiveName)) {
            submitTxt.setText(positiveName);
        }

        if (!TextUtils.isEmpty(negativeName)) {
            cancelTxt.setText(negativeName);
        }

        if (!TextUtils.isEmpty(title)) {
            titleTxt.setText(title);
        }
        if (cancelBgId != -1) {
            cancelTxt.setBackground(mContext.getResources().getDrawable(cancelBgId));
        }
        if (submitBgId != -1) {
            submitTxt.setBackground(mContext.getResources().getDrawable(submitBgId));

        }
        if (cancelTextColor != -1) {
            cancelTxt.setTextColor(mContext.getResources().getColor(cancelTextColor));

        }
        if (submitTextColor != -1) {
            submitTxt.setTextColor(mContext.getResources().getColor(submitTextColor));

        }
    }

    /**
     * 设置按钮的背景样式
     *
     * @param cancelBgId
     * @param submitBgId
     */
    public CommonDialog setButtonBgId(int cancelBgId, int submitBgId) {
        this.cancelBgId = cancelBgId;
        this.submitBgId = submitBgId;
        return this;
    }

    /**
     * 设置按钮字体颜色
     *
     * @param cancelColor
     * @param submitColor
     */
    public CommonDialog setButtonTextColorId(int cancelColor, int submitColor) {
        this.cancelTextColor = cancelColor;
        this.submitTextColor = submitColor;
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                if (listener != null) {
                    listener.onClick(this, false);
                }
                this.dismiss();
                break;
            case R.id.submit:
                if (listener != null) {
                    listener.onClick(this, true);
                }
                break;
        }
    }

    public CommonDialog setCancelBtVisible() {
        showCancelTv = true;
        return this;
    }

    public interface OnCloseListener {
        void onClick(Dialog dialog, boolean confirm);
    }
}