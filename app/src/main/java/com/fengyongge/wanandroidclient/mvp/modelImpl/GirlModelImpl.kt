package com.fengyongge.wanandroidclient.mvp.modelImpl

import com.fengyongge.wanandroidclient.api.WanAndroidRetrofit
import com.fengyongge.wanandroidclient.bean.GankGirlBean
import com.fengyongge.wanandroidclient.mvp.contract.GirlContract
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GirlModelImpl : GirlContract.model {

    override fun getGankGirl(pageNum: Int, pageSize: Int): Observable<GankGirlBean> {
        return WanAndroidRetrofit.service
            .getGankGirl(pageNum,pageSize)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}