package com.inkhjw.personalcommunity.framework.retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public final class CacheInterceptor implements Interceptor {
    private static final String USER_AGENT_HEADER_NAME = "Cache-Control";

    public CacheInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request originalRequest = chain.request();
        final Request requestWithUserAgent = originalRequest.newBuilder()

                //移除先前默认的UA
                .removeHeader(USER_AGENT_HEADER_NAME)
                //设置UA
                .addHeader(USER_AGENT_HEADER_NAME, "max-age=0")
                .build();

        return chain.proceed(requestWithUserAgent);
    }
}