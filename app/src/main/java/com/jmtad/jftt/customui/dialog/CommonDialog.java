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

    private Context mContext;
    private String content;
    private OnCloseListener listener;
    private String positiveName;
    private String negativeName;
    private String title;

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
        submitTxt.setOnClickListener(this);
//        cancelTxt.setOnClickListener(this);

        contentTxt.setText(content);
        if (!TextUtils.isEmpty(positiveName)) {
            submitTxt.setText(positiveName);
        }
//
//        if(!TextUtils.isEmpty(negativeName)){
//            cancelTxt.setText(negativeName);
//        }

        if (!TextUtils.isEmpty(title)) {
            titleTxt.setText(title);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.cancel:
//                if(listener != null){
//                    listener.onClick(this, false);
//                }
//                this.dismiss();
//                break;
            case R.id.submit:
                if (listener != null) {
                    listener.onClick(this, true);
                }
                break;
        }
    }

    public interface OnCloseListener {
        void onClick(Dialog dialog, boolean confirm);
    }
}