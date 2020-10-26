package com.fengyongge.wanandroidclient.mvp.modelImpl

import com.fengyongge.rxhttp.bean.BaseResponse
import com.fengyongge.wanandroidclient.api.WanAndroidRetrofit
import com.fengyongge.wanandroidclient.bean.NavigationBean
import com.fengyongge.wanandroidclient.mvp.contract.NavigationContact
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NavigationModelImpl : NavigationContact.Model {
    override fun getNavigation(): Observable<BaseResponse<List<NavigationBean>>> {
        return WanAndroidRetrofit.service
            .getNavigation()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


}
