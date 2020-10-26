package com.fengyongge.wanandroidclient.mvp.contract

import com.fengyongge.baselib.mvp.IBasePresenter
import com.fengyongge.baselib.mvp.IBaseView
import com.fengyongge.rxhttp.bean.BaseResponse
import com.fengyongge.rxhttp.exception.ResponseException
import com.fengyongge.wanandroidclient.bean.HotKeyBeanItem
import com.fengyongge.wanandroidclient.bean.SearchContentBean
import io.reactivex.Observable


interface SearchContact  {

    interface Presenter : IBasePresenter {
        fun postCollect(id: Int)
        fun postCancleCollect(id: Int)
        fun getHotKey()
        fun postSearchContent(pageNum: Int,searchContent: String)
    }

    interface Model {
        fun postCollect(id: Int): Observable<BaseResponse<String>>
        fun postCancleCollect(id: Int): Observable<BaseResponse<String>>
        fun getHotKey(): Observable<BaseResponse<List<HotKeyBeanItem>>>
        fun postSearchContent(pageNum: Int,searchContent: String): Observable<BaseResponse<SearchContentBean>>
    }

    interface View : IBaseView {
        fun postCollectShow(data: BaseResponse<String>)
        fun postCancleCollectShow(data: BaseResponse<String>)
        fun getHotKeyShow(data: BaseResponse<List<HotKeyBeanItem>>)
        fun postSearchContentShow(data: BaseResponse<SearchContentBean>)
        fun onError(data: ResponseException)

    }
}