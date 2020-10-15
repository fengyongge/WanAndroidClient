package com.fengyongge.wanandroidclient.mvp.presenterImpl

import com.fengyongge.baselib.mvp.BasePresenter
import com.fengyongge.baselib.mvp.IBaseView
import com.fengyongge.baselib.net.BaseResponse
import com.fengyongge.baselib.net.exception.ResponseException
import com.fengyongge.baselib.rx.observer.BaseObserver
import com.fengyongge.wanandroidclient.bean.ArticleBean
import com.fengyongge.wanandroidclient.bean.BannerBean
import com.fengyongge.wanandroidclient.mvp.contract.HomePageContract
import com.fengyongge.wanandroidclient.mvp.modelImpl.HomePageModelImpl

class HomePagePresenterImpl : BasePresenter<HomePageContract.View>(), HomePageContract.Presenter {

    lateinit var mModel: HomePageModelImpl
    override fun bannerList() {
        mView?.getCurrentView().let {
            mModel.bannerList().subscribe(object : BaseObserver<BaseResponse<List<BannerBean>>>() {
                override fun onSuccess(data: BaseResponse<List<BannerBean>>) {
                    mView?.bannerListShow(data)
                }

                override fun onError(e: ResponseException) {
                    mView?.onError(e)
                }
            })
        }


    }

    override fun articleList(pageNum: Int) {
        mView?.getCurrentView().let {
            mModel.articleList(pageNum).subscribe(object : BaseObserver<BaseResponse<ArticleBean>>() {
                    override fun onSuccess(data: BaseResponse<ArticleBean>) {
                        mView?.articleListShow(data)
                    }

                    override fun onError(e: ResponseException) {
                        mView?.onError(e)
                    }
                })
        }
    }

    override fun projectList(pageNum: Int) {
        mView?.getCurrentView().let {
            mModel.projectList(pageNum).subscribe(object :
                BaseObserver<BaseResponse<ArticleBean>>() {
                override fun onSuccess(data: BaseResponse<ArticleBean>) {
                    mView?.projectListShow(data)
                }

                override fun onError(e: ResponseException) {
                    mView?.onError(e)
                }
            })
        }
    }

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


    override fun attach(V: IBaseView) {
        mModel = HomePageModelImpl()
        super.attach(V)
    }

    override fun detech() {

    }


}