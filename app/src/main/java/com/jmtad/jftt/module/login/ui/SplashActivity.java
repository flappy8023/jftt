package com.jmtad.jftt.module.login.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.jmtad.jftt.R;
import com.jmtad.jftt.base.BaseActivity;
import com.jmtad.jftt.module.main.ui.HomeActivity;
import com.jmtad.jftt.util.SharedPreferenceUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @description:闪屏页面
 * @author: flappy8023
 * @create: 2018-10-10 14:34
 **/
public class SplashActivity extends BaseActivity {
    @BindView(R.id.splash_root)
    RelativeLayout welcomeRoot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        //透明度渐变动画
        AlphaAnimation animation = new AlphaAnimation(0.1f, 1.0f);
        animation.setDuration(2000);
        welcomeRoot.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                RxPermissions rxPermissions = new RxPermissions(SplashActivity.this);
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            String userId = SharedPreferenceUtil.getInstance().getUserId();
                            //如果本地不存在用户id,跳转登录页面,否则直接进入首页
                            if (TextUtils.isEmpty(userId)) {
                                showLogin();
                            } else {
                                goToMain();
                            }
                        } else {
                            finish();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 已登录用户直接进入主页
     */
    private void goToMain() {
        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        finish();
    }

    /**
     * 未登录用户进入登录页面
     */
    private void showLogin() {
        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();

    }


}
