package com.fengyongge.wanandroidclient.mvp.modelImpl

import com.fengyongge.rxhttp.bean.BaseResponse
import com.fengyongge.wanandroidclient.api.WanAndroidRetrofit
import com.fengyongge.wanandroidclient.bean.LoginBean
import com.fengyongge.wanandroidclient.bean.RegisterBean
import com.fengyongge.wanandroidclient.mvp.contract.LoginContact
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginModelImpl : LoginContact.Model {
    override fun postLogin(
        userName: String,
        password: String
    ): Observable<BaseResponse<LoginBean>> {
        return WanAndroidRetrofit.service
            .login(userName,password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun postRegister(
        userName: String,
        password: String,
        passwordConfirm: String
    ): Observable<BaseResponse<RegisterBean>> {
        return WanAndroidRetrofit.service
            .register(userName,password,passwordConfirm)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


}