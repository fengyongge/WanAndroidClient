package com.fengyongge.wanandroidclient.mvp.presenterImpl

import com.fengyongge.baselib.mvp.BasePresenter
import com.fengyongge.baselib.mvp.IBaseView
import com.fengyongge.baselib.net.BaseResponse
import com.fengyongge.baselib.net.exception.ResponseException
import com.fengyongge.baselib.rx.observer.BaseObserver
import com.fengyongge.wanandroidclient.bean.SquareBean
import com.fengyongge.wanandroidclient.mvp.contract.SquareContract
import com.fengyongge.wanandroidclient.mvp.modelImpl.SquareModelImpl

class SquarePresenterImpl : BasePresenter<SquareContract.View>(), SquareContract.Presenter {
    private lateinit var squareModelImpl: SquareModelImpl
    override fun postCollect(id: Int) {
        mView?.let {
            squareModelImpl.postCollect(id)
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
            squareModelImpl.postCancleCollect(id)
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

    override fun getSquareProject(pageNum: Int) {
        mView?.let {
            squareModelImpl.getSquareProject(pageNum)
                .subscribe(object : BaseObserver<BaseResponse<SquareBean>>() {
                    override fun onSuccess(data: BaseResponse<SquareBean>) {
                        it.getSquareProjectShow(data)
                    }

                    override fun onError(e: ResponseException) {
                        it.onError(e)
                    }

                })
        }
    }


    override fun attach(view: IBaseView) {
        super.attach(view)
        squareModelImpl = SquareModelImpl()
    }

    override fun detech() {
        super.detech()
    }

}