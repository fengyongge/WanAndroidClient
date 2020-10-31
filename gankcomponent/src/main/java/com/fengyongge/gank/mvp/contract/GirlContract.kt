package com.fengyongge.gank.mvp.contract

import com.fengyongge.baseframework.mvp.IBasePresenter
import com.fengyongge.baseframework.mvp.IBaseView
import com.fengyongge.gank.bean.GankGirlBean
import com.fengyongge.rxhttp.exception.ResponseException
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