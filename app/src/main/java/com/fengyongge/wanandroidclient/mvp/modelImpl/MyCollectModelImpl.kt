package com.fengyongge.wanandroidclient.mvp.modelImpl

import com.fengyongge.rxhttp.bean.BaseResponse
import com.fengyongge.wanandroidclient.api.WanAndroidRetrofit
import com.fengyongge.wanandroidclient.bean.MyCollectBean
import com.fengyongge.wanandroidclient.mvp.contract.MyCollectContact
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MyCollectModelImpl : MyCollectContact.Model {


    override fun getCollectList(pageNum: Int): Observable<BaseResponse<MyCollectBean>> {
        return WanAndroidRetrofit.service
            .getCollectList(pageNum)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun postCancleCollect(id: String,originId: Int): Observable<BaseResponse<String>> {
        return WanAndroidRetrofit.service
            .postCancleMyCollect(id,originId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}