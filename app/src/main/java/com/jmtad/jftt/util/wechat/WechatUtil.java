package com.jmtad.jftt.util.wechat;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;

import com.jmtad.jftt.App;
import com.jmtad.jftt.event.WXLoginEvent;
import com.jmtad.jftt.http.RxCallBack;
import com.jmtad.jftt.util.LogUtil;
import com.jmtad.jftt.wechat.WXHttpApi;
import com.jmtad.jftt.wechat.WechatInfoHelper;
import com.jmtad.jftt.wechat.model.BaseResp;
import com.jmtad.jftt.wechat.model.WXAccessTokenInfo;
import com.jmtad.jftt.wechat.model.WXUserInfo;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @description:
 * @author: flappy8023
 * @create: 2018-10-16 10:19
 **/
public class WechatUtil {
    private static final String TAG = "WechatUtil";
    private static IWXAPI mApi;
    private final String APP_ID = "wxa6be0a9e6160a6ed";
    private final String APP_SECRET = "1d11be2f8cf2ee681f0836436f5f5dba";

    private static WechatUtil instance;

    private WechatUtil() {
    }

    public static WechatUtil getInstance() {
        if (null == instance) {
            synchronized (WechatUtil.class) {
                if (null == instance) {
                    instance = new WechatUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 向微信注册应用
     */
    public void register() {
        mApi = WXAPIFactory.createWXAPI(App.getContext(), APP_ID, true);
        mApi.registerApp(APP_ID);
    }

    public IWXAPI getApi() {
        return mApi;
    }

    public void authorizeByWechat() {
        LogUtil.debug("authorizeByWechat: ");

        if (!isAvailable()) {
//            mWechatObservable.sendStateChange(WECHAT_CHECK_FAILURE);
            return;
        }

        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "app_wechat";
        mApi.sendReq(req);
    }

    private boolean isAvailable() {
        return mApi.isWXAppInstalled();
    }

    public void checkAccessToken(String code) {
        // 从手机本地获取存储的授权口令信息，判断是否存在access_token，不存在请求获取，存在就判断是否过期
        String accessToken = WechatInfoHelper.getWechatAccessToken();
        String openId = WechatInfoHelper.getWechatOpenid();
        if (!TextUtils.isEmpty(accessToken) && !TextUtils.isEmpty(openId)) {
            // 有access_token，判断是否过期有效
            isExpireAccessToken(accessToken, openId);
        } else {
            getTokenFromCode(code);
        }
    }

    private void getTokenFromCode(String code) {
        if (TextUtils.isEmpty(code)) {
            return;
        }
        WXHttpApi.getInstance().service.getAccessToken(APP_ID, APP_SECRET, code, "authorization_code").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new RxCallBack<WXAccessTokenInfo>() {
                    @Override
                    public void onSuccess(WXAccessTokenInfo tokenInfo) {
                        LogUtil.debug("获取token   " + tokenInfo.toString());
                        if (null != tokenInfo) {
                            WechatInfoHelper.saveWXAccessTokenInfo(tokenInfo);
                            getUserInfo(tokenInfo.getAccess_token(), tokenInfo.getOpenid());
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {

                    }
                });
    }

    /**
     * 验证token是否失效
     *
     * @param accessToken
     * @param openId
     */
    private void isExpireAccessToken(String accessToken, String openId) {
        WXHttpApi.getInstance().service.getTokenExpire(accessToken, openId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new RxCallBack<BaseResp>() {
            @Override
            public void onSuccess(BaseResp resp) {
                LogUtil.debug("验证token       ," + resp);
                //未失效获取用户信息
                if (TextUtils.equals(BaseResp.CODE_OK, resp.getErrcode())) {
                    getUserInfo(accessToken, openId);
                } else {
                    // 过期了，使用refresh_token来刷新accesstoken
                    refreshAccessToken();
                }
            }

            @Override
            public void onFail(Throwable e) {
                LogUtil.error(e.getLocalizedMessage());
            }
        });
    }

    private void refreshAccessToken() {
        // 从本地获取存储的refresh_token
        final String refreshToken = WechatInfoHelper.getWechatRefreshToken();

        if (refreshToken == null) {
            return;
        }

        // 发起网络请求 刷新token
        WXHttpApi.getInstance().service.refreshAccessToken(APP_ID, "refresh_token", refreshToken).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new RxCallBack<WXAccessTokenInfo>() {
            @Override
            public void onSuccess(WXAccessTokenInfo tokenInfo) {
                LogUtil.debug(tokenInfo.toString());
                if (null == tokenInfo) {

                } else {
                    WechatInfoHelper.saveWXAccessTokenInfo(tokenInfo);
                    getUserInfo(tokenInfo.getAccess_token(), tokenInfo.getOpenid());
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });

    }

    private void getUserInfo(String accessToken, String openId) {
        if (TextUtils.isEmpty(accessToken) || TextUtils.isEmpty(openId)) {
            return;
        }
        WXHttpApi.getInstance().service.getUserInfo(accessToken, openId, "zh-CN").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new RxCallBack<WXUserInfo>() {
            @Override
            public void onSuccess(WXUserInfo info) {
                LogUtil.debug(info.toString());
                if (null != info) {
                    EventBus.getDefault().post(new WXLoginEvent(info));
                    WechatInfoHelper.saveWXUserInfo(info);
                }
            }

            @Override
            public void onFail(Throwable e) {
                LogUtil.error(e.getLocalizedMessage());
            }
        });
    }

    private boolean validateSuccess(String response) {
        Log.i(TAG, "validateSuccess: " + response);

        if (response.contains("errcode") && response.contains("errmsg")) {
            int code = 66666;

            try {
                JSONObject obj = new JSONObject(response);
                code = obj.getInt("errcode");

                if (code != 66666) {
                    return false;
                }

            } catch (JSONException e) {
                e.printStackTrace();
                return true;
            }
        }

        return true;
    }

    public void shareImg(Bitmap bitmap, int sence) {
        WXImageObject imageObject = new WXImageObject(bitmap);
        WXMediaMessage mediaMessage = new WXMediaMessage();
        mediaMessage.mediaObject = imageObject;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = System.currentTimeMillis() + "img";
        req.message = mediaMessage;
        req.scene = sence;
        mApi.sendReq(req);
    }
}
