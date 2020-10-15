package com.fengyongge.wanandroidclient.mvp.modelImpl

import com.fengyongge.baselib.net.BaseResponse
import com.fengyongge.wanandroidclient.api.WanAndroidRetrofit
import com.fengyongge.wanandroidclient.bean.UserInforBean
import com.fengyongge.wanandroidclient.mvp.contract.UserInforContact
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserInforModelImpl : UserInforContact.Model {
    override fun getAccount(): Observable<BaseResponse<UserInforBean>> {
        return WanAndroidRetrofit.service
            .getAccount()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    override fun getLogout(): Observable<BaseResponse<String>> {
        return WanAndroidRetrofit.service
            .logout()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}