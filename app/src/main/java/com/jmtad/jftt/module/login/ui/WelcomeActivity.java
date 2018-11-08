package com.jmtad.jftt.module.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.jmtad.jftt.R;
import com.jmtad.jftt.adapter.WelcomeAdapter;
import com.jmtad.jftt.base.BaseActivity;
import com.jmtad.jftt.module.main.ui.HomeActivity;

import butterknife.BindView;

/**
 * 引导页
 */
public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.vp_welcome)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //禁止滑动返回
        slidrInterface.lock();
    }

    @Override
    protected void initView() {
        WelcomeAdapter adapter = new WelcomeAdapter(this);
        adapter.setClickListener(position -> {
            if (position == 2) {
                startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
                finish();
            }
        });
        //viewpager缓存所有页面
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initPresenter() {

    }
}
