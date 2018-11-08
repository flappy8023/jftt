package com.jmtad.jftt.module.main.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;
import com.jmtad.jftt.R;
import com.jmtad.jftt.adapter.HomeHeaderAdapter;
import com.jmtad.jftt.base.BaseActivity;
import com.jmtad.jftt.customui.FrameLayoutExt;
import com.jmtad.jftt.customui.pullextend.ExtendListHeader;
import com.jmtad.jftt.customui.pullextend.PullExtendLayout;
import com.jmtad.jftt.http.bean.node.Banner;
import com.jmtad.jftt.http.bean.response.BaseResponse;
import com.jmtad.jftt.http.bean.response.CheckUpdateResp;
import com.jmtad.jftt.module.home.HomeFragment;
import com.jmtad.jftt.module.main.MainContract;
import com.jmtad.jftt.module.main.MainPresenter;
import com.jmtad.jftt.module.mine.MineActivity;
import com.jmtad.jftt.module.setting.SettingActivity;
import com.jmtad.jftt.util.ApkUtil;
import com.jmtad.jftt.util.GlideUtil;
import com.jmtad.jftt.util.JsonParse;
import com.jmtad.jftt.util.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 首页
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.IMainView, FrameLayoutExt.SlideListener {
    @BindView(R.id.pull_extend)
    PullExtendLayout pullExtendLayout;
    @BindView(R.id.extend_header)
    ExtendListHeader extendListHeader;
    @BindView(R.id.fl_main_container)
    FrameLayout container;
    private int position = 0;
    @BindView(R.id.iv_temp)
    ImageView tempView;
    @BindView(R.id.iv_main_top_menu)
    ImageView ivTopMenu;
    PopupWindow topMenu;
    private final int PAGE_SIZE = 30;
    private RecyclerView headerRecyclerView;
    private HomeHeaderAdapter headerAdapter;
    //图文总条数
    private int mTotal = 0;
    private int totalPages = 0;
    //页数
    private int pageNo = 1;
    //图文列表
    private List<Banner> mBanners = new ArrayList<>();
    HomeFragment currentFragment = null;
    private RequestVersionListener listener = new RequestVersionListener() {
        @Nullable
        @Override
        public UIData onRequestVersionSuccess(String result) {
            CheckUpdateResp resp = JsonParse.json2Object(result, CheckUpdateResp.class);
            if (TextUtils.equals(BaseResponse.CODE_0, resp.getCode())) {
                //保存最新的下载地址
                if (null != resp && null != resp.getData()) {
                    SharedPreferenceUtil.getInstance().saveApkUrl(resp.getData().getDownloadUrl());
                    //如果返回版本号大于本地版本号,且属于强制更新,弹出更新对话框不可取消
                    if (ApkUtil.getVersionCode(MainActivity.this) < resp.getData().getIdenNumber() && TextUtils.equals(resp.getData().getIsAuto(), "1")) {
                        UIData data = UIData.create();
                        data.setContent(resp.getData().getExplainText());
                        data.setDownloadUrl(resp.getData().getDownloadUrl());
                        data.setTitle(resp.getData().getTitle());
                        return data;
                    }
                }
            } else {
                showError("请稍后再试");

            }
            return null;
        }

        @Override
        public void onRequestVersionFailure(String message) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        slidrInterface.lock();
        //启动后检测版本更新
        DownloadBuilder builder = presenter.checkUpdate(listener, false);
        builder.setShowDownloadingDialog(false);
        builder.executeMission(this);
    }

    @Override
    protected void initView() {
//        container.setSlideListener(this);
        //第一次请求图文列表
        presenter.queryBannerList(pageNo, PAGE_SIZE, "0");
        headerRecyclerView = extendListHeader.getRecyclerView();
        headerRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
        headerAdapter = new HomeHeaderAdapter(MainActivity.this, mBanners);
        headerRecyclerView.setAdapter(headerAdapter);

    }


    /**
     * 右上角菜单
     */
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
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initPresenter() {
        presenter = new MainPresenter();
    }


    /**
     * 向下滑动
     */
    @Override
    public void slideDown() {
        HomeFragment fragment = new HomeFragment();
        fragment.setSlideListener(this);
        fragment.setBanner(mBanners.get(position));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_top_in, R.anim.slide_bottom_out);
        transaction.replace(R.id.fl_main_container, fragment).commit();
        preLoadBannerPic();
        currentFragment = fragment;

    }

    /**
     * 向上滑动
     */
    @Override
    public void slideUp() {
        HomeFragment fragment = new HomeFragment();
        fragment.setSlideListener(this);
        fragment.setBanner(mBanners.get(position));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_bottom_in, R.anim.slide_top_out);
        transaction.replace(R.id.fl_main_container, fragment).commit();
        preLoadBannerPic();
        currentFragment = fragment;

    }

    /**
     * 向左滑动
     */
    @Override
    public void slideLeft() {
        HomeFragment fragment = new HomeFragment();
        fragment.setSlideListener(this);
        fragment.setBanner(mBanners.get(position));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out);
        transaction.replace(R.id.fl_main_container, fragment).commit();
        preLoadBannerPic();
        currentFragment = fragment;

    }

    /**
     * 向右滑动
     */
    @Override
    public void slideRight() {
        HomeFragment fragment = new HomeFragment();
        fragment.setSlideListener(this);
        fragment.setBanner(mBanners.get(position));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_right_out);
        transaction.replace(R.id.fl_main_container, fragment).commit();
        preLoadBannerPic();
        currentFragment = fragment;

    }

    /**
     * 判断是否可以滑动,最后一篇图文从头开始展示
     *
     * @return
     */
    @Override
    public boolean canSlide() {
        boolean canSlide = position < mTotal - 1;
        if (canSlide) {
            position++;
        } else {
            position = 0;
        }
        return true;
    }

    @Override
    public void onClick() {
        if (null != currentFragment) {
            currentFragment.toDetail();
        }
    }

    /**
     * 滑动完成回调
     */
    @Override
    public void onSlideDone() {
        int offset = PAGE_SIZE / 2;
        //当总数超过两页,当前已请求到的图文数量小于总数量,且滑动到二分之一pagesize位置时开始请求下一页
        if (position % PAGE_SIZE == offset && mTotal > PAGE_SIZE && mBanners.size() < mTotal) {
            pageNo++;
            presenter.queryBannerList(pageNo, PAGE_SIZE, "0");
        }
    }


    private void loadFirtBanner(Banner banner) {
        HomeFragment fragment = new HomeFragment();
        fragment.setSlideListener(this);
        fragment.setBanner(banner);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_main_container, fragment);
        transaction.commit();
        preLoadBannerPic();
        currentFragment = fragment;
    }

    private void preLoadBannerPic() {
        if ((position + 1) < mBanners.size() && null != mBanners.get(position + 1)) {
            GlideUtil.loadImage(MainActivity.this, mBanners.get(position + 1).getImgUrl(), tempView);
        }
    }

    @Override
    public void loadBannerList(List<Banner> banners, int total, int pages) {
        this.mBanners.addAll(banners);
        this.totalPages = pages;
        headerAdapter.notifyDataSetChanged();
        this.mTotal = total;
        //如果是第一次请求加载第一个图文
        if (1 == pageNo) {
            loadFirtBanner(mBanners.get(0));
        }
    }

    @Override
    public void noBanners() {

    }

}
