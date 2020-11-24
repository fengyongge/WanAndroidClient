package com.fengyongge.wanandroidclient.mvp.presenterImpl

import com.fengyongge.baseframework.mvp.BasePresenter
import com.fengyongge.baseframework.mvp.IBaseView
import com.fengyongge.rxhttp.bean.BaseResponse
import com.fengyongge.rxhttp.core.BaseObserver
import com.fengyongge.rxhttp.exception.ResponseException
import com.fengyongge.wanandroidclient.bean.SystemArticleBean
import com.fengyongge.wanandroidclient.bean.SystemCategoryBean
import com.fengyongge.wanandroidclient.mvp.contract.SystemContact
import com.fengyongge.wanandroidclient.mvp.modelImpl.SystemModelImpl

class SystemPresenterImpl :BasePresenter<SystemContact.View>(),SystemContact.Presenter {

    lateinit var mModel: SystemModelImpl
    override fun postCollect(id: Int) {
        mView?.let {
            mModel.postCollect(id)
                .subscribe(object : BaseObserver<BaseResponse<String>>() {
                    override fun onSuccess(data: BaseResponse<String>) {
                        it.postCollectShow(data)
                    }

                    override fun onError(e: ResponseException) {
                        it.onError(e)
                    }

                })
        }
    }

    override fun postCancleCollect(id: Int) {
        mView?.let {
            mModel.postCancleCollect(id)
                .subscribe(object : BaseObserver<BaseResponse<String>>() {
                    override fun onSuccess(data: BaseResponse<String>) {
                        it.postCancleCollectShow(data)
                    }

                    override fun onError(e: ResponseException) {
                        it.onError(e)
                    }

                })
        }
    }
    override fun getSystemCategory() {
        mView?.let {
            mModel.getSystemCategory().subscribe(object : BaseObserver<BaseResponse<List<SystemCategoryBean>>>() {
                override fun onSuccess(data: BaseResponse<List<SystemCategoryBean>>) {
                    it.getSystemCategoryShow(data)
                }
                override fun onError(e: ResponseException) {
                    it.onError(e)

                }

            })
        }
    }

    override fun getSystemArticle(pageNum: Int, cid: Int) {
        mView?.let {
            mModel.getSystemArticle(pageNum,cid).subscribe(object : BaseObserver<BaseResponse<SystemArticleBean>>() {

                override fun onSuccess(data: BaseResponse<SystemArticleBean>) {
                    it.getSystemArticleShow(data)
                }
                override fun onError(e: ResponseException) {
                    it.getSystemArticleFail(e)

                }

            })
        }
    }

    override fun attach(view: IBaseView) {
        super.attach(view)
        mModel = SystemModelImpl()
    }
}