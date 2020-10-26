package com.fengyongge.wanandroidclient.common.interceptor

import android.text.TextUtils
import com.fengyongge.androidcommonutils.ktutils.SharedPreferencesUtils
import com.fengyongge.wanandroidclient.App
import com.fengyongge.wanandroidclient.constant.Const
import okhttp3.Interceptor
import okhttp3.Response

/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class AddCookiesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = chain.request().newBuilder()
        if (!TextUtils.isEmpty(
                SharedPreferencesUtils(
                    App.getContext()
                ).get(Const.COOKIE, ""))) {
            val cookies =
                SharedPreferencesUtils(App.getContext())
                    .get(Const.COOKIE, "").split("#")
            for (cookie in cookies) {
                builder.addHeader("Cookie", cookie)
            }
        }
        return chain.proceed(builder.build())
    }
}