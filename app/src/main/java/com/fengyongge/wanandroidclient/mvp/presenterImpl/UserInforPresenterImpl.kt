package com.fengyongge.wanandroidclient.mvp.presenterImpl

import com.fengyongge.baselib.mvp.BasePresenter
import com.fengyongge.baselib.mvp.IBaseView
import com.fengyongge.rxhttp.bean.BaseResponse
import com.fengyongge.rxhttp.core.BaseObserver
import com.fengyongge.rxhttp.exception.ResponseException
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



    override fun attach(view: IBaseView) {
        super.attach(view)
        model = UserInforModelImpl()
    }

    override fun detech() {
        super.detech()
    }


}