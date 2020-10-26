package com.fengyongge.wanandroidclient.mvp.modelImpl

import com.fengyongge.rxhttp.bean.BaseResponse
import com.fengyongge.wanandroidclient.api.WanAndroidRetrofit
import com.fengyongge.wanandroidclient.bean.ArticleBean
import com.fengyongge.wanandroidclient.bean.BannerBean
import com.fengyongge.wanandroidclient.bean.DataX
import com.fengyongge.wanandroidclient.mvp.contract.HomePageContract
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomePageModelImpl : HomePageContract.Model {
    override fun bannerList(): Observable<BaseResponse<List<BannerBean>>> {
        return WanAndroidRetrofit.service
            .bannerList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun stickArticle(): Observable<BaseResponse<List<DataX>>> {
        return WanAndroidRetrofit.service
            .stickArticle()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun articleList(pageNum: Int): Observable<BaseResponse<ArticleBean>> {
       return WanAndroidRetrofit.service
           .articleList(pageNum)
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
    }

    override fun projectList(pageNum: Int): Observable<BaseResponse<ArticleBean>> {
        return WanAndroidRetrofit.service
            .projectList(pageNum)
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