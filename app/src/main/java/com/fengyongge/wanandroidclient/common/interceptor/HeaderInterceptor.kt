package com.fengyongge.wanandroidclient.common.interceptor

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response

/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class HeaderInterceptor: Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var oldHttpUrl = request.url
        var builder = request.newBuilder()
        var headerValues = request.header("base_url")

        headerValues?.let {
            if(it.length!! >0){
                builder.removeHeader("base_url");
                //匹配获得新的BaseUrl
                var headerValue = headerValues;
                var newBaseUrl: HttpUrl?
                newBaseUrl = if ("gank" == headerValue) {
                    "https://gank.io/".toHttpUrlOrNull()
                }else if("kaiyanapp" == headerValue){
                    "http://baobab.kaiyanapp.com/".toHttpUrlOrNull()
                }else{
                    oldHttpUrl
                }
                var newFullUrl = oldHttpUrl
                    .newBuilder()
                    .scheme(newBaseUrl?.scheme.toString())//http协议如：http或者https
                    .host(newBaseUrl?.host.toString())//主机地址
                    .port(newBaseUrl?.port!!)//端口
//                    .removePathSegment(0)//移除第一个参数
                    .build()
                return chain.proceed(builder.url(newFullUrl).build());
            }
        }
        return chain.proceed(request);
    }

}