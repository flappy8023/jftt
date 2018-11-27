package com.jmtad.jftt.wechat;

import com.jmtad.jftt.util.SharedPreferenceUtil;
import com.jmtad.jftt.wechat.model.WXAccessTokenInfo;
import com.jmtad.jftt.wechat.model.WXUserInfo;

/**
 * @description:微信信息缓存工具
 * @author: luweiming
 * @create: 2018-10-16 14:00
 **/
public class WechatInfoHelper {
    public static final String WECHAT_OPENID = "WECHAT_OPENID";
    public static final String WECHAT_NICKNAME = "WECHAT_NICKNAME";
    public static final String WECHAT_SEX = "WECHAT_SEX";
    public static final String WECHAT_PROVINCE = "WECHAT_PROVINCE";
    public static final String WECHAT_CITY = "WECHAT_CITY";
    public static final String WECHAT_COUNTRY = "WECHAT_COUNTRY";
    public static final String WECHAT_HEADIMGURL = "WECHAT_HEADIMGURL";
    public static final String WECHAT_UNIONID = "WECHAT_UNIONID";
    public static final String WECHAT_SCOPE = "WECHAT_SCOPE";
    public static final String WECHAT_ACCESS_TOKEN = "WECHAT_ACCESS_TOKEN";
    public static final String WECHAT_REFRESH_TOKEN = "WECHAT_REFRESH_TOKEN";

    public static String getWechatAccessToken() {
        return SharedPreferenceUtil.getInstance().getStringData(WECHAT_ACCESS_TOKEN, "");
    }

    public static String getWechatOpenid() {
        return SharedPreferenceUtil.getInstance().getStringData(WECHAT_OPENID, "");
    }

    public static String getWechatRefreshToken() {
        return SharedPreferenceUtil.getInstance().getStringData(WECHAT_REFRESH_TOKEN, "");
    }

    public static String getWechatNickname() {
        return SharedPreferenceUtil.getInstance().getStringData(WECHAT_NICKNAME, "");
    }

    public static String getWechatHeadimgurl() {
        return SharedPreferenceUtil.getInstance().getStringData(WECHAT_HEADIMGURL, "");
    }

    public static void saveWXAccessTokenInfo(WXAccessTokenInfo tokenInfo) {
        // 防止传null
        if (tokenInfo.getOpenid() == null) {
            tokenInfo.setOpenid("");
        }

        if (tokenInfo.getAccess_token() == null) {
            tokenInfo.setAccess_token("");
        }
        if (tokenInfo.getRefresh_token() == null) {
            tokenInfo.setRefresh_token("1");
        }
        if (tokenInfo.getScope() == null) {
            tokenInfo.setScope("");
        }
        SharedPreferenceUtil.getInstance().putString(WECHAT_OPENID, tokenInfo.getOpenid());
        SharedPreferenceUtil.getInstance().putString(WECHAT_ACCESS_TOKEN, tokenInfo.getAccess_token());
        SharedPreferenceUtil.getInstance().putString(WECHAT_SCOPE, tokenInfo.getScope());
    }

    public static void saveWXUserInfo(WXUserInfo info) {
        // 防止传null
        if (info.getOpenid() == null) {
            info.setOpenid("");
        }

        if (info.getNickname() == null) {
            info.setNickname("");
        }
        if (info.getSex() == null) {
            info.setSex("");
        }
        if (info.getProvince() == null) {
            info.setProvince("");
        }
        if (info.getCity() == null) {
            info.setCity("");
        }
        if (info.getCountry() == null) {
            info.setCountry("");
        }
        if (info.getHeadimgurl() == null) {
            info.setHeadimgurl("");
        }
        if (info.getUnionid() == null) {
            info.setUnionid("");
        }

        SharedPreferenceUtil.getInstance().putString(WECHAT_OPENID, info.getOpenid());
        SharedPreferenceUtil.getInstance().putString(WECHAT_NICKNAME, info.getNickname());
        SharedPreferenceUtil.getInstance().putString(WECHAT_SEX, info.getSex());
        SharedPreferenceUtil.getInstance().putString(WECHAT_PROVINCE, info.getProvince());
        SharedPreferenceUtil.getInstance().putString(WECHAT_CITY, info.getCity());
        SharedPreferenceUtil.getInstance().putString(WECHAT_COUNTRY, info.getCountry());
        SharedPreferenceUtil.getInstance().putString(WECHAT_HEADIMGURL, info.getHeadimgurl());
        SharedPreferenceUtil.getInstance().putString(WECHAT_UNIONID, info.getUnionid());
    }
}
