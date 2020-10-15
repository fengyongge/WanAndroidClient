package com.fengyongge.wanandroidclient.mvp.modelImpl

import com.fengyongge.wanandroidclient.api.WanAndroidRetrofit
import com.fengyongge.wanandroidclient.bean.openeye.OpenEyeDailyBean
import com.fengyongge.wanandroidclient.bean.openeye.OpenEyeRelateCommentBean
import com.fengyongge.wanandroidclient.bean.openeye.OpenEyeRelateVideoBean
import com.fengyongge.wanandroidclient.mvp.contract.OpenEyeContract
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class OpenEeyModelImpl : OpenEyeContract.Model {

    override fun getOpenEyeDaily(date: String, num: Int): Observable<OpenEyeDailyBean> {
        return WanAndroidRetrofit.service
            .getOpenEyeDaily(date)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getOeRelateVideo(id: String): Observable<OpenEyeRelateVideoBean> {
        return WanAndroidRetrofit.service
            .getOeRelateVideo(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getOeRelateComment(videoId: String): Observable<OpenEyeRelateCommentBean> {
        return WanAndroidRetrofit.service
            .getOeRelateComment(videoId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}