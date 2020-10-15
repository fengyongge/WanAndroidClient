package com.fengyongge.wanandroidclient.mvp.modelImpl

import com.fengyongge.baselib.net.BaseResponse
import com.fengyongge.wanandroidclient.api.WanAndroidRetrofit
import com.fengyongge.wanandroidclient.bean.ProjectBean
import com.fengyongge.wanandroidclient.bean.ProjectTypeBeanItem
import com.fengyongge.wanandroidclient.mvp.contract.ProjectContract
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProjectModelImpl : ProjectContract.Model {
    override fun getProjectType(): Observable<BaseResponse<List<ProjectTypeBeanItem>>> {

       return WanAndroidRetrofit.service
           .getProjectType()
           .subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getProjectByType(pageNum: Int, cid: String): Observable<BaseResponse<ProjectBean>> {

        return WanAndroidRetrofit
            .service.getProjectByType(pageNum,cid)
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