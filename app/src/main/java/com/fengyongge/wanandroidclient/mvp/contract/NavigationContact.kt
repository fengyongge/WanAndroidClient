package com.fengyongge.wanandroidclient.mvp.contract

import com.fengyongge.baselib.mvp.IBasePresenter
import com.fengyongge.baselib.mvp.IBaseView
import com.fengyongge.baselib.net.BaseResponse
import com.fengyongge.baselib.net.exception.ResponseException
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