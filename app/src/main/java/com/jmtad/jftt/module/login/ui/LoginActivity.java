package com.jmtad.jftt.module.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jmtad.jftt.R;
import com.jmtad.jftt.base.BaseActivity;
import com.jmtad.jftt.customui.CircleImageView;
import com.jmtad.jftt.event.WXLoginEvent;
import com.jmtad.jftt.http.HttpApi;
import com.jmtad.jftt.http.RxCallBack;
import com.jmtad.jftt.http.bean.node.SaveAuthRespData;
import com.jmtad.jftt.http.bean.response.BaseResponse;
import com.jmtad.jftt.http.bean.response.SaveAuthResp;
import com.jmtad.jftt.module.login.contract.LoginContract;
import com.jmtad.jftt.module.login.presenter.LoginPresenter;
import com.jmtad.jftt.util.ApkUtil;
import com.jmtad.jftt.util.GlideUtil;
import com.jmtad.jftt.util.SharedPreferenceUtil;
import com.jmtad.jftt.util.wechat.WechatUtil;
import com.jmtad.jftt.wechat.model.WXUserInfo;
import com.victor.loading.rotate.RotateLoading;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.util.LogUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 登录页面,目前微信登录
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.ILoginView {
    @BindView(R.id.bt_choose_top)
    TextView btTop;
    @BindView(R.id.bt_choose_bottom)
    TextView btBottom;
    @BindView(R.id.rl_choose_login_logo)
    RelativeLayout rlLogo;
    @BindView(R.id.iv_choose_login_head)
    CircleImageView ivHead;
    @BindView(R.id.tv_choose_login_nickname)
    TextView tvName;
    @BindView(R.id.loading_view)
    RotateLoading loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        //在登录页面禁用滑动返回功能
        slidrInterface.lock();
    }

    @Override
    protected void initView() {

    }

    /**
     * 微信登录
     */
    @OnClick(R.id.bt_choose_bottom)
    public void loginByWechat() {
        WechatUtil.getInstance().authorizeByWechat();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_login;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void wxLoginSuc(WXLoginEvent event) {
        //微信登陆成功返回后展示loading,保存鉴权信息
        loading.start();
        WXUserInfo userInfo = event.getUserInfo();
        HttpApi.getInstance().service.saveAuthData(userInfo.getUnionid(), userInfo.getNickname(), userInfo.getSex(), userInfo.getHeadimgurl(), ApkUtil.getDevice())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new RxCallBack<SaveAuthResp>() {
            @Override
            public void onSuccess(SaveAuthResp resp) {
                loading.stop();
                if (TextUtils.equals(BaseResponse.CODE_0, resp.getCode())) {
                    //缓存用户信息到本地
                    saveUserInfo(userInfo, resp.getData());
                    showSuccView(event, resp.getData());
                } else {
                    showError(resp.getMsg());
                }
            }

            @Override
            public void onFail(Throwable e) {
                LogUtils.error(e);
                loading.stop();
            }
        });
    }

    private void saveUserInfo(WXUserInfo userInfo, SaveAuthRespData data) {
        SharedPreferenceUtil.getInstance().saveUserId(data.getUserId());
        SharedPreferenceUtil.getInstance().saveUionId(data.getUnionid());
        SharedPreferenceUtil.getInstance().savePhone(data.getPhone());
    }

    /**
     * 展示登陆选择界面
     *
     * @param event
     * @param data
     */
    private void showSuccView(WXLoginEvent event, SaveAuthRespData data) {
        rlLogo.setBackground(getResources().getDrawable(R.drawable.choose_login_head_bg));
        btTop.setVisibility(View.VISIBLE);
        btTop.setText(getString(R.string.choose_login_wechat_direct));
        if (TextUtils.isEmpty(data.getPhone())) {
            btBottom.setText(getString(R.string.choose_login_bind_phone));
        } else {
            btBottom.setText("已绑定手机号");
            btBottom.setEnabled(false);
        }
        String wechatNickname = event.getUserInfo().getNickname();
        tvName.setVisibility(View.VISIBLE);
        tvName.setText(wechatNickname);
        String imgUrl = event.getUserInfo().getHeadimgurl();
        ivHead.setVisibility(View.VISIBLE);
        GlideUtil.loadImage(this, imgUrl, ivHead);
        //点击直接登录按钮
        btTop.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
            finish();
        });
        //点击绑定手机按钮
        btBottom.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, LoginBindActivity.class));
        });
    }

    @Override
    public void sendCodeSuc() {

    }

    @Override
    public void sendCodeFail() {

    }

    @Override
    public void bindPhoneSuc() {

    }

    @Override
    public void bindPhoneFail() {

    }
}
