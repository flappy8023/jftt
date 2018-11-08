package com.jmtad.jftt.module.mine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jmtad.jftt.R;
import com.jmtad.jftt.base.BaseActivity;
import com.jmtad.jftt.module.mine.contract.MineContract;
import com.jmtad.jftt.module.mine.presenter.MinePresenter;
import com.jmtad.jftt.util.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-10-25 15:47
 **/
public class BindCardActivity extends BaseActivity<MinePresenter> implements MineContract.IMineView {
    @BindView(R.id.iv_toolbar_left_button)
    ImageView ivBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvTitle;

    @BindView(R.id.et_bind_card_cardNo)
    EditText etCardNo;
    @BindView(R.id.bt_bind_card_sure)
    Button btSure;
    private String cardNo;

    @Override
    protected void initView() {
        StatusBarUtil.StatusBarLightMode(this);
        ivBack.setImageResource(R.drawable.back_black);
        tvTitle.setText(getString(R.string.card_bind_title));
    }

    @OnClick(R.id.bt_bind_card_sure)
    public void bind() {
        cardNo = etCardNo.getText().toString();
        if (TextUtils.isEmpty(cardNo)) {
            showError("请输入正确的卡号");
            return;
        }
        presenter.bindCard(cardNo);
    }

    @OnClick(R.id.iv_toolbar_left_button)
    public void back() {
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_card;
    }

    @Override
    protected void initPresenter() {
        presenter = new MinePresenter();
    }

    @Override
    public void showCards(String cardNo) {

    }

    @Override
    public void showNoCard() {

    }

    @Override
    public void bindSuccess(String cardNo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.bind_card_success);
        builder.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent();
                intent.putExtra("cardNo", cardNo);
                setResult(RESULT_OK, intent);
                BindCardActivity.this.finish();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void showBalance(String balance) {

    }
}
