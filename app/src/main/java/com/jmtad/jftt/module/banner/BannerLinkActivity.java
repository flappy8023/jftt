package com.jmtad.jftt.module.banner;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jmtad.jftt.R;
import com.jmtad.jftt.base.BaseActivity;
import com.jmtad.jftt.util.StatusBarUtil;
import com.just.agentweb.AgentWeb;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-10-29 16:43
 **/
public class BannerLinkActivity extends BaseActivity {
    //    @BindView(R.id.webview_banner_link)
//    WebView webView;
    public static final String KEY_LINK_URL = "linkurl";
    public static final String KEY_TITLE = "title";

    @BindView(R.id.tv_toolbar_title)
    TextView tvTitle;
    @BindView(R.id.iv_toolbar_left_button)
    ImageView ivBack;
    private AgentWeb mAgentWeb;
    @BindView(R.id.root)
    LinearLayout container;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.StatusBarLightMode(this);
    }

    @Override
    protected void initView() {
        ivBack.setImageResource(R.drawable.back_black);
        if (null != getIntent()) {
            String link = getIntent().getStringExtra(KEY_LINK_URL);
            String title = getIntent().getStringExtra(KEY_TITLE);
            if (null != title) {
                tvTitle.setText(title);
            }
            if (!TextUtils.isEmpty(link)) {
                mAgentWeb = AgentWeb.with(BannerLinkActivity.this)
                        .setAgentWebParent(container, new LinearLayout.LayoutParams(-1, -1))
                        .useDefaultIndicator()
                        .setWebViewClient(new WebViewClient() {
                            @Override
                            //设置网页标题
                            public void onPageFinished(WebView view, String url) {
                                super.onPageFinished(view, url);
                                String title = view.getTitle();
                                if (!TextUtils.isEmpty(title)) {
                                    tvTitle.setText(title);
                                }
                            }
                        })
                        .createAgentWeb()
                        .ready()
                        .go(link);
            }
        }
    }

    @Override
    protected void onPause() {
        if (null != mAgentWeb) {
            mAgentWeb.getWebLifeCycle().onPause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        if (null != mAgentWeb) {
            mAgentWeb.getWebLifeCycle().onResume();
        }
        super.onResume();
    }


    private void downloadByBrowser(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    @OnClick(R.id.iv_toolbar_left_button)
    public void back() {
        if (!mAgentWeb.back()) {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (!mAgentWeb.back()) {
            finish();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_banner_link;
    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void onDestroy() {
        if (null != mAgentWeb) {
            mAgentWeb.getWebLifeCycle().onDestroy();
        }
        super.onDestroy();
//        if (null != webView) {
//            webView.destroy();
//            webView = null;
//        }
    }
}
