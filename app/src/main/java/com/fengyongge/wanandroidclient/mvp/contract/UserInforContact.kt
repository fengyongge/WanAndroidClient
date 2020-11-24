package com.fengyongge.wanandroidclient.mvp.contract

import com.fengyongge.baseframework.mvp.IBasePresenter
import com.fengyongge.baseframework.mvp.IBaseView
import com.fengyongge.rxhttp.bean.BaseResponse
import com.fengyongge.rxhttp.exception.ResponseException
import com.fengyongge.wanandroidclient.bean.UserInforBean
import io.reactivex.Observable

class UserInforContact {

    interface Presenter : IBasePresenter {

        fun getAccount()

    }

    interface Model{

        fun getAccount(): Observable<BaseResponse<UserInforBean>>

    }

    interface View : IBaseView {

        fun getAccountShow(data: BaseResponse<UserInforBean>)
        fun onError(data: ResponseException)

    }
}