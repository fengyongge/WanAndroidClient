package runalone

import android.content.Context
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.fengyongge.basecomponent.app.BaseApplication
import com.fengyongge.rxhttp.core.RxHttp
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration

class App: BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        init(this)
        RxHttp.init(applicationContext)
        ARouter.init(this)
        initImageload()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
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