package com.fengyongge.wanandroidclient.mvp.modelImpl

import com.fengyongge.baselib.net.BaseResponse
import com.fengyongge.wanandroidclient.api.WanAndroidRetrofit
import com.fengyongge.wanandroidclient.mvp.contract.ShareContract
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ShareModelImpl : ShareContract.Model{
    override fun postShare(title: String, link: String): Observable<BaseResponse<String>> {
        return WanAndroidRetrofit.service
            .postShare(title,link)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())    }

    override fun getShareList(pageNum: Int): Observable<BaseResponse<String>> {
        return WanAndroidRetrofit.service
            .getShareList(pageNum)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())    }

    override fun postDeleteMyShare(id: Int): Observable<BaseResponse<String>> {
        return WanAndroidRetrofit.service
            .postDeleteMyShare(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}

