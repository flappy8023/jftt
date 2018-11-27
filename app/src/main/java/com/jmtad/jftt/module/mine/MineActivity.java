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

/**
 * 个人中心页面，每张卡片对应下方一个菜单，当滑动切换卡片时相应切换下方的菜单
 */
public class MineActivity extends BaseActivity<MinePresenter> implements MineContract.IMineView {

    private final int REQ_CODE = 110;
    /**
     * 卡片对应的菜单列表适配器
     */
    @BindView(R.id.vp_mine_card)
    ViewPager vpCard;
    /**
     * 卡片列表指示器，当卡片个数大于1个时进行展示
     */
    @BindView(R.id.indicator_mine_card)
    CircleIndicatorView indicatorView;

    /**
     * 用户头像
     */
    @BindView(R.id.iv_mine_head)
    CircleImageView ivHead;
    @BindView(R.id.vp_mine_content)
    ViewPagerExt vpContent;
    @BindView(R.id.iv_mine_back)
    ImageView ivBack;

    /**
     * 上方卡片列表适配器
     */
    private CardAdapter cardAdapter;
    private CardMenuAdpater cardMenuAdpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //进入页面查询用户绑定的中石化加油卡
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
        //监听卡片滑动状态
        vpCard.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //卡片和菜单进行联动
                vpContent.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //设置绑卡按钮监听，点击后跳转绑卡页面
        cardAdapter.setClickBindCallBack(() -> startActivityForResult(new Intent(MineActivity.this, BindCardActivity.class), REQ_CODE));
    }

    @OnClick({R.id.iv_mine_back, R.id.tv_title})
    public void back() {
        finish();
    }

    /**
     * 用户点击个人头像，进入个人资料页面
     */
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

    /**
     * 展示中石化加油卡号
     *
     * @param cardNo
     */
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

    /**
     * 没有绑定加油卡时展示立即绑卡布局
     */
    @Override
    public void showNoCard() {
        indicatorView.setVisibility(View.INVISIBLE);
        cardAdapter.setNoCard();
    }

    @Override
    public void bindSuccess(String cardNo) {

    }

    /**
     * 展示用户油滴余额
     *
     * @param balance 油滴余额
     */
    @Override
    public void showBalance(String balance) {
        //格式化货币
        NumberFormat format = new DecimalFormat("#,###");
        double bal = Double.valueOf(balance);
        cardAdapter.setBalance(format.format(bal));
    }

    /**
     * 当用户从绑卡页面返回后，如果绑定成功展示卡号，同时查询当前油滴剩余
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
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
