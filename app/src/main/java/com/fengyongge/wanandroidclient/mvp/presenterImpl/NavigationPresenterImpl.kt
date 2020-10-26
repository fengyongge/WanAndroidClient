package com.fengyongge.wanandroidclient.mvp.presenterImpl

import com.fengyongge.baselib.mvp.BasePresenter
import com.fengyongge.baselib.mvp.IBaseView
import com.fengyongge.rxhttp.bean.BaseResponse
import com.fengyongge.rxhttp.core.BaseObserver
import com.fengyongge.rxhttp.exception.ResponseException
import com.fengyongge.wanandroidclient.bean.NavigationBean
import com.fengyongge.wanandroidclient.mvp.contract.NavigationContact
import com.fengyongge.wanandroidclient.mvp.modelImpl.NavigationModelImpl

class NavigationPresenterImpl : BasePresenter<NavigationContact.View>(),NavigationContact.Presenter {
    lateinit var mModel: NavigationModelImpl
    override fun getNavigation() {
        mView?.let {
            mModel.getNavigation()
                .subscribe(object : BaseObserver<BaseResponse<List<NavigationBean>>>() {
                    override fun onSuccess(data: BaseResponse<List<NavigationBean>>) {
                        it.getNavigationShow(data)
                    }
                    override fun onError(e: ResponseException) {
                        it.onError(e)
                    }
                })
        }
    }


    override fun attach(view: IBaseView) {
        super.attach(view)
        mModel = NavigationModelImpl()
    }

    override fun detech() {
        super.detech()
    }
}