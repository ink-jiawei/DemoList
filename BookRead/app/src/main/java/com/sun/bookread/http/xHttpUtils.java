package com.sun.bookread.http;

import android.content.Context;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;

/**
 * author:何佳伟
 * HttpUtils的封装类
 */
public class xHttpUtils {
    /**
     * 发送http请求,自动实现异步处理
     *
     * @param url            请求的地址
     * @param params         请求的参数
     * @param iOAuthCallBack 数据回调接口
     */
    public static void sendRequest(final Context context,
                                   final HttpRequest.HttpMethod method, String url, RequestParams params,
                                   final IOAuthCallBack iOAuthCallBack) {

        HttpUtils http = new HttpUtils();

        http.configCurrentHttpCacheExpiry(1000 * 5);
        // 设置超时时间
        http.configTimeout(5 * 1000);
        http.configSoTimeout(5 * 1000);

        if (method == HttpRequest.HttpMethod.GET) {
            http.configCurrentHttpCacheExpiry(0); // 设置缓存5秒,5秒内直接返回上次成功请求的结果。
        }

        http.send(method, url, params,
                new RequestCallBack<String>() {

                    @Override
                    public void onStart() {
                        LogUtils.d(method.name() + " request is onStart.......");
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        LogUtils.d("statusCode:" + responseInfo.statusCode + " ----->" + responseInfo.result);
                        iOAuthCallBack.getIOAuthCallBack(responseInfo.result);// 利用接口回调数据传输
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        LogUtils.d("statusCode:" + error.getExceptionCode() + " -----> " + msg);
                        iOAuthCallBack.getIOAuthCallBack("FF");// 利用接口回调数据传输
                    }
                });
    }
}
