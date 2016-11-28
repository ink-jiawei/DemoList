package com.inkhjw.personalcommunity.framework.retrofit;

import com.inkhjw.personalcommunity.base.BaseApplication;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author hjw
 * @deprecated
 */

public enum OkHttpClientManager {
    INSTANCE;

    private final OkHttpClient okHttpClient;

    private static final int TIMEOUT_READ = 25;
    private static final int TIMEOUT_CONNECTION = 25;

    OkHttpClientManager() {
        //打印请求Log
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //缓存目录
        Cache cache = new Cache(BaseApplication.baseContext.getCacheDir(), 10 * 1024 * 1024);

        okHttpClient = new OkHttpClient.Builder()
                //打印请求log
                .addInterceptor(interceptor)
                //必须是设置Cache目录
                .cache(cache)
                //走缓存，两个都要设置
                .addInterceptor(new CacheInterceptor())
                .addNetworkInterceptor(new CacheInterceptor())
                //失败重连
                .retryOnConnectionFailure(true)
                //time out
                .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                .build();
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}
