package com.fengyongge.baselib

import android.app.Activity
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.umeng.analytics.MobclickAgent
import kotlin.system.exitProcess

/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
abstract class BaseActivity : AppCompatActivity() {
    private lateinit var mContext: BaseActivity
    var activitys = mutableListOf<Activity>()


    @LayoutRes
    abstract fun initLayout(): Int
    abstract fun initView()
    abstract fun initData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(initLayout())
        mContext = this
        initData()
        initView()
    }

    override fun onResume() {
        super.onResume()
        activitys?.let {
            it.add(this)
        }
        MobclickAgent.onResume(this)
    }
    override fun onPause() {
        super.onPause()
        MobclickAgent.onPause(this)
    }

    open fun exitApp() {
        if (activitys != null) {
            for (activity in activitys) {
                activity?.finish()
            }
        }
        exitProcess(0)
    }



}