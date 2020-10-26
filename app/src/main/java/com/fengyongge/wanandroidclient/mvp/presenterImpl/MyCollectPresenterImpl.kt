package com.fengyongge.wanandroidclient.mvp.presenterImpl

import com.fengyongge.baselib.mvp.BasePresenter
import com.fengyongge.baselib.mvp.IBaseView
import com.fengyongge.rxhttp.bean.BaseResponse
import com.fengyongge.rxhttp.core.BaseObserver
import com.fengyongge.rxhttp.exception.ResponseException
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
                        it.getCollectListFail(e)
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
                        it.postCancleCollectFail(e)
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