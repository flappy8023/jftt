package com.jmtad.jftt.module.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jmtad.jftt.R;
import com.jmtad.jftt.base.BaseMvpFragment;
import com.jmtad.jftt.config.Constants;
import com.jmtad.jftt.customui.FrameLayoutExt;
import com.jmtad.jftt.customui.SharePopwindow;
import com.jmtad.jftt.http.bean.node.Banner;
import com.jmtad.jftt.module.banner.BannerDetailActivity;
import com.jmtad.jftt.module.banner.BannerLinkActivity;
import com.jmtad.jftt.module.main.ui.HomeActivity;
import com.jmtad.jftt.util.GlideUtil;
import com.jmtad.jftt.util.MyToast;
import com.jmtad.jftt.util.QRCodeUtil;
import com.jmtad.jftt.util.SharedPreferenceUtil;
import com.jmtad.jftt.util.wechat.WechatUtil;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Fragment切换方式在V1.1废弃,新的实现{@link HomeActivity}
 * @description:
 * @author: flappy8023
 * @create: 2018-10-11 14:08
 **/
@Deprecated
public class HomeFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.IHomeView {

    private FrameLayoutExt.SlideListener listener;
    //主海报
    @BindView(R.id.iv_home_frag_poster)
    ImageView ivPoster;
    //分享按钮
    @BindView(R.id.iv_home_news_share)
    ImageView btShare;
    @BindView(R.id.root_home_frag)
    RelativeLayout root;
    //点赞
    @BindView(R.id.tv_home_news_likes)
    TextView tvLikes;
    //阅读量
    @BindView(R.id.tv_home_news_views)
    TextView tvViews;
    //作者
    @BindView(R.id.tv_home_news_author)
    TextView tvAuthor;
    @BindView(R.id.tv_home_news_title)
    TextView tvTitle;
    @BindView(R.id.frame_container)
    FrameLayoutExt container;
    //分享生成二维码
    @BindView(R.id.iv_home_qrcode)
    ImageView ivQRCode;
    private SharePopwindow popwindow;
    //要显示的图文
    private Banner banner;
    //点赞数
    private long stars = 0;
    @BindView(R.id.iv_star)
    ImageView ivStar;

    public void setBanner(Banner banner) {
        this.banner = banner;
    }

    @Override
    protected void initView(View view) {
        container.setSlideListener(listener);
        if (null != banner) {
            //绑定图文数据
            if (!TextUtils.isEmpty(banner.getAuthor())) {
                tvAuthor.setText(banner.getAuthor());
            }
            if (!TextUtils.isEmpty(banner.getTitle())) {
                tvTitle.setText(banner.getTitle());
            }
            if (TextUtils.equals(banner.getStarStatus(), Banner.STATUS_STARED)) {
                ivStar.setImageResource(R.drawable.liked);
            } else {
                ivStar.setImageResource(R.drawable.like);
            }
            stars = banner.getStars();
            tvLikes.setText(stars + "");
            tvViews.setText(String.format(getString(R.string.home_news_view_format), banner.getViews()));

            GlideUtil.loadImage(getActivity(), banner.getImgUrl(), ivPoster);
            presenter.addViews(banner.getId());
        }
    }

    public void setSlideListener(FrameLayoutExt.SlideListener slideListener) {
        listener = slideListener;
    }

    /**
     * 点击跳转到详情页
     */
    @OnClick(R.id.iv_home_frag_poster)
    public void toDetail() {
        //判断是否展示详情页
        if (TextUtils.equals(banner.getIsShowDetails(), "0")) {
            Intent intent = new Intent(getActivity(), BannerDetailActivity.class);
            intent.putExtra(BannerDetailActivity.KEY_BANNER, banner);
            startActivity(intent);
        } else {
            //如果跳转链接不为空跳转到配置的链接
            if (!TextUtils.isEmpty(banner.getLinkUrl())) {
                Intent intent = new Intent(getActivity(), BannerLinkActivity.class);
                intent.putExtra(BannerLinkActivity.KEY_LINK_URL, banner.getLinkUrl());
                startActivity(intent);
            }
        }
    }

    @OnClick(R.id.ll_home_news_like_container)
    public void starOrUnStrar() {
        if (null != banner) {
            presenter.starOrUnStar(banner.getId());
        }
    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.frag_home;
    }

    @OnClick(R.id.iv_home_news_share)
    public void share() {
        if (null == popwindow) {
            popwindow = new SharePopwindow(getActivity(), view -> {
                switch (view.getId()) {
                    //分享到微信会话
                    case R.id.wechat_share_session:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                WechatUtil.getInstance().shareImg(createQRCodeBitmap(), SendMessageToWX.Req.WXSceneSession);
                                root.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        popwindow.dismiss();
                                    }
                                });
                            }
                        }).start();

                        return;
                    //分享到朋友圈
                    case R.id.wechat_share_timeline:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                WechatUtil.getInstance().shareImg(createQRCodeBitmap(), SendMessageToWX.Req.WXSceneTimeline);
                                root.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        popwindow.dismiss();
                                    }
                                });
                            }
                        }).start();

                        return;
                }
            });
        }
        popwindow.showAtLocation(btShare, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }

    @Override
    protected void initPresenter() {
        presenter = new HomePresenter();
    }

    @Override
    public void showError(String msg) {
        MyToast.showLongToast(getActivity(), msg);
    }

    /**
     * 生成分享所用的图片
     *
     * @return
     */
    private Bitmap createQRCodeBitmap() {
        Bitmap bmp = Bitmap.createBitmap(root.getWidth(), root.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        root.draw(canvas);
        //如果没有获取到最新下载地址,使用默认的下载地址
        String apkUrl = Constants.APK_DOWNLOAD_URL;
        if (!TextUtils.isEmpty(SharedPreferenceUtil.getInstance().getApkUrl())) {
            apkUrl = SharedPreferenceUtil.getInstance().getApkUrl();
        }
        //下载地址生成二维码
        Bitmap qrcode = QRCodeUtil.createQRCodeBitmap(apkUrl, ivQRCode.getWidth());
        Paint paint = new Paint();
        //覆盖到imageview占的位置
        canvas.drawBitmap(qrcode, ivQRCode.getX(), ivQRCode.getY(), paint);
        return bmp;
    }

    @Override
    public void addViewsSucc(long views) {
        setViews(views);
    }

    private void setViews(long views) {
        tvViews.setText(String.format(getString(R.string.home_news_view_format), views));
    }

    private void setStars(long stars) {
        tvLikes.setText(stars + "");
    }

    @Override
    public void unStarSucc() {
        ivStar.setImageResource(R.drawable.like);
        setStars(--stars);
    }

    @Override
    public void starSucc() {
        ivStar.setImageResource(R.drawable.liked);
        setStars(++stars);
    }
}
