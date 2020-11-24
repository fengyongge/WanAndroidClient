package com.fengyongge.gank.mvp.model

import com.fengyongge.gank.api.GankRetrofit
import com.fengyongge.gank.bean.GankGirlBean
import com.fengyongge.gank.mvp.contract.GirlContract
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GirlModelImpl : GirlContract.model {

    override fun getGankGirl(pageNum: Int, pageSize: Int): Observable<GankGirlBean> {
        return GankRetrofit.service
            .getGankGirl(pageNum,pageSize)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}