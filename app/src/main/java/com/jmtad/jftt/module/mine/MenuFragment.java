package com.jmtad.jftt.module.mine;

import android.content.Intent;
import android.view.View;

import com.jmtad.jftt.R;
import com.jmtad.jftt.base.BaseMvpFragment;
import com.jmtad.jftt.module.banner.BannerLinkActivity;
import com.jmtad.jftt.util.SharedPreferenceUtil;

import butterknife.OnClick;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-10-17 14:58
 **/
public class MenuFragment extends BaseMvpFragment {

    @Override
    protected void initView(View view) {

    }

    @Override
    protected int attachLayoutRes() {
        return R.layout.frag_mine_menu;
    }

    @Override
    protected void initPresenter() {

    }

    @OnClick(R.id.ll_card_menu_my_msg)
    public void goMessage() {
        startActivity(new Intent(getActivity(), SysMessageActivity.class));
    }

    @OnClick(R.id.ll_card_menu_bindaccount)
    public void bindAccount() {
        startActivity(new Intent(getActivity(), BindAccountActivity.class));
    }

    @OnClick(R.id.ll_card_menu_faq)
    public void faq() {
        startActivity(new Intent(getActivity(), FAQActivity.class));
    }


    @OnClick(R.id.ll_card_menu_dealQuery)
    public void dealQuery() {
        startActivity(new Intent(getActivity(), DealQueryActivity.class));
    }

    @OnClick(R.id.ll_card_menu_oilPool)
    public void oilPool() {
        String url = "https://game.juxinbox.com/app_oil_depot/dist/index.html#/?unionId=" + SharedPreferenceUtil.getInstance().getUnionId();
//        String url = "http://tdev.juxinbox.com/app_oil_depot/dist/index.html#/?unionId=oeAH9tuhNTgfxXnm1ujZdqoMmcnc" ;
//        String url1 = "https://game.juxinbox.com/app_oil_depot/dist/index.html?unionId=" + SharedPreferenceUtil.getInstance().getUnionId();

        Intent intent = new Intent(getActivity(), BannerLinkActivity.class);
        intent.putExtra(BannerLinkActivity.KEY_LINK_URL, url);
        intent.putExtra(BannerLinkActivity.KEY_TITLE, "我的油库");
        startActivity(intent);
    }

    @Override
    public void showError(String msg) {

    }
}
