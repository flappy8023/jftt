package com.jmtad.jftt.module.main.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jmtad.jftt.R;
import com.jmtad.jftt.adapter.HomeHeaderAdapter;
import com.jmtad.jftt.adapter.MainAdapter;
import com.jmtad.jftt.base.BaseActivity;
import com.jmtad.jftt.config.Constants;
import com.jmtad.jftt.customui.SharePopwindow;
import com.jmtad.jftt.customui.pullextend.ExtendListHeader;
import com.jmtad.jftt.customui.pullextend.PullExtendLayout;
import com.jmtad.jftt.customui.slide.ItemTouchHelperCallback;
import com.jmtad.jftt.customui.slide.OnSlideListener;
import com.jmtad.jftt.customui.slide.SlideLayoutManager;
import com.jmtad.jftt.http.bean.node.Banner;
import com.jmtad.jftt.module.banner.BannerDetailActivity;
import com.jmtad.jftt.module.main.MainContract;
import com.jmtad.jftt.module.main.MainPresenter;
import com.jmtad.jftt.module.mine.MineActivity;
import com.jmtad.jftt.module.setting.SettingActivity;
import com.jmtad.jftt.util.MyToast;
import com.jmtad.jftt.util.QRCodeUtil;
import com.jmtad.jftt.util.SharedPreferenceUtil;
import com.jmtad.jftt.util.wechat.WechatUtil;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity<MainPresenter> implements MainContract.IMainView, MainAdapter.HomeListener {
    @BindView(R.id.pull_extend)
    PullExtendLayout pullExtendLayout;
    @BindView(R.id.extend_header)
    ExtendListHeader extendListHeader;
    @BindView(R.id.rv_main_container)
    RecyclerView recyclerView;
    private final int PAGE_SIZE = 5;
    PopupWindow topMenu;
    @BindView(R.id.iv_main_top_menu)
    ImageView ivTopMenu;
    //图文总条数
    private int mTotal = 0;
    //总页数
    private int totalPages = 0;
    //页数
    private int pageNo = 1;
    //图文列表
    private List<Banner> mBanners = new ArrayList<>();
    private MainAdapter mainAdapter;
    private ItemTouchHelperCallback<Banner> itemTouchHelperCallback;
    private ItemTouchHelper itemTouchHelper;
    private RecyclerView headerRecyclerView;
    private HomeHeaderAdapter headerAdapter;
    //分享生成二维码
    @BindView(R.id.iv_home_qrcode)
    ImageView ivQRCode;
    private SharePopwindow popwindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        slidrInterface.lock();
        presenter.queryBannerList(pageNo, PAGE_SIZE, "0");
    }

    @Override
    protected void initView() {

        mainAdapter = new MainAdapter(HomeActivity.this, mBanners);
        //初始化下拉布局
        itemTouchHelperCallback = new ItemTouchHelperCallback(mainAdapter, mBanners);
        itemTouchHelperCallback.setOnSlideListener(onSlideListener);
        itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        SlideLayoutManager layoutManager = new SlideLayoutManager(recyclerView, itemTouchHelper, HomeActivity.this);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        mainAdapter.setHomeListener(this);
        recyclerView.setAdapter(mainAdapter);
        mainAdapter.setItemTouchHelper(itemTouchHelper);
        //初始化头部数据
        initHeader();

    }

    private void initHeader() {
        headerRecyclerView = extendListHeader.getRecyclerView();
        headerRecyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, false));
        headerAdapter = new HomeHeaderAdapter(HomeActivity.this, mBanners);
        headerRecyclerView.setAdapter(headerAdapter);
        headerAdapter.setListener(new HomeHeaderAdapter.HeaderClickListener() {
            @Override
            public void onClick(Banner banner) {
                toDetail(banner);
            }

            @Override
            public void onLongClick(Banner banner) {
                MyToast.showLongToast(HomeActivity.this, "长按删除");
            }
        });
    }

    private OnSlideListener<Banner> onSlideListener = new OnSlideListener<Banner>() {
        @Override
        public void onSliding(RecyclerView.ViewHolder viewHolder, float ratio, int direction) {

        }

        @Override
        public void onSlided(RecyclerView.ViewHolder viewHolder, Banner banner, int direction, int position) {
            int offset = PAGE_SIZE / 2;
            //position是当前所有浏览的计数,循环展示时会超过总数
            position = position % mTotal;
            //当总数超过两页,当前已请求到的图文数量小于总数量,且滑动到二分之一pagesize位置时开始请求下一页
            if (position % PAGE_SIZE == offset && mTotal > PAGE_SIZE && pageNo < totalPages) {
                pageNo++;
                presenter.queryBannerList(pageNo, PAGE_SIZE, "0");
            }
        }

        @Override
        public void onClear(List<Banner> temp) {
            int beforeSize = mBanners.size();
            //当前列表全部展示一遍后,循环展示刷新数据
            mBanners.addAll(temp);

            mainAdapter.notifyItemRangeChanged(beforeSize, temp.size());
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initPresenter() {
        presenter = new MainPresenter();
    }

    @Override
    public void loadBannerList(List<Banner> banners, int total, int pages) {
        this.mTotal = total;
        this.totalPages = pages;
        int beforeSize = mBanners.size();
        mBanners.addAll(banners);
        mainAdapter.notifyItemRangeChanged(beforeSize, banners.size());
    }

    @Override
    public void noBanners() {

    }

    @Override
    public void starSucc(View view, long stars) {
        TextView tvStar = view.findViewById(R.id.tv_home_news_likes);
        ImageView ivStar = view.findViewById(R.id.iv_star);
        ivStar.setImageDrawable(getResources().getDrawable(R.drawable.liked));
        tvStar.setText(String.valueOf(stars));
    }

    @Override
    public void unStarSucc(View view, long stars) {
        TextView tvStar = view.findViewById(R.id.tv_home_news_likes);
        ImageView ivStar = view.findViewById(R.id.iv_star);
        ivStar.setImageDrawable(getResources().getDrawable(R.drawable.like));
        tvStar.setText(String.valueOf(stars));
    }

    @OnClick(R.id.iv_main_top_menu)
    public void showMenu() {
        if (null == topMenu) {
            View view = LayoutInflater.from(this).inflate(R.layout.home_popmenu_layout, null);
            topMenu = new PopupWindow(view, (int) getResources().getDimension(R.dimen.px_240), (int) getResources().getDimension(R.dimen.px_120));
            topMenu.setBackgroundDrawable(getResources().getDrawable(R.drawable.home_menu_bg));
            topMenu.setOutsideTouchable(true);
            topMenu.setFocusable(true);
            topMenu.setTouchable(true);
            topMenu.showAsDropDown(ivTopMenu, 0, (int) getResources().getDimension(R.dimen.px_20), Gravity.END);
            LinearLayout menuMine = view.findViewById(R.id.ll_home_menu_mine);
            LinearLayout menuSetting = view.findViewById(R.id.ll_home_menu_setting);
            //跳转我的页面
            menuMine.setOnClickListener((view1 -> {
                        startActivity(new Intent(this, MineActivity.class));
                        topMenu.dismiss();
                    })
            );
            //跳转社这页面
            menuSetting.setOnClickListener((view1 -> {
                startActivity(new Intent(this, SettingActivity.class));
                topMenu.dismiss();
            }));
        } else {
            if (topMenu.isShowing()) {
                topMenu.dismiss();
            } else {
                topMenu.showAsDropDown(ivTopMenu, 0, (int) getResources().getDimension(R.dimen.px_20), Gravity.END);
            }
        }
    }

    @Override
    public void share(Banner banner) {
        if (null == popwindow) {
            popwindow = new SharePopwindow(HomeActivity.this, view -> {
                switch (view.getId()) {
                    //分享到微信会话
                    case R.id.wechat_share_session:
                        new Thread(() -> {
                            WechatUtil.getInstance().shareImg(createQRCodeBitmap(), SendMessageToWX.Req.WXSceneSession);
                            recyclerView.post((Runnable) () -> popwindow.dismiss());
                        }).start();

                        return;
                    //分享到朋友圈
                    case R.id.wechat_share_timeline:
                        new Thread(() -> {
                            WechatUtil.getInstance().shareImg(createQRCodeBitmap(), SendMessageToWX.Req.WXSceneTimeline);
                            recyclerView.post((Runnable) () -> popwindow.dismiss());
                        }).start();

                        return;
                }
            });
        }
        popwindow.showAtLocation(recyclerView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 生成分享所用的图片
     *
     * @return
     */
    private Bitmap createQRCodeBitmap() {
        Bitmap bmp = Bitmap.createBitmap(recyclerView.getWidth(), recyclerView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        recyclerView.draw(canvas);
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
    public void starOrUnstar(Banner banner, View view) {
        presenter.starOrUnStar(banner, view);
    }

    @Override
    public void toDetail(Banner banner) {
        Intent intent = new Intent(HomeActivity.this, BannerDetailActivity.class);
        intent.putExtra(BannerDetailActivity.KEY_BANNER_ID, banner.getId());
        startActivity(intent);
    }
}
