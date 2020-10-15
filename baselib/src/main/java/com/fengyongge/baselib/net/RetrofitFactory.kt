package com.fengyongge.baselib.net

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
abstract class RetrofitFactory<T> {

    var service: T
    private var mBaseUrl = ""

    private var okHttpClient: OkHttpClient

    abstract fun baseUrl(): String

    abstract fun getService(): Class<T>

    abstract fun getInterceptorList(): MutableList<Interceptor>


    init {
        mBaseUrl = this.baseUrl()
        if (mBaseUrl.isEmpty()) {
            throw RuntimeException("base url can not be empty")
        }
        okHttpClient =  getOkHttpClient(true)
        service = getRetrofit()!!.create(getService())
    }


    private fun getRetrofit(): Retrofit? {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(mBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun getOkHttpClient(flag: Boolean): OkHttpClient {
        val builder = OkHttpClient.Builder().apply {
            connectTimeout(10, TimeUnit.SECONDS)
            writeTimeout(10, TimeUnit.SECONDS)
            readTimeout(10, TimeUnit.SECONDS)

            if (flag) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(loggingInterceptor)
            }
            if(getInterceptorList().isNotEmpty()){
                for(index in getInterceptorList().indices){
                    addInterceptor(getInterceptorList()[index])
                }
            }
        }

        return builder.build()
    }
}