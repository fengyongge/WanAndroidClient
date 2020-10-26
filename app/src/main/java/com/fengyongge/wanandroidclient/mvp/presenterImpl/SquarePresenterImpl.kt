package com.fengyongge.wanandroidclient.mvp.presenterImpl

import com.fengyongge.baselib.mvp.BasePresenter
import com.fengyongge.baselib.mvp.IBaseView
import com.fengyongge.rxhttp.bean.BaseResponse
import com.fengyongge.rxhttp.core.BaseObserver
import com.fengyongge.rxhttp.exception.ResponseException
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
                        it.getSquareProjectFail(e)
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