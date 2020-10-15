package com.fengyongge.wanandroidclient.mvp.presenterImpl

import com.fengyongge.baselib.mvp.BasePresenter
import com.fengyongge.baselib.mvp.IBaseView
import com.fengyongge.baselib.net.BaseResponse
import com.fengyongge.baselib.net.exception.ResponseException
import com.fengyongge.baselib.rx.observer.BaseObserver
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
                .subscribe(object : BaseObserver<BaseResponse<String>>() {
                    override fun onSuccess(data: BaseResponse<String>) {
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
