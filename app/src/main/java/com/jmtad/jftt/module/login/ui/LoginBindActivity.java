package com.jmtad.jftt.module.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.jmtad.jftt.R;
import com.jmtad.jftt.base.BaseActivity;
import com.jmtad.jftt.customui.CountDownTextView;
import com.jmtad.jftt.module.login.contract.LoginContract;
import com.jmtad.jftt.module.login.presenter.LoginPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 微信登录之后绑定手机页面
 */
public class LoginBindActivity extends BaseActivity<LoginPresenter> implements LoginContract.ILoginView {

    @BindView(R.id.et_bind_phone_code)
    EditText etCode;
    @BindView(R.id.et_bind_phone_phone)
    EditText etPhone;
    @BindView(R.id.bt_send_code)
    CountDownTextView btSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        btSend.setCountDownColor(R.color.countDown_usable_color, R.color.countDown_unusable_color);
        btSend.setEndHint("s");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_bind;
    }

    @Override
    protected void initPresenter() {
        presenter = new LoginPresenter();
    }

    @Override
    public void sendCodeSuc() {
        btSend.setCountDownMillis(60 * 1000);
        btSend.start();
    }

    @Override
    public void sendCodeFail() {

    }

    @Override
    public void bindPhoneSuc() {
        startActivity(new Intent(this, WelcomeActivity.class));
        finish();
    }

    @Override
    public void bindPhoneFail() {
        showError("验证码错误");
        btSend.setUsable(false);
    }

    @OnClick(R.id.bt_send_code)
    public void sendCode() {
        presenter.sendCode(etPhone.getText().toString());
    }

    @OnClick(R.id.bt_login_bind_sure)
    public void submit() {
        presenter.bindPhone(etPhone.getText().toString(), etCode.getText().toString());
    }

    @OnClick(R.id.bt_login_bind_back)
    public void back() {
        finish();
    }
}
