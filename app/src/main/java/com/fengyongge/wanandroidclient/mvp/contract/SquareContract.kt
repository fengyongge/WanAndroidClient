package com.fengyongge.wanandroidclient.mvp.contract

import com.fengyongge.baselib.mvp.IBasePresenter
import com.fengyongge.baselib.mvp.IBaseView
import com.fengyongge.baselib.net.BaseResponse
import com.fengyongge.baselib.net.exception.ResponseException
import com.fengyongge.wanandroidclient.bean.SquareBean
import io.reactivex.Observable

class SquareContract {

    interface Presenter : IBasePresenter{
        fun postCollect(id: Int)
        fun postCancleCollect(id: Int)
        fun getSquareProject(pageNum: Int)

    }

    interface Model{

        fun postCollect(id: Int): Observable<BaseResponse<String>>
        fun postCancleCollect(id: Int): Observable<BaseResponse<String>>
        fun getSquareProject(pageNum: Int): Observable<BaseResponse<SquareBean>>

    }

    interface View : IBaseView{

        fun postCollectShow(data: BaseResponse<String>)
        fun postCancleCollectShow(data: BaseResponse<String>)
        fun getSquareProjectShow(data: BaseResponse<SquareBean>)
        fun onError(data: ResponseException)

    }
}