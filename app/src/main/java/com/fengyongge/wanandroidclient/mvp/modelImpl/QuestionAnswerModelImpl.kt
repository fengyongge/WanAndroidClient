package com.fengyongge.wanandroidclient.mvp.modelImpl

import com.fengyongge.rxhttp.bean.BaseResponse
import com.fengyongge.wanandroidclient.api.WanAndroidRetrofit
import com.fengyongge.wanandroidclient.bean.QuestionAnswerBean
import com.fengyongge.wanandroidclient.mvp.contract.QuestionAnswerContact
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class QuestionAnswerModelImpl : QuestionAnswerContact.Model {
    override fun getQuestionAnswer(pageNum: Int): Observable<BaseResponse<QuestionAnswerBean>> {
        return WanAndroidRetrofit.service
            .getQuestionAnswer(pageNum)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())        }

    override fun postCollect(id: Int): Observable<BaseResponse<String>> {
        return WanAndroidRetrofit.service
            .postCollect(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())    }

    override fun postCancleCollect(id: Int): Observable<BaseResponse<String>> {
        return WanAndroidRetrofit.service
            .postCancleCollect(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())    }
}