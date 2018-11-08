package com.jmtad.jftt.http;


import com.jmtad.jftt.http.listener.HttpIntercepterLog;
import com.jmtad.jftt.util.LogUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


public abstract class BaseHttpManager {

    private static final String TAG = "BaseHttpManager";

    /*基础url*///"http://www.izaodao.com/Api/"
    public static final String BASE_URL = "";

    /*超时设置 s*/
    public static final int DEFAULT_TIMEOUT = 20000;

    protected HttpService httpService;


    /**
     * @param url     可定义URL
     * @param factory 解析方法 如：GsonConverterFactory.create()
     *                SimpleXmlConverterFactory.create()
     */
    protected BaseHttpManager(String url, Converter.Factory factory) {

        LogUtil.debug("BaseHttpManager()");

        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        HttpIntercepterLog mHttpIntercepterLog = new HttpIntercepterLog();
        /**
         * 设置消息体的日志
         */
        builder.addInterceptor(mHttpIntercepterLog);

        /**
         * 禁制OkHttp的重定向操作，我们自己处理重定向
         */
//        builder.followRedirects(false);

        /**
         * 新Https认证
         */
//        HttpsUtil.SSLParams sslParams = HttpsUtil.getSslSocketFactory(App.getContext(), null, 0, null);
//        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        builder.hostnameVerifier(HttpsUtil.getHostnameVerifier());

        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(factory == null ? createConverterFactory() : factory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url == null ? getBaseUrl() : url)
                .build();
        httpService = retrofit.create(HttpService.class);
    }


    abstract Converter.Factory createConverterFactory();

    abstract String getBaseUrl();
}
