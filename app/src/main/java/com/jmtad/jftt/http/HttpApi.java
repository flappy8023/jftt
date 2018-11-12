package com.jmtad.jftt.http;

import com.jmtad.jftt.App;
import com.jmtad.jftt.http.listener.HttpIntercepterLog;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * HttpApi
 *
 * @version : 1.0
 * @Title: HttpApi
 * @Package com.pukka.ydepg.common.http
 * @date 2018/01/11 17:59
 */
public class HttpApi {
    //    public final String BASE_URL = "http://tdev.juxinbox.com/integral_headline/";//测试环境
    public final String BASE_URL = "https://game.juxinbox.com/integral_headline/";//正式环境
    /**
     * 超时时间
     */
    private static final int DEFAULT_TIMEOUT = 10;

    private static HttpApi mHttpApi;

    public HttpService service;

    private HttpApi() {
        createHttpApiService(createOkHttp());
    }

    public static synchronized HttpApi getInstance() {
        if (null == mHttpApi) {
            mHttpApi = new HttpApi();
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
                .baseUrl(BASE_URL)
                .build();
        service = retrofit.create(HttpService.class);
    }
}