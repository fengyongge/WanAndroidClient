package com.fengyongge.wanandroidclient.mvp.contract

import com.fengyongge.baseframework.mvp.IBasePresenter
import com.fengyongge.baseframework.mvp.IBaseView
import com.fengyongge.rxhttp.bean.BaseResponse
import com.fengyongge.rxhttp.exception.ResponseException
import com.fengyongge.wanandroidclient.bean.NavigationBean
import io.reactivex.Observable

class NavigationContact {

    interface Presenter : IBasePresenter {
        fun getNavigation()
    }

    interface Model{
        fun getNavigation(): Observable<BaseResponse<List<NavigationBean>>>
    }

    interface View : IBaseView {
        fun getNavigationShow(data: BaseResponse<List<NavigationBean>>)
        fun onError(data: ResponseException)
    }
}