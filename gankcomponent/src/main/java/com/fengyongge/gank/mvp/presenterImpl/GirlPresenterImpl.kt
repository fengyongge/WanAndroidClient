package com.fengyongge.gank.mvp.presenterImpl

import com.fengyongge.baseframework.mvp.BasePresenter
import com.fengyongge.baseframework.mvp.IBaseView
import com.fengyongge.gank.bean.GankGirlBean
import com.fengyongge.gank.mvp.contract.GirlContract
import com.fengyongge.gank.mvp.model.GirlModelImpl
import com.fengyongge.rxhttp.core.BaseObserver
import com.fengyongge.rxhttp.exception.ResponseException

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
                        it.getGankGirlFail(e)
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