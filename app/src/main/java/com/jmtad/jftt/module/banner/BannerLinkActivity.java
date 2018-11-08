package com.jmtad.jftt.module.banner;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
    TextView title;
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
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                super.onPageStarted(view, url, favicon);
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                super.onPageFinished(view, url);
//            }
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不另跳浏览器
//                // 在2.3上面不加这句话，可以加载出页面，在4.0上面必须要加入，不然出现白屏
//                if (url.startsWith("http://") || url.startsWith("https://")) {
//                    view.loadUrl(url);
//                    webView.stopLoading();
//                    return true;
//                }
//                return false;
//            }
//
//            @Override
//            public void onReceivedError(WebView view, int errorCode,
//                                        String description, String failingUrl) {
//                super.onReceivedError(view, errorCode, description, failingUrl);
//            }
//
//            @Override
//            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                handler.proceed();
//            }
//        });
//        webView.setWebChromeClient(new WebChromeClient());
//
//        webView.requestFocus();
//
//        webView.setDownloadListener(new DownloadListener() {
//            @Override
//            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
//                downloadByBrowser(url);
//            }
//        });
        ivBack.setImageResource(R.drawable.back_black);
//        //清除缓存 解决白屏
//        webView.clearCache(true);
//        webView.clearHistory();
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        // 缓存白屏
//        String appCachePath = getApplicationContext().getCacheDir()
//                .getAbsolutePath() + "/webcache";
//// 设置 Application Caches 缓存目录
//        webSettings.setAppCachePath(appCachePath);
//        webSettings.setDatabasePath(appCachePath);
//        webSettings.setUserAgentString("User-Agent:Android");
//        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        webSettings.setAppCacheEnabled(false);
//        webSettings.setDomStorageEnabled(true);
//        webSettings.setDatabaseEnabled(true);
        if (null != getIntent()) {
            String link = getIntent().getStringExtra(KEY_LINK_URL);
            String title = getIntent().getStringExtra(KEY_TITLE);
            if (null != title) {
                this.title.setText(title);
            }
            if (!TextUtils.isEmpty(link)) {
                mAgentWeb = AgentWeb.with(this)
                        .setAgentWebParent(container, new LinearLayout.LayoutParams(-1, -1))
                        .useDefaultIndicator()
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
