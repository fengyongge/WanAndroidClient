package com.fengyongge.wanandroidclient.mvp.presenterImpl

import com.fengyongge.baselib.mvp.BasePresenter
import com.fengyongge.baselib.mvp.IBaseView
import com.fengyongge.rxhttp.bean.BaseResponse
import com.fengyongge.rxhttp.core.BaseObserver
import com.fengyongge.rxhttp.exception.ResponseException
import com.fengyongge.wanandroidclient.bean.WxAccountBeanItem
import com.fengyongge.wanandroidclient.bean.WxAccountSearchBean
import com.fengyongge.wanandroidclient.bean.WxHistoryBean
import com.fengyongge.wanandroidclient.mvp.contract.WxAccountContact
import com.fengyongge.wanandroidclient.mvp.modelImpl.WxAccountModelImpl

class WxAccountPresenterImpl : BasePresenter<WxAccountContact.View>(),WxAccountContact.Presenter {

    private lateinit var wxAccountModelmpl: WxAccountModelImpl

    override fun postCollect(id: Int) {
        mView?.let {
            wxAccountModelmpl.postCollect(id)
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
            wxAccountModelmpl.postCancleCollect(id)
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

    override fun getWxAccount() {
        mView?.let {
            wxAccountModelmpl.getWxAccount()
                .subscribe(object : BaseObserver<BaseResponse<List<WxAccountBeanItem>>>(){
                    override fun onSuccess(data: BaseResponse<List<WxAccountBeanItem>>) {
                        it.getWxAccountShow(data)
                    }

                    override fun onError(e: ResponseException) {
                        it.onError(e)
                    }

                })
        }
    }


    override fun getSearchWxContent(id: Int, pageNum: Int, content: String) {
        mView?.let {
            wxAccountModelmpl.getSearchWxContent(id,pageNum,content)
                .subscribe(object : BaseObserver<BaseResponse<WxAccountSearchBean>>(){
                    override fun onSuccess(data: BaseResponse<WxAccountSearchBean>) {
                        it.getSearchWxContentShow(data)
                    }

                    override fun onError(e: ResponseException) {
                        it.onError(e)
                    }

                })
        }
    }

    override fun getWxHistoryList(id: String,pageNum: Int) {

        mView?.let {
            wxAccountModelmpl.getWxHistoryList(id,pageNum)
                .subscribe(object : BaseObserver<BaseResponse<WxHistoryBean>>(){
                    override fun onSuccess(data: BaseResponse<WxHistoryBean>) {
                        it.getWxHistoryList(data)
                    }
                    override fun onError(e: ResponseException) {
                        it.getWxHistoryListFail(e)
                    }
                })

        }

    }

    override fun attach(view: IBaseView) {
        super.attach(view)
        wxAccountModelmpl = WxAccountModelImpl()
    }

    override fun detech() {
        super.detech()
    }
}