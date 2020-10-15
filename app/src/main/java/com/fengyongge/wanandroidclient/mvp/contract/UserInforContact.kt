package com.fengyongge.wanandroidclient.mvp.contract

import com.fengyongge.baselib.mvp.IBasePresenter
import com.fengyongge.baselib.mvp.IBaseView
import com.fengyongge.baselib.net.BaseResponse
import com.fengyongge.baselib.net.exception.ResponseException
import com.fengyongge.wanandroidclient.bean.UserInforBean
import io.reactivex.Observable

class UserInforContact {

    interface Presenter : IBasePresenter {

        fun getAccount()
        fun getLogout()

    }

    interface Model{

        fun getAccount(): Observable<BaseResponse<UserInforBean>>
        fun getLogout(): Observable<BaseResponse<String>>

    }

    interface View : IBaseView {

        fun getLogoutShow(data: BaseResponse<String>)
        fun getAccountShow(data: BaseResponse<UserInforBean>)
        fun onError(data: ResponseException)

    }
}