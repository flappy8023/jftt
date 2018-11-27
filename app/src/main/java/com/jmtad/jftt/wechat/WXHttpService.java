package com.jmtad.jftt.wechat;

import com.jmtad.jftt.wechat.model.BaseResp;
import com.jmtad.jftt.wechat.model.WXAccessTokenInfo;
import com.jmtad.jftt.wechat.model.WXUserInfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-10-16 14:32
 **/
public interface WXHttpService {
    @GET("oauth2/access_token")
    Observable<WXAccessTokenInfo> getAccessToken(@Query("appid") String appid, @Query("secret") String secret, @Query("code") String code, @Query("grant_type") String grantType);

    @GET("auth")
    Observable<BaseResp> getTokenExpire(@Query("access_token") String access_token, @Query("openid") String openId);

    @GET("userinfo")
    Observable<WXUserInfo> getUserInfo(@Query("access_token") String accessToken, @Query("openid") String openid, @Query("lang") String lang);

    @GET("oauth2/refresh_token")
    Observable<WXAccessTokenInfo> refreshAccessToken(@Query("appid") String appid, @Query("grant_type") String grant_type, @Query("refresh_token") String refresh_token);
}
