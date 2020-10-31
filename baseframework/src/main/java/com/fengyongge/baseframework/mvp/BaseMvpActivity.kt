package com.fengyongge.baseframework.mvp

import android.content.Context
import android.os.Bundle
import com.fengyongge.baseframework.BaseActivity

/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
abstract class BaseMvpActivity<P : IBasePresenter> : BaseActivity(),IBaseView{

     var mPresenter: P ?= null
     abstract fun initPresenter(): P


    override fun onCreate(savedInstanceState: Bundle?) {
        mPresenter = initPresenter()
        mPresenter!!.attach(this)
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        mPresenter!!.detech()
        mPresenter = null
        super.onDestroy()
    }

    override fun getCurrentView(): Context? {
        return this
    }



}