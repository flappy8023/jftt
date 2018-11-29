package com.jmtad.jftt.manager;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.jmtad.jftt.http.bean.node.Banner;
import com.jmtad.jftt.module.banner.BannerDetailActivity;
import com.jmtad.jftt.module.banner.BannerLinkActivity;
import com.jmtad.jftt.util.SharedPreferenceUtil;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-11-29 17:09
 **/
public class BannerManager {
    public static void clickBanner(Banner banner, Context context) {
        if (TextUtils.equals(banner.getIsShowDetails(), "0")) {
            Intent intent = new Intent(context, BannerDetailActivity.class);
            intent.putExtra(BannerDetailActivity.KEY_BANNER, banner);
            context.startActivity(intent);
        } else {
            if (TextUtils.isEmpty(banner.getLinkUrl())) {
                return;
            }
            String url = banner.getLinkUrl() + "&userId=" + SharedPreferenceUtil.getInstance().getUserId() + "&unionId=" + SharedPreferenceUtil.getInstance().getUnionId();
            Intent intent = new Intent(context, BannerLinkActivity.class);
            intent.putExtra(BannerLinkActivity.KEY_LINK_URL, url);
            context.startActivity(intent);
        }
    }
}
