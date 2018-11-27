package com.jmtad.jftt.wechat;

import com.jmtad.jftt.App;
import com.jmtad.jftt.http.HttpsUtil;
import com.jmtad.jftt.http.listener.HttpIntercepterLog;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @description:
 * @author: luweiming
 * @create: 2018-10-16 14:29
 **/
public class WXHttpApi {
    /**
     * 超时时间
     */
    private static final int DEFAULT_TIMEOUT = 20000;

    private static WXHttpApi mHttpApi;

    public WXHttpService service;

    private WXHttpApi() {
        createHttpApiService(createOkHttp());
    }

    public static synchronized WXHttpApi getInstance() {
        if (null == mHttpApi) {
            mHttpApi = new WXHttpApi();
        }
        return mHttpApi;
    }

    private OkHttpClient createOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.addInterceptor(new HttpIntercepterLog());
        //https
        HttpsUtil.SSLParams sslParams = HttpsUtil.getSslSocketFactory(App.getContext(), null, 0, null);
        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        builder.hostnameVerifier(HttpsUtil.getHostnameVerifier());
        return builder.build();
    }

    private void createHttpApiService(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://api.weixin.qq.com/sns/")
                .build();
        service = retrofit.create(WXHttpService.class);
    }
}
