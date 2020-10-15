package com.fengyongge.wanandroidclient.mvp.presenterImpl

import com.fengyongge.baselib.mvp.BasePresenter
import com.fengyongge.baselib.mvp.IBaseView
import com.fengyongge.baselib.net.BaseResponse
import com.fengyongge.baselib.net.exception.ResponseException
import com.fengyongge.baselib.rx.observer.BaseObserver
import com.fengyongge.wanandroidclient.bean.UserInforBean
import com.fengyongge.wanandroidclient.mvp.contract.UserInforContact
import com.fengyongge.wanandroidclient.mvp.modelImpl.UserInforModelImpl

class UserInforPresenterImpl : BasePresenter<UserInforContact.View>(), UserInforContact.Presenter  {

    lateinit var model: UserInforModelImpl
    override fun getAccount() {

        mView?.let {
            model.getAccount().subscribe(object : BaseObserver<BaseResponse<UserInforBean>>() {
                override fun onSuccess(data: BaseResponse<UserInforBean>) {
                    it.getAccountShow(data)
                }

                override fun onError(e: ResponseException) {
                    it.onError(e)
                }

            })
        }

    }

    override fun getLogout() {
        mView?.getCurrentView().let {
            model.getLogout().subscribe(object :
                BaseObserver<BaseResponse<String>>() {
                override fun onSuccess(data: BaseResponse<String>) {
                    mView?.getLogoutShow(data)
                }

                override fun onError(e: ResponseException) {
                    mView?.onError(e)
                }
            })
        }
    }

    override fun attach(view: IBaseView) {
        super.attach(view)
        model = UserInforModelImpl()
    }

    override fun detech() {
        super.detech()
    }


}