package com.fengyongge.wanandroidclient.mvp.presenterImpl


import com.fengyongge.baselib.mvp.BasePresenter
import com.fengyongge.baselib.mvp.IBaseView
import com.fengyongge.baselib.net.BaseResponse
import com.fengyongge.baselib.net.exception.ResponseException
import com.fengyongge.baselib.rx.observer.BaseObserver
import com.fengyongge.wanandroidclient.bean.ProjectBean
import com.fengyongge.wanandroidclient.bean.ProjectTypeBeanItem
import com.fengyongge.wanandroidclient.mvp.contract.ProjectContract
import com.fengyongge.wanandroidclient.mvp.modelImpl.ProjectModelImpl

class ProjectPresenterImpl : BasePresenter<ProjectContract.View>(), ProjectContract.Presenter {

    private lateinit var mModel: ProjectModelImpl
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

    override fun getProjectType() {


        mView?.let {

            mModel.getProjectType()
                .subscribe(object : BaseObserver<BaseResponse<List<ProjectTypeBeanItem>>>() {

                    override fun onSuccess(data: BaseResponse<List<ProjectTypeBeanItem>>) {
                        it.getProjectTypeShow(data)
                    }

                    override fun onError(e: ResponseException) {
                        it.onError(e)
                    }


                })
        }


    }

    override fun getProjectByType(pageNum: Int, cid: String) {

        mView?.let {
            mModel.getProjectByType(pageNum, cid)
                .subscribe(object : BaseObserver<BaseResponse<ProjectBean>>() {
                    override fun onSuccess(data: BaseResponse<ProjectBean>) {
                        mView?.getProjectByType(data)
                    }

                    override fun onError(e: ResponseException) {
                        mView?.onError(e)
                    }

                })
        }


    }


    override fun attach(view: IBaseView) {
        super.attach(view)
        mModel = ProjectModelImpl()

    }


    override fun detech() {
        super.detech()
    }


}