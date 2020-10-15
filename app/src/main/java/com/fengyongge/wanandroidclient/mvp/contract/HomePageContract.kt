package com.fengyongge.wanandroidclient.mvp.contract

import com.fengyongge.baselib.mvp.IBasePresenter
import com.fengyongge.baselib.mvp.IBaseView
import com.fengyongge.baselib.net.BaseResponse
import com.fengyongge.baselib.net.exception.ResponseException
import com.fengyongge.wanandroidclient.bean.ArticleBean
import com.fengyongge.wanandroidclient.bean.BannerBean
import io.reactivex.Observable

interface HomePageContract {

    interface Presenter : IBasePresenter {
        fun bannerList()
        fun articleList(pageNum: Int)
        fun projectList(pageNum: Int)
        fun postCollect(id: Int)
        fun postCancleCollect(id: Int)
    }

    interface Model {
        fun bannerList(): Observable<BaseResponse<List<BannerBean>>>
        fun articleList(pageNum: Int): Observable<BaseResponse<ArticleBean>>
        fun projectList(pageNum: Int): Observable<BaseResponse<ArticleBean>>
        fun postCollect(id: Int): Observable<BaseResponse<String>>
        fun postCancleCollect(id: Int): Observable<BaseResponse<String>>
    }

    interface View : IBaseView {
        fun bannerListShow(data: BaseResponse<List<BannerBean>>)
        fun articleListShow(data: BaseResponse<ArticleBean>)
        fun projectListShow(data: BaseResponse<ArticleBean>)
        fun onError(data: ResponseException)
        fun postCollectShow(data: BaseResponse<String>)
        fun postCancleCollectShow(data: BaseResponse<String>)
    }

}