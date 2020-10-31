package com.fengyongge.wanandroidclient.mvp.presenterImpl

import com.fengyongge.baseframework.mvp.BasePresenter
import com.fengyongge.baseframework.mvp.IBaseView
import com.fengyongge.rxhttp.bean.BaseResponse
import com.fengyongge.rxhttp.core.BaseObserver
import com.fengyongge.rxhttp.exception.ExceptionHandler
import com.fengyongge.rxhttp.exception.ResponseException
import com.fengyongge.wanandroidclient.bean.QuestionAnswerBean
import com.fengyongge.wanandroidclient.mvp.contract.QuestionAnswerContact
import com.fengyongge.wanandroidclient.mvp.modelImpl.QuestionAnswerModelImpl

class QuestionAnswerPresenterImpl : BasePresenter<QuestionAnswerContact.View>(), QuestionAnswerContact.Presenter {

    private lateinit var mModel: QuestionAnswerModelImpl

    override fun postCollect(id: Int) {
        mView?.let {
            mModel.postCollect(id)
                .subscribe(object : BaseObserver<BaseResponse<String>>() {
                    override fun onSuccess(data: BaseResponse<String>) {
                        it.postCollectShow(data)
                    }

                    override fun onError(e: ResponseException) {
                        it.onError(e)
                    }

                })
        }
    }

    override fun postCancleCollect(id: Int) {
        mView?.let {
            mModel.postCancleCollect(id)
                .subscribe(object : BaseObserver<BaseResponse<String>>() {
                    override fun onSuccess(data: BaseResponse<String>) {
                        it.postCancleCollectShow(data)
                    }

                    override fun onError(e: ResponseException) {
                        it.onError(e)
                    }

                })
        }
    }

    override fun getQuestionAnswer(pageNum: Int) {
        mView?.let {
            mModel.getQuestionAnswer(pageNum)
                .subscribe(object : BaseObserver<BaseResponse<QuestionAnswerBean>>() {
                    override fun onSuccess(data: BaseResponse<QuestionAnswerBean>) {
                        it.getQuestionAnswerShow(data)
                    }

                    override fun onError(e: ResponseException) {

                        ExceptionHandler.handle(e)
                        it.getQuestionAnswerFail(e)
                    }

                })
        }    }

    override fun attach(view: IBaseView) {
        super.attach(view)
        mModel = QuestionAnswerModelImpl()
    }

    override fun detech() {
        super.detech()
    }

}