package com.fengyongge.basecomponent.interceptor

import com.fengyongge.androidcommonutils.ktutils.SharedPreferencesUtils
import com.fengyongge.basecomponent.app.BaseApplication
import com.fengyongge.basecomponent.constant.Const
import okhttp3.Interceptor
import okhttp3.Response
import java.lang.StringBuilder
/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class SaveCookiesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        val request = chain.request()
        if (request.url.toString().contains("login") || request.url.toString().contains("register")) {
            if (originalResponse.headers("Set-Cookie").isNotEmpty()) {
                val cookies = originalResponse.headers("Set-Cookie")
                if (cookies.isNotEmpty()) {
                    val cookieStr = StringBuilder()
                    for (cookie in cookies) {
                        cookieStr.append(cookie)
                        cookieStr.append("#")
                    }
                    SharedPreferencesUtils(BaseApplication.getAppContext())
                        .put(Const.COOKIE,cookieStr.toString())
                }
            }
        }

        return originalResponse
    }
}