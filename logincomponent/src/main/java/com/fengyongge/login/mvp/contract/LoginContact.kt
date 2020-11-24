package com.fengyongge.login.mvp.contract

import com.fengyongge.baseframework.mvp.IBasePresenter
import com.fengyongge.baseframework.mvp.IBaseView
import com.fengyongge.login.bean.LoginBean
import com.fengyongge.login.bean.RegisterBean
import com.fengyongge.rxhttp.bean.BaseResponse
import com.fengyongge.rxhttp.exception.ResponseException
import io.reactivex.Observable

class LoginContact {

    interface Presenter : IBasePresenter{
        fun postLogin(userName: String,password: String)
        fun postRegister(userName: String,password: String,passwordConfirm: String)
    }

    interface Model{

        fun postLogin(userName: String,password: String): Observable<BaseResponse<LoginBean>>
        fun postRegister(userName: String,password: String,passwordConfirm: String): Observable<BaseResponse<RegisterBean>>
    }

    interface View : IBaseView{

        fun postLoginShow(data: BaseResponse<LoginBean>)
        fun postRegisterShow(data: BaseResponse<RegisterBean>)
        fun onError(data: ResponseException)
    }
}