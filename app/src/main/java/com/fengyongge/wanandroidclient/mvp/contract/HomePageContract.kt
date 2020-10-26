package com.fengyongge.wanandroidclient.mvp.contract

import com.fengyongge.baselib.mvp.IBasePresenter
import com.fengyongge.baselib.mvp.IBaseView
import com.fengyongge.rxhttp.bean.BaseResponse
import com.fengyongge.rxhttp.exception.ResponseException
import com.fengyongge.wanandroidclient.bean.ArticleBean
import com.fengyongge.wanandroidclient.bean.BannerBean
import com.fengyongge.wanandroidclient.bean.DataX
import io.reactivex.Observable

interface HomePageContract {

    interface Presenter : IBasePresenter {
        fun bannerList()
        fun stickArticle()
        fun articleList(pageNum: Int)
        fun projectList(pageNum: Int)
        fun postCollect(id: Int)
        fun postCancleCollect(id: Int)
        fun onAllFail()
    }

    interface Model {
        fun bannerList(): Observable<BaseResponse<List<BannerBean>>>
        fun stickArticle(): Observable<BaseResponse<List<DataX>>>
        fun articleList(pageNum: Int): Observable<BaseResponse<ArticleBean>>
        fun projectList(pageNum: Int): Observable<BaseResponse<ArticleBean>>
        fun postCollect(id: Int): Observable<BaseResponse<String>>
        fun postCancleCollect(id: Int): Observable<BaseResponse<String>>
    }

    interface View : IBaseView {
        fun bannerListShow(data: BaseResponse<List<BannerBean>>)
        fun bannerListFail(data: ResponseException)
        fun articleListShow(data: BaseResponse<ArticleBean>)
        fun articleListFail(data: ResponseException)
        fun projectListShow(data: BaseResponse<ArticleBean>)
        fun projectListFail(data: ResponseException)

        fun stickArticleShow(data: BaseResponse<List<DataX>>)
        fun postCollectShow(data: BaseResponse<String>)
        fun postCancleCollectShow(data: BaseResponse<String>)
        fun onError(data: ResponseException)
        fun onAllFail()
    }

}