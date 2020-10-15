package com.fengyongge.wanandroidclient.mvp.contract

import com.fengyongge.baselib.mvp.IBasePresenter
import com.fengyongge.baselib.mvp.IBaseView
import com.fengyongge.baselib.net.BaseResponse
import com.fengyongge.baselib.net.exception.ResponseException
import com.fengyongge.wanandroidclient.bean.SystemArticleBean
import com.fengyongge.wanandroidclient.bean.SystemCategoryBean
import io.reactivex.Observable

interface SystemContact {
    interface Presenter : IBasePresenter {
        fun postCollect(id: Int)
        fun postCancleCollect(id: Int)
        fun getSystemCategory()
        fun getSystemArticle(pageNum: Int,cid: Int)
    }

    interface Model{
        fun postCollect(id: Int): Observable<BaseResponse<String>>
        fun postCancleCollect(id: Int): Observable<BaseResponse<String>>
        fun getSystemCategory(): Observable<BaseResponse<List<SystemCategoryBean>>>
        fun getSystemArticle(pageNum: Int,cid: Int): Observable<BaseResponse<SystemArticleBean>>
    }

    interface View : IBaseView {
        fun postCollectShow(data: BaseResponse<String>)
        fun postCancleCollectShow(data: BaseResponse<String>)
        fun getSystemCategoryShow(data: BaseResponse<List<SystemCategoryBean>>)
        fun getSystemArticleShow(data: BaseResponse<SystemArticleBean>)
        fun onError(data: ResponseException)
    }
}