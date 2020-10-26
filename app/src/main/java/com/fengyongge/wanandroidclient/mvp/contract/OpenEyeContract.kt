package com.fengyongge.wanandroidclient.mvp.contract

import com.fengyongge.baselib.mvp.IBasePresenter
import com.fengyongge.baselib.mvp.IBaseView
import com.fengyongge.rxhttp.exception.ResponseException
import com.fengyongge.wanandroidclient.bean.openeye.OpenEyeDailyBean
import com.fengyongge.wanandroidclient.bean.openeye.OpenEyeRelateCommentBean
import com.fengyongge.wanandroidclient.bean.openeye.OpenEyeRelateVideoBean
import io.reactivex.Observable

class OpenEyeContract {

    interface Presenter : IBasePresenter{
        fun getOpenEyeDaily(date: String,num: Int)
        fun getOeRelateVideo(id: String)
        fun getOeRelateComment(videoId: String)

    }

    interface Model {
        fun getOpenEyeDaily(date: String,num: Int): Observable<OpenEyeDailyBean>
        fun getOeRelateVideo(id: String): Observable<OpenEyeRelateVideoBean>
        fun getOeRelateComment(videoId: String): Observable<OpenEyeRelateCommentBean>

    }

    interface View : IBaseView{
        fun getOpenEyeDailyShow(data: OpenEyeDailyBean)
        fun getOpenEyeDailyShowFail(data: ResponseException)
        fun getOeRelateVideoShow(data: OpenEyeRelateVideoBean)
        fun getOeRelateCommentShow(data: OpenEyeRelateCommentBean)
        fun onError(data: ResponseException)
    }


}