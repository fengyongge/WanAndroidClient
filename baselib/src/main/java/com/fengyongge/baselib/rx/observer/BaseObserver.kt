package com.fengyongge.baselib.rx.observer


import com.fengyongge.baselib.net.exception.ExceptionHandler
import com.fengyongge.baselib.net.exception.ResponseException
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

abstract class BaseObserver<E> : Observer<E> {

    private lateinit var disposable: Disposable

    override fun onSubscribe(d: Disposable) {
        disposable = d
    }

    override fun onNext(data: E) {
        onSuccess(data)
    }

    override fun onError(e: Throwable) {
        var responseException: ResponseException ?=null
        responseException = if(e is ResponseException){
            ExceptionHandler.handle(e)
        }else{//非统一数据结构
            if(e.message.toString().contains("Unable to resolve host")){
                ResponseException(e,"1001","网络异常,请检查网络")
            }else{
                ResponseException(e,"1000",e.message.toString())
            }
        }
        responseException?.let { onError(it) }
    }

    override fun onComplete() {

    }

    fun getDisposable() = disposable

    abstract fun onSuccess(data: E)

    abstract fun onError(e: ResponseException)
}