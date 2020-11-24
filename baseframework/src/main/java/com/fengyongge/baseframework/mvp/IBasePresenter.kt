package com.fengyongge.baseframework.mvp

/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
interface IBasePresenter {

    open fun attach(V : IBaseView)

    open fun detech()

}