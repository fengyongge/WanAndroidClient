package com.fengyongge.wanandroidclient.mvp.contract

import com.fengyongge.baselib.mvp.IBasePresenter
import com.fengyongge.baselib.mvp.IBaseView
import com.fengyongge.baselib.net.BaseResponse
import com.fengyongge.baselib.net.exception.ResponseException
import com.fengyongge.wanandroidclient.bean.MyCollectBean
import io.reactivex.Observable

class MyCollectContact {

    interface Presenter : IBasePresenter {
        fun getCollectList(pageNum: Int)
        fun postCancleCollect(id: String,originId: Int)
    }

    interface Model{

        fun getCollectList(pageNum: Int): Observable<BaseResponse<MyCollectBean>>
        fun postCancleCollect(id: String,originId: Int): Observable<BaseResponse<String>>
    }

    interface View : IBaseView {

        fun getCollectListShow(data: BaseResponse<MyCollectBean>)
        fun postCancleCollectShow(data: BaseResponse<String>)
        fun onError(data: ResponseException)
    }
}