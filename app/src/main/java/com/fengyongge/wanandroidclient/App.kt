package com.fengyongge.wanandroidclient

import android.app.Application
import android.content.Context
import com.fengyongge.wanandroidclient.common.db.AppDataBase
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration

/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        getAppContext = this
        initImageload()
        AppDataBase.getInstance(this)
    }

    companion object {
        private lateinit var getAppContext: Application
        fun getContext(): Context {
            return getAppContext
        }
    }

    private fun initImageload(){
        val defaultOptions =
            DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisc(true).build()
        val config =
            ImageLoaderConfiguration.Builder(applicationContext)
                .defaultDisplayImageOptions(defaultOptions).build()
        ImageLoader.getInstance().init(config)
    }


}