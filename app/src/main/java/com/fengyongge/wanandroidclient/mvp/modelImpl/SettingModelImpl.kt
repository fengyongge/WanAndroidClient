package com.fengyongge.wanandroidclient.mvp.modelImpl

import com.fengyongge.rxhttp.bean.BaseResponse
import com.fengyongge.wanandroidclient.api.WanAndroidRetrofit
import com.fengyongge.wanandroidclient.mvp.contract.SettingContract
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SettingModelImpl : SettingContract.Model{
    override fun getLogout(): Observable<BaseResponse<String>> {
        return WanAndroidRetrofit.service
            .logout()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}