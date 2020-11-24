package com.fengyongge.login.api

import com.fengyongge.basecomponent.constant.Const
import com.fengyongge.basecomponent.interceptor.AddCookiesInterceptor
import com.fengyongge.basecomponent.interceptor.HeaderInterceptor
import com.fengyongge.basecomponent.interceptor.SaveCookiesInterceptor
import com.fengyongge.rxhttp.core.RetrofitFactory
import okhttp3.Interceptor

/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
object LoginRetrofit : RetrofitFactory<LoginApi>() {

    override fun baseUrl(): String {
        return Const.BASE_URL
    }

    override fun getService(): Class<LoginApi> {
        return LoginApi::class.java
    }

    override fun getInterceptorList(): MutableList<Interceptor> {
        var interceptorList = mutableListOf<Interceptor>()
        interceptorList.add(SaveCookiesInterceptor())
        interceptorList.add(AddCookiesInterceptor())
        interceptorList.add(HeaderInterceptor())
        return interceptorList
    }


}