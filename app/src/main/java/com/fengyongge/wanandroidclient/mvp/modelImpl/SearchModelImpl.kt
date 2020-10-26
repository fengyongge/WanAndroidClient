package com.fengyongge.wanandroidclient.mvp.modelImpl

import com.fengyongge.rxhttp.bean.BaseResponse
import com.fengyongge.wanandroidclient.api.WanAndroidRetrofit
import com.fengyongge.wanandroidclient.bean.HotKeyBeanItem
import com.fengyongge.wanandroidclient.bean.SearchContentBean
import com.fengyongge.wanandroidclient.mvp.contract.SearchContact
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchModelImpl : SearchContact.Model {
    override fun postCollect(id: Int): Observable<BaseResponse<String>> {
        return WanAndroidRetrofit.service
            .postCollect(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())    }

    override fun postCancleCollect(id: Int): Observable<BaseResponse<String>> {
        return WanAndroidRetrofit.service
            .postCancleCollect(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())    }

    override fun getHotKey(): Observable<BaseResponse<List<HotKeyBeanItem>>> {
        return WanAndroidRetrofit.service
            .getHotKey()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    override fun postSearchContent(
        pageNum: Int,
        searchContent: String
    ): Observable<BaseResponse<SearchContentBean>> {
        return WanAndroidRetrofit.service
            .postSearchContent(pageNum,searchContent)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}