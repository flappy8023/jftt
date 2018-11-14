package com.jmtad.jftt.module.banner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jmtad.jftt.MyScrollView;
import com.jmtad.jftt.R;
import com.jmtad.jftt.base.BaseActivity;
import com.jmtad.jftt.customui.SharePopwindow;
import com.jmtad.jftt.event.RefreshBannerEvent;
import com.jmtad.jftt.http.bean.node.Banner;
import com.jmtad.jftt.module.banner.contract.DetailContract;
import com.jmtad.jftt.module.banner.presenter.DetailPresenter;
import com.jmtad.jftt.util.StatusBarUtil;
import com.jmtad.jftt.util.wechat.WeShareUtil;
import com.just.agentweb.AgentWebView;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 图文详情页
 */
public class BannerDetailActivity extends BaseActivity<DetailPresenter> implements DetailContract.IDetailView {
    public static final String KEY_BANNER = "mBanner";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_banner_detail_content)
    AgentWebView webView;
    private Banner mBanner;
    private String bannerId;
    @BindView(R.id.bt_go_link)
    FloatingActionButton btLink;
    @BindView(R.id.nestedScrollView)
    MyScrollView scrollView;
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
            mBanner = (Banner) getIntent().getSerializableExtra(KEY_BANNER);
            bannerId = mBanner.getId();
            //点赞状态
            if (TextUtils.equals(mBanner.getStarStatus(), "0")) {
                ivStar.setImageDrawable(getResources().getDrawable(R.drawable.liked));
            } else {
                ivStar.setImageDrawable(getResources().getDrawable(R.drawable.like_black));
            }
            presenter.queryBannerByID(bannerId);
        }
        //滚动中隐藏跳转按钮，停止后显示按钮
        scrollView.addOnScrollListner(myScrollListener);
    }

    private MyScrollView.OnMyScrollListener myScrollListener = new MyScrollView.OnMyScrollListener() {
        @Override
        public void onScrollStateChanged(MyScrollView view, int state) {
            if (state == MyScrollView.OnMyScrollListener.SCROLL_STATE_FLING || state == MyScrollView.OnMyScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                animateOut();
            } else {
                animateIn();
            }
        }

        @Override
        public void onScrollToTop() {
            animateIn();
        }

        @Override
        public void onScrollToBottom() {
            animateIn();
        }

        @Override
        public void onScroll(int l, int t, int oldl, int oldt) {
            //向下滚动时展示标题,滚动到顶部时隐藏标题
            if (t > 100) {
                if (null != mBanner && null != mBanner.getAuthor()) {
                    toolbar.setTitle(mBanner.getAuthor());
                }
            } else {
                toolbar.setTitle("");
            }
        }
    };

    private void animateIn() {
        if (btLink.getVisibility() == View.GONE) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 14) {
            ViewCompat.animate(btLink).scaleX(1.0F).scaleY(1.0F).alpha(1.0F)
                    .withLayer().setListener(null)
                    .start();

        }
    }

    private void animateOut() {
        if (btLink.getVisibility() == View.GONE) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 14) {
            ViewCompat.animate(btLink).scaleX(0.0F).scaleY(0.0F).alpha(0.0F).withLayer()
                    .setListener(new ViewPropertyAnimatorListener() {
                        public void onAnimationStart(View view) {
                        }

                        public void onAnimationCancel(View view) {
                        }

                        public void onAnimationEnd(View view) {
                            btLink.setVisibility(View.INVISIBLE);
                        }
                    }).start();

        }
    }

    private void showBanner() {
        if (null != mBanner) {
            if (!TextUtils.isEmpty(mBanner.getLinkUrl())) {
                btLink.setVisibility(View.VISIBLE);
            } else {
                btLink.setVisibility(View.GONE);

            }
            if (!TextUtils.isEmpty(mBanner.getTitle())) {
                tvTitle.setText(mBanner.getTitle());
            }
            if (!TextUtils.isEmpty(mBanner.getAuthor())) {
                tvAuthor.setText(mBanner.getAuthor());
            }
            //原始格式:2018-10-10 10:10:10
            if (!TextUtils.isEmpty(mBanner.getCreateTime())) {
                String date = mBanner.getCreateTime().substring(0, 10);
                tvDate.setText(date);
            }
            //点赞数
            tvLikes.setText(mBanner.getStars() + "");
            //浏览量
            tvViews.setText(mBanner.getViews() + "");
            webView.loadDataWithBaseURL(null, getHtmlData(mBanner.getContentText()), "text/html", "UTF-8", null);
            toolbar.setTitle("");
            //增加阅读数
            presenter.addViews(mBanner.getId());
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
                            WeShareUtil.shareUrl(mBanner.getTitle(), mBanner.getSummary(), mBanner.getLinkUrl(), thumb, SendMessageToWX.Req.WXSceneSession);
                            webView.post(() -> popwindow.dismiss());
                        }).start();

                        return;
                    //分享到朋友圈
                    case R.id.wechat_share_timeline:
                        new Thread(() -> {
                            Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
                            WeShareUtil.shareUrl(mBanner.getTitle(), mBanner.getSummary(), mBanner.getLinkUrl(), thumb, SendMessageToWX.Req.WXSceneTimeline);
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
        if (null != mBanner) {
            Intent intent = new Intent(BannerDetailActivity.this, BannerLinkActivity.class);
            intent.putExtra(BannerLinkActivity.KEY_LINK_URL, mBanner.getLinkUrl());
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //返回首页刷新当前的banner信息
        RefreshBannerEvent event = new RefreshBannerEvent();
        event.setCurrentBanner(mBanner);
        EventBus.getDefault().post(event);
    }

    @OnClick(R.id.ll_banner_detail_like_container)
    public void starOrUnstar() {
        if (null != mBanner) {
            presenter.starOrUnStar(mBanner.getId());
        }
    }

    @Override
    public void starSuc() {
        ivStar.setImageDrawable(getResources().getDrawable(R.drawable.liked));
        mBanner.setStars(mBanner.getStars() + 1);
        mBanner.setStarStatus(Banner.STATUS_STARED);
        tvLikes.setText(mBanner.getStars() + "");
    }

    @Override
    public void unStarSuc() {
        ivStar.setImageDrawable(getResources().getDrawable(R.drawable.like_black));
        mBanner.setStars(mBanner.getStars() - 1);
        mBanner.setStarStatus(Banner.STATUS_UNSTARED);
        tvLikes.setText(mBanner.getStars() + "");
    }

    @Override
    public void addViewsSuc(long views) {

        mBanner.setViews(views);
        tvViews.setText(views + "");
    }

    @Override
    public void loadBanner(Banner banner) {
        this.mBanner = banner;
        showBanner();
    }

}
