package com.inkhjw.personalcommunity.framework.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author hjw
 * @deprecated
 */

public enum RetrofitClient {
    INSTANCE;

    private final Retrofit retrofit;

    RetrofitClient() {
        retrofit = new Retrofit.Builder()
                //设置OKHttpClient
                .client(OkHttpClientManager.INSTANCE.getOkHttpClient())
                //baseUrl
                .baseUrl(ApiInterface.BASE_URL)
                //gson转化器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
