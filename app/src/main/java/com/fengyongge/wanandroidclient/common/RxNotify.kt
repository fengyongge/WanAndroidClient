package com.fengyongge.wanandroidclient.common

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class RxNotify private constructor() {
    private val bus: Subject<Any> = PublishSubject.create<Any>().toSerialized()
    fun post(o: Any) {
        bus.onNext(o)
    }

    fun hasObservable(): Boolean {
        return bus.hasObservers()
    }

    /*
     * 转换为特定类型的Obserbale
     */
    fun <T> toObservable(type: Class<T>?): Observable<T> {
        return bus.ofType(type)
    }

    companion object {
        @Volatile
        private var defaultRxBus: RxNotify? = null
        val instance: RxNotify?
            get() {
                if (null == defaultRxBus) {
                    synchronized(RxNotify::class.java) {
                        if (null == defaultRxBus) {
                            defaultRxBus =
                                RxNotify()
                        }
                    }
                }
                return defaultRxBus
            }
    }

}