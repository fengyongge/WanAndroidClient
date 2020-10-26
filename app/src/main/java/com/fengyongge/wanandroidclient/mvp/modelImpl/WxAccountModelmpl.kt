package com.fengyongge.wanandroidclient.mvp.modelImpl

import com.fengyongge.rxhttp.bean.BaseResponse
import com.fengyongge.wanandroidclient.api.WanAndroidRetrofit
import com.fengyongge.wanandroidclient.bean.WxAccountBeanItem
import com.fengyongge.wanandroidclient.bean.WxAccountSearchBean
import com.fengyongge.wanandroidclient.bean.WxHistoryBean
import com.fengyongge.wanandroidclient.mvp.contract.WxAccountContact
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class WxAccountModelImpl : WxAccountContact.Model {
    override fun getWxAccount(): Observable<BaseResponse<List<WxAccountBeanItem>>> {

        return WanAndroidRetrofit.service
            .getWxAccount()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getSearchWxContent(
        id: Int,
        pageNum: Int,
        content: String
    ): Observable<BaseResponse<WxAccountSearchBean>> {
        return WanAndroidRetrofit.service
            .getSearchWxContent(id,pageNum,content)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getWxHistoryList(id: String,pageNum: Int): Observable<BaseResponse<WxHistoryBean>> {
        return WanAndroidRetrofit.service
            .getWxHistoryList(id,pageNum)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun postCollect(id: Int): Observable<BaseResponse<String>> {
        return WanAndroidRetrofit.service
            .postCollect(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun postCancleCollect(id: Int): Observable<BaseResponse<String>> {
        return WanAndroidRetrofit.service
            .postCancleCollect(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}