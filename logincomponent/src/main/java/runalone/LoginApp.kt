package runalone

import android.content.Context
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.fengyongge.basecomponent.app.BaseApplication
import com.fengyongge.rxhttp.core.RxHttp

class LoginApp: BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        init(this)
        RxHttp.init(applicationContext)
        ARouter.init(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}