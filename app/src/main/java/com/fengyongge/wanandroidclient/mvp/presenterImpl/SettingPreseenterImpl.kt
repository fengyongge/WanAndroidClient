package com.fengyongge.wanandroidclient.mvp.presenterImpl

import com.fengyongge.androidcommonutils.ktutils.CacheUtils
import com.fengyongge.baseframework.mvp.BasePresenter
import com.fengyongge.baseframework.mvp.IBaseView
import com.fengyongge.rxhttp.bean.BaseResponse
import com.fengyongge.rxhttp.core.BaseObserver
import com.fengyongge.rxhttp.exception.ResponseException
import com.fengyongge.wanandroidclient.mvp.contract.SettingContract
import com.fengyongge.wanandroidclient.mvp.modelImpl.SettingModelImpl
import io.reactivex.Observable

class SettingPreseenterImpl : BasePresenter<SettingContract.View>(),SettingContract.Presenter{

    lateinit var model: SettingModelImpl

    override fun getLogout() {
        mView?.getCurrentView().let {
            model.getLogout().subscribe(object :
                BaseObserver<BaseResponse<String>>() {
                override fun onSuccess(data: BaseResponse<String>) {
                    mView?.getLogoutSuccess(data)
                }

                override fun onError(e: ResponseException) {
                    mView?.getLogoutFail(e)
                }
            })
        }
    }

    override fun clearCache() {

        Observable.create<String> { emitter ->
            CacheUtils.clearAllCache()
            val size: String = CacheUtils.totalCacheSize()
            if (!emitter.isDisposed) {
                emitter.onNext(size)
            }
        }.subscribe(object :
            BaseObserver<String>() {
            override fun onSuccess(data: String) {
                mView?.clearCacheSuccess(data)
            }

            override fun onError(e: ResponseException) {
            }
        })


    }

    override fun attach(view: IBaseView) {
        super.attach(view)
        model = SettingModelImpl()
    }

    override fun detech() {
        super.detech()
    }


}