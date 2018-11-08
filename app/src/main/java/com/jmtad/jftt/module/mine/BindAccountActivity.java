package com.jmtad.jftt.module.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jmtad.jftt.R;
import com.jmtad.jftt.base.BaseActivity;
import com.jmtad.jftt.customui.CountDownTextView;
import com.jmtad.jftt.module.login.contract.LoginContract;
import com.jmtad.jftt.module.login.presenter.LoginPresenter;
import com.jmtad.jftt.util.SharedPreferenceUtil;
import com.jmtad.jftt.util.StatusBarUtil;
import com.jmtad.jftt.wechat.WechatInfoHelper;

import butterknife.BindView;
import butterknife.OnClick;

public class BindAccountActivity extends BaseActivity<LoginPresenter> implements LoginContract.ILoginView {
    @BindView(R.id.iv_toolbar_left_button)
    ImageView ivBack;
    @BindView(R.id.tv_toolbar_title)
    TextView tvTitle;
    @BindView(R.id.bt_send_verifyCode)
    CountDownTextView sendCode;
    @BindView(R.id.et_bind_account_phoneNO)
    EditText etPhone;
    @BindView(R.id.et_bind_account_verCode)
    EditText etVerCode;
    @BindView(R.id.tv_phone_state_title)
    TextView tvPhoneState;
    @BindView(R.id.tv_wechat_state_title)
    TextView tvWechatState;

    @BindView(R.id.bt_bind_account_wechat)
    Button btBindWechat;
    @BindView(R.id.bt_bind_account_phone)
    Button btBindPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
    }

    @Override
    protected void initView() {
        ivBack.setImageResource(R.drawable.back_black);
        tvTitle.setText(R.string.bind_account_title);
        String phone = SharedPreferenceUtil.getInstance().getPhone();
        //已绑定手机号展示手机号,隐藏验证码
        if (!TextUtils.isEmpty(phone)) {
            tvPhoneState.setText(R.string.bind_binded);
            etPhone.setVisibility(View.GONE);
            etVerCode.setVisibility(View.GONE);
            sendCode.setVisibility(View.GONE);
            btBindPhone.setVisibility(View.VISIBLE);
            btBindPhone.setText(phone);
        }
        if (!TextUtils.isEmpty(SharedPreferenceUtil.getInstance().getUnionId())) {
            btBindWechat.setText(WechatInfoHelper.getWechatNickname());
        }

    }

    @OnClick(R.id.iv_toolbar_left_button)
    public void back() {
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_account;
    }

    @OnClick(R.id.bt_send_verifyCode)
    public void sendCode() {
        String phone = etPhone.getText().toString();
        //校验
        presenter.sendCode(phone);

    }

    @OnClick(R.id.bt_bind_account_submit)
    public void submit() {
        presenter.bindPhone(etPhone.getText().toString(), etVerCode.getText().toString());
    }

    @Override
    protected void initPresenter() {
        presenter = new LoginPresenter();
    }

    @Override
    public void sendCodeSuc() {
        sendCode.setCountDownMillis(60 * 1000);
        sendCode.start();
    }

    @Override
    public void sendCodeFail() {

    }

    @Override
    public void bindPhoneSuc() {
        finish();
    }

    @Override
    public void bindPhoneFail() {
        sendCode.setUsable(false);
        showError("验证码错误");
    }
}
