package com.fengyongge.wanandroidclient.mvp.presenterImpl

import com.fengyongge.baseframework.mvp.BasePresenter
import com.fengyongge.baseframework.mvp.IBaseView
import com.fengyongge.rxhttp.bean.BaseResponse
import com.fengyongge.rxhttp.core.BaseObserver
import com.fengyongge.rxhttp.exception.ResponseException
import com.fengyongge.wanandroidclient.bean.MyShareBean
import com.fengyongge.wanandroidclient.mvp.contract.ShareContract
import com.fengyongge.wanandroidclient.mvp.modelImpl.ShareModelImpl

class SharePresenterImpl : BasePresenter<ShareContract.View>(), ShareContract.Presenter {

    private lateinit var shareModelImpl: ShareModelImpl

    override fun postShare(title: String, link: String) {
            mView?.let {
                shareModelImpl.postShare(title,link)
                    .subscribe(object : BaseObserver<BaseResponse<String>>() {
                        override fun onSuccess(data: BaseResponse<String>) {
                            it.postShareShow(data)
                        }

                        override fun onError(e: ResponseException) {
                            it.onError(e)
                        }

                    })
            }
        }

    override fun getShareList(pageNum: Int) {
        mView?.let {
            shareModelImpl.getShareList(pageNum)
                .subscribe(object : BaseObserver<BaseResponse<MyShareBean>>() {
                    override fun onSuccess(data: BaseResponse<MyShareBean>) {
                        it.getShareListShow(data)
                    }

                    override fun onError(e: ResponseException) {
                        it.onError(e)
                    }

                })
        }
    }

    override fun postDeleteMyShare(id: Int) {
        mView?.let {
            shareModelImpl.postDeleteMyShare(id)
                .subscribe(object : BaseObserver<BaseResponse<String>>() {
                    override fun onSuccess(data: BaseResponse<String>) {
                        it.postDeleteMyShareShow(data)
                    }

                    override fun onError(e: ResponseException) {
                        it.onError(e)
                    }

                })
        }
    }

    override fun attach(view: IBaseView) {
        super.attach(view)
        shareModelImpl = ShareModelImpl()
    }

    override fun detech() {
        super.detech()
    }



}
