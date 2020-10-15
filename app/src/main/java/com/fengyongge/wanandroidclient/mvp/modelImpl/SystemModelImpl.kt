package com.fengyongge.wanandroidclient.mvp.modelImpl

import com.fengyongge.baselib.net.BaseResponse
import com.fengyongge.wanandroidclient.api.WanAndroidRetrofit
import com.fengyongge.wanandroidclient.bean.SystemArticleBean
import com.fengyongge.wanandroidclient.bean.SystemCategoryBean
import com.fengyongge.wanandroidclient.mvp.contract.SystemContact
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SystemModelImpl : SystemContact.Model {
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

    override fun getSystemCategory(): Observable<BaseResponse<List<SystemCategoryBean>>> {
        return WanAndroidRetrofit.service
            .getSystemTree()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getSystemArticle(pageNum: Int,cid: Int): Observable<BaseResponse<SystemArticleBean>> {
        return WanAndroidRetrofit.service
            .getSystemProject(pageNum,cid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}