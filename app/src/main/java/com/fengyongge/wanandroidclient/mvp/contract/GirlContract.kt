package com.fengyongge.wanandroidclient.mvp.contract

import com.fengyongge.baselib.mvp.IBasePresenter
import com.fengyongge.baselib.mvp.IBaseView
import com.fengyongge.rxhttp.exception.ResponseException
import com.fengyongge.wanandroidclient.bean.GankGirlBean
import io.reactivex.Observable

class GirlContract {

    interface presenter : IBasePresenter{

        fun getGankGirl(pageNum: Int,pageSize: Int)
    }

    interface model {

        fun getGankGirl(pageNum: Int,pageSize: Int): Observable<GankGirlBean>

    }

    interface view : IBaseView{
        fun getGankGirlShow(data: GankGirlBean)
        fun getGankGirlFail(data: ResponseException)
        fun onError(data: ResponseException)

    }


}