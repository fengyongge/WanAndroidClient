package com.fengyongge.wanandroidclient.mvp.presenterImpl

import com.fengyongge.baselib.mvp.BasePresenter
import com.fengyongge.baselib.mvp.IBaseView
import com.fengyongge.baselib.net.exception.ResponseException
import com.fengyongge.baselib.rx.observer.BaseObserver
import com.fengyongge.wanandroidclient.bean.GankGirlBean
import com.fengyongge.wanandroidclient.mvp.contract.GirlContract
import com.fengyongge.wanandroidclient.mvp.modelImpl.GirlModelImpl

class GirlPresenterImpl : BasePresenter<GirlContract.view>(),GirlContract.presenter {

    private lateinit var girlModelImpl: GirlModelImpl

    override fun getGankGirl(pageNum: Int, pageSize: Int) {
        mView?.let {
            girlModelImpl.getGankGirl(pageNum,pageSize)
                .subscribe(object : BaseObserver<GankGirlBean>(){
                    override fun onSuccess(data: GankGirlBean) {
                        it.getGankGirlShow(data)
                    }

                    override fun onError(e: ResponseException) {
                        it.onError(e)
                    }

                })
        }

    }

    override fun attach(view: IBaseView) {
        super.attach(view)
        girlModelImpl = GirlModelImpl()
    }

    override fun detech() {
        super.detech()
    }
}