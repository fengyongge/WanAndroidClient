package com.fengyongge.baseframework.mvp

/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
abstract class BasePresenter<V : IBaseView> : IBasePresenter {

    protected var mView: V? = null

    override fun attach(view: IBaseView) {
        mView = view as V
    }

    override fun detech() {
        mView = null
    }


}