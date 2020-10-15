package com.fengyongge.wanandroidclient.mvp.contract

import com.fengyongge.baselib.mvp.IBasePresenter
import com.fengyongge.baselib.mvp.IBaseView
import com.fengyongge.baselib.net.BaseResponse
import com.fengyongge.baselib.net.exception.ResponseException
import com.fengyongge.wanandroidclient.bean.ProjectBean
import com.fengyongge.wanandroidclient.bean.ProjectTypeBeanItem
import io.reactivex.Observable

interface ProjectContract  {

    interface Presenter : IBasePresenter{
        fun postCollect(id: Int)
        fun postCancleCollect(id: Int)
        fun getProjectType()
        fun getProjectByType(pageNum: Int,cid: String)
    }

    interface Model {
        fun postCollect(id: Int): Observable<BaseResponse<String>>
        fun postCancleCollect(id: Int): Observable<BaseResponse<String>>
        fun getProjectType(): Observable<BaseResponse<List<ProjectTypeBeanItem>>>
        fun getProjectByType(pageNum: Int,cid: String): Observable<BaseResponse<ProjectBean>>
    }

    interface View : IBaseView {
        fun postCollectShow(data: BaseResponse<String>)
        fun postCancleCollectShow(data: BaseResponse<String>)
        fun getProjectTypeShow(data: BaseResponse<List<ProjectTypeBeanItem>>)
        fun getProjectByType(data: BaseResponse<ProjectBean>)
        fun onError(data: ResponseException)

    }
}