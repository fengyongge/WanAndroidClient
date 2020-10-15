package com.fengyongge.baselib

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
abstract class BaseActivity : AppCompatActivity() {
    private lateinit var mContext: BaseActivity

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
}