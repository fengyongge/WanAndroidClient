package com.fengyongge.wanandroidclient.mvp.presenterImpl

import com.fengyongge.baselib.mvp.BasePresenter
import com.fengyongge.baselib.mvp.IBaseView
import com.fengyongge.baselib.net.BaseResponse
import com.fengyongge.baselib.net.exception.ResponseException
import com.fengyongge.baselib.rx.observer.BaseObserver
import com.fengyongge.wanandroidclient.bean.MyCollectBean
import com.fengyongge.wanandroidclient.mvp.contract.MyCollectContact
import com.fengyongge.wanandroidclient.mvp.modelImpl.MyCollectModelImpl

class MyCollectPresenterImpl : BasePresenter<MyCollectContact.View>(), MyCollectContact.Presenter {

    private lateinit var myCollectModelImpl: MyCollectModelImpl

    
    override fun getCollectList(pageNum: Int) {
        mView?.let {
            myCollectModelImpl.getCollectList(pageNum)
                .subscribe(object : BaseObserver<BaseResponse<MyCollectBean>>(){
                    override fun onSuccess(data: BaseResponse<MyCollectBean>) {
                        it.getCollectListShow(data)
                    }

                    override fun onError(e: ResponseException) {
                        it.onError(e)
                    }

                })
        }
    }

    override fun postCancleCollect(id: String,originId: Int) {
        mView?.let {
            myCollectModelImpl.postCancleCollect(id,originId)
                .subscribe(object : BaseObserver<BaseResponse<String>>(){
                    override fun onSuccess(data: BaseResponse<String>) {
                        it.postCancleCollectShow(data)
                    }

                    override fun onError(e: ResponseException) {
                        it.onError(e)
                    }

                })
        }    }

    override fun attach(view: IBaseView) {
        super.attach(view)
        myCollectModelImpl = MyCollectModelImpl()
    }

    override fun detech() {
        super.detech()
    }
}