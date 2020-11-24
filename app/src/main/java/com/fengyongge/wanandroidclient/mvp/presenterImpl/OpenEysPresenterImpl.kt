package com.fengyongge.wanandroidclient.mvp.presenterImpl

import com.fengyongge.baseframework.mvp.BasePresenter
import com.fengyongge.baseframework.mvp.IBaseView
import com.fengyongge.rxhttp.core.BaseObserver
import com.fengyongge.rxhttp.exception.ResponseException
import com.fengyongge.wanandroidclient.bean.openeye.OpenEyeDailyBean
import com.fengyongge.wanandroidclient.bean.openeye.OpenEyeRelateCommentBean
import com.fengyongge.wanandroidclient.bean.openeye.OpenEyeRelateVideoBean
import com.fengyongge.wanandroidclient.mvp.contract.OpenEyeContract
import com.fengyongge.wanandroidclient.mvp.modelImpl.OpenEeyModelImpl

class OpenEysPresenterImpl : BasePresenter<OpenEyeContract.View>(),OpenEyeContract.Presenter {

    private lateinit var openEyeModelImpl: OpenEeyModelImpl

    override fun getOpenEyeDaily(date: String, num: Int) {
        mView?.let {
            openEyeModelImpl.getOpenEyeDaily(date,num)
                .subscribe(object : BaseObserver<OpenEyeDailyBean>(){
                    override fun onSuccess(data: OpenEyeDailyBean) {
                        it.getOpenEyeDailyShow(data)
                    }

                    override fun onError(e: ResponseException) {
                        it.getOpenEyeDailyShowFail(e)
                    }

                })
        }
    }

    override fun getOeRelateVideo(id: String) {
        mView?.let {
            openEyeModelImpl.getOeRelateVideo(id)
                .subscribe(object : BaseObserver<OpenEyeRelateVideoBean>(){
                    override fun onSuccess(data: OpenEyeRelateVideoBean) {
                        it.getOeRelateVideoShow(data)
                    }

                    override fun onError(e: ResponseException) {
                        it.onError(e)
                    }

                })
        }    }

    override fun getOeRelateComment(videoId: String) {
        mView?.let {
            openEyeModelImpl.getOeRelateComment(videoId)
                .subscribe(object : BaseObserver<OpenEyeRelateCommentBean>(){
                    override fun onSuccess(data: OpenEyeRelateCommentBean) {
                        it.getOeRelateCommentShow(data)
                    }

                    override fun onError(e: ResponseException) {
                        it.onError(e)
                    }

                })
        }
    }

    override fun attach(view: IBaseView) {
        super.attach(view)
        openEyeModelImpl = OpenEeyModelImpl()
    }

    override fun detech() {
        super.detech()
    }
}