package com.fengyongge.baseframework.mvp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fengyongge.baseframework.BaseFragment
/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
abstract class BaseMvpFragment<P : IBasePresenter> : BaseFragment(), IBaseView {

    protected var mPresenter: P? = null

    abstract fun initPresenter(): P

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mPresenter = initPresenter()
        mPresenter?.attach(this)

        return super.onCreateView(inflater, container, savedInstanceState)
    }



    override fun onDestroy() {
        mPresenter?.detech()
        mPresenter = null
        super.onDestroy()
    }

    override fun getCurrentView(): Context? {
        return activity
    }


}