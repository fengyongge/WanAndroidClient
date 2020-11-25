package com.fengyongge.login.mvp.model

import com.fengyongge.login.api.LoginRetrofit
import com.fengyongge.rxhttp.bean.BaseResponse
import com.fengyongge.login.bean.LoginBean
import com.fengyongge.login.bean.RegisterBean
import com.fengyongge.login.mvp.contract.LoginContact
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginModelImpl : LoginContact.Model {
    override fun postLogin(
        userName: String,
        password: String
    ): Observable<BaseResponse<LoginBean>> {

        return LoginRetrofit.service
            .login(userName,password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun postRegister(
        userName: String,
        password: String,
        passwordConfirm: String
    ): Observable<BaseResponse<RegisterBean>> {
        return LoginRetrofit.service
            .register(userName,password,passwordConfirm)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


}