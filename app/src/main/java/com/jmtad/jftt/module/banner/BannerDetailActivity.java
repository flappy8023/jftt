package com.jmtad.jftt.module.banner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jmtad.jftt.R;
import com.jmtad.jftt.base.BaseActivity;
import com.jmtad.jftt.customui.SharePopwindow;
import com.jmtad.jftt.http.bean.node.Banner;
import com.jmtad.jftt.module.banner.contract.DetailContract;
import com.jmtad.jftt.module.banner.presenter.DetailPresenter;
import com.jmtad.jftt.util.StatusBarUtil;
import com.jmtad.jftt.util.wechat.WeShareUtil;
import com.just.agentweb.AgentWebView;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 图文详情页
 */
public class BannerDetailActivity extends BaseActivity<DetailPresenter> implements DetailContract.IDetailView {
    public static final String KEY_BANNER_ID = "bannerId";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_banner_detail_content)
    AgentWebView webView;
    private Banner banner;
    private String bannerId;
    @BindView(R.id.bt_go_link)
    FloatingActionButton btLink;
    @BindView(R.id.nestedScrollView)
    NestedScrollView scrollView;
    @BindView(R.id.appbar_news_detail)
    AppBarLayout appBarLayout;
    private SharePopwindow popwindow;
    @BindView(R.id.tv_banner_title)
    TextView tvTitle;
    @BindView(R.id.tv_banner_author)
    TextView tvAuthor;
    @BindView(R.id.tv_banner_date)
    TextView tvDate;
    @BindView(R.id.tv_banner_detail_likes)
    TextView tvLikes;
    @BindView(R.id.tv_banner_detail_views)
    TextView tvViews;
    @BindView(R.id.iv_star)
    ImageView ivStar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        StatusBarUtil.StatusBarLightMode(this);
    }

    /**
     * 拼接html标签
     *
     * @param bodyHTML
     * @return
     */
    private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body style='margin:0;padding:0'>" + bodyHTML + "</body></html>";
    }

    @Override
    protected void initView() {
        initToolbar();
        //填充数据
        if (null != getIntent()) {
            bannerId = getIntent().getStringExtra(KEY_BANNER_ID);
            presenter.queryBannerByID(bannerId);
        }
        scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            //向下滚动时展示标题,滚动到顶部时隐藏标题
            if (scrollY > 100) {
                if (null != banner && null != banner.getAuthor()) {
                    toolbar.setTitle(banner.getAuthor());
                }
            } else {
                toolbar.setTitle("");
            }
                }
        );
//        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//
//            }
//
//        });

    }

    private void showBanner() {
        if (null != banner) {
            if (!TextUtils.isEmpty(banner.getLinkUrl())) {
                btLink.setVisibility(View.VISIBLE);
            } else {
                btLink.setVisibility(View.GONE);

            }
            if (!TextUtils.isEmpty(banner.getTitle())) {
                tvTitle.setText(banner.getTitle());
            }
            if (!TextUtils.isEmpty(banner.getAuthor())) {
                tvAuthor.setText(banner.getAuthor());
            }
            //原始格式:2018-10-10 10:10:10
            if (!TextUtils.isEmpty(banner.getCreateTime())) {
                String date = banner.getCreateTime().substring(0, 10);
                tvDate.setText(date);
            }
            //点赞数
            tvLikes.setText(banner.getStars() + "");
            //点赞状态
            if (TextUtils.equals(banner.getStarStatus(), "0")) {
                ivStar.setImageDrawable(getResources().getDrawable(R.drawable.liked));
            } else {
                ivStar.setImageDrawable(getResources().getDrawable(R.drawable.like_black));
            }
            //浏览量
            tvViews.setText(banner.getViews() + "");
            webView.loadDataWithBaseURL(null, getHtmlData(banner.getContentText()), "text/html", "UTF-8", null);
            toolbar.setTitle("");
            //增加阅读数
            presenter.addViews(banner.getId());
        }
    }

    private void initToolbar() {
        toolbar.setNavigationIcon(R.drawable.close);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_banner_detail;
    }

    @Override
    protected void initPresenter() {
        presenter = new DetailPresenter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.banner_detail_share) {
            shareBanner();
        }
        //点击关闭按钮
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareBanner() {
        if (null == popwindow) {
            popwindow = new SharePopwindow(BannerDetailActivity.this, view -> {
                switch (view.getId()) {
                    //分享到微信会话
                    case R.id.wechat_share_session:
                        new Thread(() -> {
                            Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                            WeShareUtil.shareUrl(banner.getTitle(), banner.getSummary(), banner.getLinkUrl(), thumb, SendMessageToWX.Req.WXSceneSession);
                            webView.post(() -> popwindow.dismiss());
                        }).start();

                        return;
                    //分享到朋友圈
                    case R.id.wechat_share_timeline:
                        new Thread(() -> {
                            Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                            WeShareUtil.shareUrl(banner.getTitle(), banner.getSummary(), banner.getLinkUrl(), thumb, SendMessageToWX.Req.WXSceneTimeline);
                            runOnUiThread(() -> popwindow.dismiss());
                        }).start();

                        return;
                }
            });
        }
        popwindow.showAtLocation(webView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @OnClick(R.id.bt_go_link)
    public void toDetail() {
        if (null != banner) {
            Intent intent = new Intent(BannerDetailActivity.this, BannerLinkActivity.class);
            intent.putExtra(BannerLinkActivity.KEY_LINK_URL, banner.getLinkUrl());
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.ll_banner_detail_like_container)
    public void starOrUnstar() {
        if (null != banner) {
            presenter.starOrUnStar(banner.getId());
        }
    }

    @Override
    public void starSuc() {
        ivStar.setImageDrawable(getResources().getDrawable(R.drawable.liked));
        tvLikes.setText((banner.getStars() + 1) + "");
    }

    @Override
    public void unStarSuc() {
        ivStar.setImageDrawable(getResources().getDrawable(R.drawable.like_black));
        tvLikes.setText((banner.getStars() - 1) + "");
    }

    @Override
    public void addViewsSuc(long views) {
        tvViews.setText(views + "");
    }

    @Override
    public void loadBanner(Banner banner) {
        this.banner = banner;
        showBanner();
    }
}
