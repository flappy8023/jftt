package com.jmtad.jftt.http;

import com.jmtad.jftt.App;
import com.jmtad.jftt.http.listener.HttpIntercepterLog;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * wanandroid开发过程模拟api
 *
 * @version : 1.0
 * @Title: HttpApi
 */
public class MockApi {
    /**
     * 超时时间
     */
    private static final int DEFAULT_TIMEOUT = 10;
    private static MockApi mHttpApi;
    public final String BASE_URL = "http://wanandroid.com/tools/mockapi/3995/";
    public MockService service;

    private MockApi() {
        createHttpApiService(createOkHttp());
    }

    public static synchronized MockApi getInstance() {
        if (null == mHttpApi) {
            mHttpApi = new MockApi();
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
        service = retrofit.create(MockService.class);
    }
}