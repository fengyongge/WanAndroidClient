package com.fengyongge.wanandroidclient.mvp.contract

import com.fengyongge.baselib.mvp.IBaseView
import com.fengyongge.rxhttp.bean.BaseResponse
import com.fengyongge.rxhttp.exception.ResponseException
import io.reactivex.Observable

class SettingContract {

    interface Presenter{
        fun getLogout()
        fun clearCache()
    }

    interface Model{
        fun getLogout(): Observable<BaseResponse<String>>
    }

    interface View : IBaseView{
        fun getLogoutSuccess(data: BaseResponse<String>)
        fun getLogoutFail(e: ResponseException)
        fun clearCacheSuccess(size: String)
    }
}