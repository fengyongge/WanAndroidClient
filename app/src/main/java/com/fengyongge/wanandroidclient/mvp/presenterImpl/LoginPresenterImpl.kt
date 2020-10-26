package com.fengyongge.wanandroidclient.mvp.presenterImpl

import com.fengyongge.baselib.mvp.BasePresenter
import com.fengyongge.baselib.mvp.IBaseView
import com.fengyongge.rxhttp.bean.BaseResponse
import com.fengyongge.rxhttp.core.BaseObserver
import com.fengyongge.rxhttp.exception.ResponseException
import com.fengyongge.wanandroidclient.bean.LoginBean
import com.fengyongge.wanandroidclient.bean.RegisterBean
import com.fengyongge.wanandroidclient.mvp.contract.LoginContact
import com.fengyongge.wanandroidclient.mvp.modelImpl.LoginModelImpl

class LoginPresenterImpl : BasePresenter<LoginContact.View>(), LoginContact.Presenter {

    lateinit var mModel: LoginModelImpl

    override fun postLogin(userName: String, password: String) {
        mView?.getCurrentView().let {
            mModel.postLogin(userName, password).subscribe(object :
                BaseObserver<BaseResponse<LoginBean>>() {
                override fun onSuccess(data: BaseResponse<LoginBean>) {
                    mView?.postLoginShow(data)
                }

                override fun onError(e: ResponseException) {
                    mView?.onError(e)
                }
            })
        }
    }

    override fun postRegister(userName: String, password: String, passwordConfirm: String) {
        mView?.getCurrentView().let {
            mModel.postRegister(userName, password, passwordConfirm)
                .subscribe(object :
                BaseObserver<BaseResponse<RegisterBean>>() {
                override fun onSuccess(data: BaseResponse<RegisterBean>) {
                    mView?.postRegisterShow(data)
                }

                override fun onError(e: ResponseException) {
                    mView?.onError(e)
                }
            })
        }
    }



    override fun attach(view: IBaseView) {
        super.attach(view)
        mModel = LoginModelImpl()
    }


    override fun detech() {
        super.detech()
    }
}