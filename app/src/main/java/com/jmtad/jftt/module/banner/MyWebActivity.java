package com.jmtad.jftt.module.banner;

import android.support.v4.widget.NestedScrollView;
import android.widget.LinearLayout;

import com.jmtad.jftt.R;
import com.jmtad.jftt.base.BaseActivity;
import com.just.agentweb.AgentWeb;

import butterknife.BindView;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-11-01 04:34
 **/
public class MyWebActivity extends BaseActivity {
    @BindView(R.id.nestedScrollView)
    NestedScrollView container;

    @Override
    protected void initView() {
        AgentWeb.with(this)
                .setAgentWebParent(container, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go("http://www.jd.com");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_banner_link;
    }

    @Override
    protected void initPresenter() {

    }
}
