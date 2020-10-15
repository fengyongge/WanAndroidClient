package com.fengyongge.wanandroidclient.mvp.contract

import com.fengyongge.baselib.mvp.IBasePresenter
import com.fengyongge.baselib.mvp.IBaseView
import com.fengyongge.baselib.net.BaseResponse
import com.fengyongge.baselib.net.exception.ResponseException
import com.fengyongge.wanandroidclient.bean.WxAccountBeanItem
import com.fengyongge.wanandroidclient.bean.WxAccountSearchBean
import com.fengyongge.wanandroidclient.bean.WxHistoryBean
import io.reactivex.Observable

class WxAccountContact {
    interface Presenter : IBasePresenter{
        fun postCollect(id: Int)
        fun postCancleCollect(id: Int)
        fun getWxAccount()
        fun getSearchWxContent(id: Int,pageNum: Int,content: String)
        fun getWxHistoryList(id: String,pageNum: Int)
    }

    interface Model{
        fun postCollect(id: Int): Observable<BaseResponse<String>>
        fun postCancleCollect(id: Int): Observable<BaseResponse<String>>
        fun getWxAccount(): Observable<BaseResponse<List<WxAccountBeanItem>>>
        fun getSearchWxContent(id: Int,pageNum: Int,content: String): Observable<BaseResponse<WxAccountSearchBean>>
        fun getWxHistoryList(id: String,pageNum: Int): Observable<BaseResponse<WxHistoryBean>>

    }

    interface View : IBaseView{
        fun getSearchWxContentShow(data: BaseResponse<WxAccountSearchBean>)
        fun postCollectShow(data: BaseResponse<String>)
        fun postCancleCollectShow(data: BaseResponse<String>)
        fun getWxAccountShow(data: BaseResponse<List<WxAccountBeanItem>>)
        fun getWxHistoryList(data: BaseResponse<WxHistoryBean>)
        fun onError(e: ResponseException)
    }

}