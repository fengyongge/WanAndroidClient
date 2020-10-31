package com.fengyongge.wanandroidclient

import android.app.Application
import android.content.Context
import android.os.Environment
import com.fengyongge.androidcommonutils.AndroidCommonUtils
import com.fengyongge.rxhttp.core.RxHttp
import com.fengyongge.wanandroidclient.common.db.AppDataBase
import com.fengyongge.basecomponent.constant.Const
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.tencent.smtt.sdk.QbSdk
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure

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
        initUmeng()
        initBugly()
        initTbs()
        AndroidCommonUtils.init(getAppContext)
        RxHttp.init(getAppContext)
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

    fun initUmeng() {
        UMConfigure.init(
            applicationContext,
            Const.UmengKey,
            "wanandroid",
            UMConfigure.DEVICE_TYPE_PHONE,
            ""
        )
        if (BuildConfig.DEBUG) {
            UMConfigure.setLogEnabled(true)
            //Debug模式下 不需要上传Crash
            MobclickAgent.setCatchUncaughtExceptions(false)
        } else {
            UMConfigure.setLogEnabled(false)
            MobclickAgent.setCatchUncaughtExceptions(true)
        }
    }

    private fun initBugly() {
        /***** Beta高级设置  */
        /**
         * true表示app启动自动初始化升级模块;
         * false不会自动初始化;
         * 开发者如果担心sdk初始化影响app启动速度，可以设置为false，
         * 在后面某个时刻手动调用Beta.init(getApplicationContext(),false);
         */
        Beta.autoInit = true
        /**
         * true表示初始化时自动检查升级;
         * false表示不会自动检查升级,需要手动调用Beta.checkUpgrade()方法;
         */
        Beta.autoCheckUpgrade = true
        /**
         * 设置升级检查周期为60s(默认检查周期为0s)，60s内SDK不重复向后台请求策略);
         */
        Beta.upgradeCheckPeriod = 60 * 1000.toLong()
        /**
         * 设置启动延时为1s（默认延时3s），APP启动1s后初始化SDK，避免影响APP启动速度;
         */
        Beta.initDelay = 3 * 1000.toLong()
        /**
         * 设置通知栏大图标，largeIconId为项目中的图片资源;
         */
        Beta.largeIconId = R.mipmap.ic_launcher
        /**
         * 设置状态栏小图标，smallIconId为项目中的图片资源Id;
         */
        Beta.smallIconId = R.mipmap.ic_launcher
        /**
         * 设置更新弹窗默认展示的banner，defaultBannerId为项目中的图片资源Id;
         * 当后台配置的banner拉取失败时显示此banner，默认不设置则展示“loading“;
         */
        /**
         * 设置sd卡的Download为更新资源保存目录;
         * 后续更新资源会保存在此目录，需要在manifest中添加WRITE_EXTERNAL_STORAGE权限;
         */
        Beta.storageDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        /**
         * 点击过确认的弹窗在APP下次启动自动检查更新时会再次显示;
         */
        Beta.showInterruptedStrategy = true
        /**
         * 只允许在MainActivity上显示更新弹窗，其他activity上不显示弹窗;
         * 不设置会默认所有activity都可以显示弹窗;
         */
//        Beta.canShowUpgradeActs.add(Home.class);
        /**
         * 设置自定义升级对话框UI布局
         * upgrade_dialog为项目的布局资源。 注意：因为要保持接口统一，需要用户在指定控件按照以下方式设置tag，否则会影响您的正常使用：
         * 特性图片：beta_upgrade_banner，如：android:tag="beta_upgrade_banner"
         * 标题：beta_title，如：android:tag="beta_title"
         * 升级信息：beta_upgrade_info 如： android:tag="beta_upgrade_info"
         * 更新属性：beta_upgrade_feature 如： android:tag="beta_upgrade_feature"
         * 取消按钮：beta_cancel_button 如：android:tag="beta_cancel_button"
         * 确定按钮：beta_confirm_button 如：android:tag="beta_confirm_button"
         */
        Beta.upgradeDialogLayoutId = R.layout.dialog_customer_update
        Bugly.init(applicationContext, Const.BUGLY_APP_ID, false)
    }

    private fun initTbs(){
        QbSdk.initX5Environment(this,object :QbSdk.PreInitCallback{
            override fun onCoreInitFinished() {
            }

            override fun onViewInitFinished(p0: Boolean) {
            }

        })
    }


}