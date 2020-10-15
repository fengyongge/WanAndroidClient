package com.fengyongge.wanandroidclient.mvp.contract

import com.fengyongge.baselib.mvp.IBasePresenter
import com.fengyongge.baselib.mvp.IBaseView
import com.fengyongge.baselib.net.BaseResponse
import com.fengyongge.baselib.net.exception.ResponseException
import com.fengyongge.wanandroidclient.bean.QuestionAnswerBean
import io.reactivex.Observable

class QuestionAnswerContact{
    interface Presenter : IBasePresenter {
        fun postCollect(id: Int)
        fun postCancleCollect(id: Int)
        fun getQuestionAnswer(pageNum: Int)
    }

    interface Model {
        fun postCollect(id: Int): Observable<BaseResponse<String>>
        fun postCancleCollect(id: Int): Observable<BaseResponse<String>>
        fun getQuestionAnswer(pageNum: Int): Observable<BaseResponse<QuestionAnswerBean>>
    }

    interface View : IBaseView {
        fun postCollectShow(data: BaseResponse<String>)
        fun postCancleCollectShow(data: BaseResponse<String>)
        fun getQuestionAnswerShow(data: BaseResponse<QuestionAnswerBean>)
        fun onError(data: ResponseException)
    }

}
