package com.fengyongge.wanandroidclient.mvp.contract

import com.fengyongge.baselib.mvp.IBasePresenter
import com.fengyongge.baselib.mvp.IBaseView
import com.fengyongge.rxhttp.bean.BaseResponse
import com.fengyongge.rxhttp.exception.ResponseException
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
        fun getCollectListFail(data: ResponseException)
        fun postCancleCollectShow(data: BaseResponse<String>)
        fun postCancleCollectFail(data: ResponseException)
    }
}