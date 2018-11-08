package com.jmtad.jftt.module.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.jmtad.jftt.R;
import com.jmtad.jftt.adapter.CardAdapter;
import com.jmtad.jftt.adapter.CardMenuAdpater;
import com.jmtad.jftt.base.BaseActivity;
import com.jmtad.jftt.customui.CircleImageView;
import com.jmtad.jftt.customui.ViewPagerExt;
import com.jmtad.jftt.customui.indicator.CircleIndicatorView;
import com.jmtad.jftt.module.mine.contract.MineContract;
import com.jmtad.jftt.module.mine.presenter.MinePresenter;
import com.jmtad.jftt.util.GlideUtil;
import com.jmtad.jftt.wechat.WechatInfoHelper;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MineActivity extends BaseActivity<MinePresenter> implements MineContract.IMineView {

    private final int REQ_CODE = 110;
    @BindView(R.id.vp_mine_card)
    ViewPager vpCard;
    @BindView(R.id.indicator_mine_card)
    CircleIndicatorView indicatorView;

    @BindView(R.id.iv_mine_head)
    CircleImageView ivHead;
    @BindView(R.id.vp_mine_content)
    ViewPagerExt vpContent;
    @BindView(R.id.iv_mine_back)
    ImageView ivBack;
    private CardAdapter cardAdapter;
    private CardMenuAdpater cardMenuAdpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.queryCard();
    }

    @Override
    protected void initView() {
        //如果头像地址不为空,显示用户头像
        String headImg = WechatInfoHelper.getWechatHeadimgurl();
        if (!TextUtils.isEmpty(headImg)) {
            GlideUtil.loadImage(this, headImg, ivHead);
        }
        cardAdapter = new CardAdapter(this);
        vpCard.setPageMargin((int) getResources().getDimension(R.dimen.px_30));
        vpCard.setOffscreenPageLimit(4);
        vpCard.setAdapter(cardAdapter);
        indicatorView.setUpWithViewPager(vpCard);
        indicatorView.setVisibility(View.INVISIBLE);
        List<MenuFragment> fragments = new ArrayList<>();
        //TODO  暂时只有一个
        for (int i = 0; i < 5; i++) {
            fragments.add(new MenuFragment());
        }
        cardMenuAdpater = new CardMenuAdpater(getSupportFragmentManager(), this, fragments);
        vpContent.setSlide(false);
        vpContent.setAdapter(cardMenuAdpater);
        vpCard.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                vpContent.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        cardAdapter.setClickBindCallBack(new CardAdapter.ClickBindCallback() {
            @Override
            public void goBind() {
                startActivityForResult(new Intent(MineActivity.this, BindCardActivity.class), REQ_CODE);
            }
        });
    }

    @OnClick(R.id.iv_mine_back)
    public void back() {
        finish();
    }

    @OnClick(R.id.iv_mine_head)
    public void editProfile() {
        startActivity(new Intent(MineActivity.this, ProfileActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mine;
    }

    @Override
    protected void initPresenter() {
        presenter = new MinePresenter();
    }

    @Override
    public void showCards(String cardNo) {
        indicatorView.setVisibility(View.INVISIBLE);
        cardAdapter.showCard(cardNo);
        presenter.queryBalance();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //如果已经绑定卡,每次回到个人中心重新查询油滴
        if (cardAdapter.isHasCard()) {
            presenter.queryBalance();
        }
    }

    @Override
    public void showNoCard() {
        indicatorView.setVisibility(View.INVISIBLE);
        cardAdapter.setNoCard();
    }

    @Override
    public void bindSuccess(String cardNo) {

    }

    @Override
    public void showBalance(String balance) {
        //格式化货币
        NumberFormat format = new DecimalFormat("#,###");
        double bal = Double.valueOf(balance);
        cardAdapter.setBalance(format.format(bal));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (null != data) {
                String cardNo = data.getStringExtra("cardNo");
                showCards(cardNo);
                presenter.queryBalance();
            }
        }
    }
}
