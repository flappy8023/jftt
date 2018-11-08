package com.jmtad.jftt.module.mine;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jmtad.jftt.R;
import com.jmtad.jftt.base.BaseActivity;
import com.jmtad.jftt.http.bean.node.FaqDetail;
import com.jmtad.jftt.util.MyToast;
import com.jmtad.jftt.util.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class FAQActivity extends BaseActivity {

    @BindView(R.id.iv_toolbar_left_button)
    ImageView back;
    @BindView(R.id.tv_toolbar_title)
    TextView tvTitle;
    @BindView(R.id.rl_faq_charge_question)
    RelativeLayout chargeFaq;
    @BindView(R.id.rl_faq_account_question)
    RelativeLayout acccountFaq;
    //    @BindView(R.id.rv_faq)
//    RecyclerView recyclerView;
    private List<FaqDetail> detailList = new ArrayList<>();

    //账号问题
    public static final String FAQ_TYPE_ACCOUNT = "1";
    //充值问题
    public static final String FAQ_TYPE_CHARGE = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
    }

    @OnClick(R.id.iv_toolbar_left_button)
    public void setBack() {
        finish();
    }

    @Override
    protected void initView() {
        back.setImageResource(R.drawable.back_black);
        tvTitle.setText(getString(R.string.menu_faq));

    }

    @OnClick(R.id.rl_faq_account_question)
    public void accountFaq() {
        Intent intent = new Intent(this, FAQDetailActivity.class);
        intent.putExtra(FAQDetailActivity.KEY_TYPE, FAQ_TYPE_ACCOUNT);
        startActivity(intent);
    }

    @OnClick(R.id.rl_faq_charge_question)
    public void chargeFaq() {
        Intent intent = new Intent(this, FAQDetailActivity.class);
        intent.putExtra(FAQDetailActivity.KEY_TYPE, FAQ_TYPE_CHARGE);
        startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_faq;
    }

    @OnClick(R.id.bt_faq_contact_custom_service)
    public void contactCustomService() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_custom_service_layout);
        dialog.setCanceledOnTouchOutside(true);
        Button btCopy = dialog.findViewById(R.id.bt_copy);
        btCopy.setOnClickListener(view -> {
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData data = ClipData.newPlainText("copy", "17715231656");
            cm.setPrimaryClip(data);
            dialog.dismiss();
            MyToast.showLongToast(this, getString(R.string.copy_kefu_wx_suc));
        });
        dialog.show();
    }


    @Override
    protected void initPresenter() {

    }
}
