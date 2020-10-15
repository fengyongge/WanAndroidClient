package com.fengyongge.wanandroidclient.mvp.presenterImpl

import com.fengyongge.baselib.mvp.BasePresenter
import com.fengyongge.baselib.mvp.IBaseView
import com.fengyongge.baselib.net.BaseResponse
import com.fengyongge.baselib.net.exception.ResponseException
import com.fengyongge.baselib.rx.observer.BaseObserver
import com.fengyongge.wanandroidclient.bean.HotKeyBeanItem
import com.fengyongge.wanandroidclient.bean.SearchContentBean
import com.fengyongge.wanandroidclient.mvp.contract.SearchContact
import com.fengyongge.wanandroidclient.mvp.modelImpl.SearchModelImpl

class SearchPresenterImpl : BasePresenter<SearchContact.View>(),SearchContact.Presenter{
    lateinit var searchModelImpl: SearchModelImpl
    override fun postCollect(id: Int) {
        mView?.let {
            searchModelImpl.postCollect(id)
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
            searchModelImpl.postCancleCollect(id)
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

    override fun getHotKey() {
        mView?.let {
            searchModelImpl.getHotKey()
                .subscribe(object : BaseObserver<BaseResponse<List<HotKeyBeanItem>>>() {
                    override fun onSuccess(data: BaseResponse<List<HotKeyBeanItem>>) {
                        it.getHotKeyShow(data)
                    }

                    override fun onError(e: ResponseException) {
                        it.onError(e)
                    }

                })
        }
    }

    override fun postSearchContent(pageNum: Int, searchContent: String) {
        mView?.let {
            searchModelImpl.postSearchContent(pageNum,searchContent)
                .subscribe(object : BaseObserver<BaseResponse<SearchContentBean>>() {
                    override fun onSuccess(data: BaseResponse<SearchContentBean>) {
                        it.postSearchContentShow(data)
                    }

                    override fun onError(e: ResponseException) {
                        it.onError(e)
                    }

                })
        }
    }

    override fun attach(view: IBaseView) {
        super.attach(view)
        searchModelImpl = SearchModelImpl()
    }

    override fun detech() {
        super.detech()
    }


}