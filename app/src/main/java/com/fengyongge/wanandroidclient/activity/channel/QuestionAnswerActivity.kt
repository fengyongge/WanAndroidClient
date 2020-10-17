package com.fengyongge.wanandroidclient.activity.channel

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.fengyongge.baselib.mvp.BaseMvpActivity
import com.fengyongge.baselib.net.BaseResponse
import com.fengyongge.baselib.net.exception.ResponseException
import com.fengyongge.baselib.utils.DialogUtils
import com.fengyongge.baselib.utils.ToastUtils
import com.fengyongge.wanandroidclient.R
import com.fengyongge.wanandroidclient.activity.WebViewActivity
import com.fengyongge.wanandroidclient.bean.QaData
import com.fengyongge.wanandroidclient.bean.QuestionAnswerBean
import com.fengyongge.wanandroidclient.constant.Const
import com.fengyongge.wanandroidclient.mvp.contract.QuestionAnswerContact
import com.fengyongge.wanandroidclient.mvp.presenterImpl.QuestionAnswerPresenterImpl
import kotlinx.android.synthetic.main.activity_question_answer.*

class QuestionAnswerActivity : BaseMvpActivity<QuestionAnswerPresenterImpl>(),QuestionAnswerContact.View,SwipeRefreshLayout.OnRefreshListener {

    private var offset = 0
    private var isRefresh = false
    private var qaDataList = mutableListOf<QaData>()
    private lateinit var adapter: Adapter
    private var collectPosition: Int = 0
    private var pageNum: Int = 1

    override fun initLayout(): Int {
        return R.layout.activity_question_answer
    }
    
    override fun initPresenter(): QuestionAnswerPresenterImpl {
        return QuestionAnswerPresenterImpl()
    }

    override fun initView() {
        initTitle()
        swipeRefreshLayoutQa.setOnRefreshListener(this)
        swipeRefreshLayoutQa.setColorSchemeColors(
            ContextCompat.getColor(
                this,
                R.color.colorPrimary
            )
        )

        recycleViewQa.layoutManager = LinearLayoutManager(NavigationActivity@this, LinearLayoutManager.VERTICAL,false)
        adapter = Adapter(qaDataList)
        recycleViewQa.adapter = adapter

        adapter.loadMoreModule.apply {
            setOnLoadMoreListener {
                if(pageNum* Const.PAGE_SIZE < offset){
                    loadMoreComplete()
                    loadMoreEnd()
                }else{
                    pageNum++
                    loadMore(false,pageNum)
                }
            }
        }

        adapter.apply {

            addChildClickViewIds(R.id.ivSystemArticleCollect)

            setOnItemClickListener { adapter, view, position ->
                
                var qaData = adapter.data[position] as QaData
                startActivity(WebViewActivity.getIntent(this@QuestionAnswerActivity,qaData.link))

            }

            setOnItemChildClickListener { adapter, view, position ->

                when (view?.id) {
                    R.id.ivSystemArticleCollect -> {
                        var qaData = adapter.data[position] as QaData
                        collectPosition = position
                        if(qaData.collect){
                            qaData.collect = false
                            mPresenter?.postCancleCollect(qaData.id)
                        }else{
                            qaData.collect = true
                            mPresenter?.postCollect(qaData.id)
                        }
                    }
                }
            }
        }
        loadMore(true,1)
    }

    private fun initTitle(){
        var tvTitle = findViewById<TextView>(R.id.tvTitle)
        tvTitle?.text = "问答"
        var ivLeft = findViewById<ImageView>(R.id.ivLeft)
        ivLeft.visibility = View.VISIBLE
        ivLeft.setBackgroundResource(R.drawable.ic_back)
        ivLeft.setOnClickListener { finish() }
    }

    private fun loadMore(isRefresh: Boolean,pageNum: Int){
        this.isRefresh = isRefresh
        mPresenter?.getQuestionAnswer(pageNum)
        if(isRefresh){
            DialogUtils.showProgress(QuestionAnswerActivity@this,getString(R.string.collect_success))
        }
    }

    override fun initData() {

    }

    override fun postCollectShow(data: BaseResponse<String>) {
        if (data.errorCode == "0") {
            ToastUtils.showToast(this, getString(R.string.collect_success))
            adapter.notifyItemChanged(collectPosition)
        }else{
            ToastUtils.showToast(this,data.errorMsg)
        }
    }

    override fun postCancleCollectShow(data: BaseResponse<String>) {
        if (data.errorCode == "0") {
            ToastUtils.showToast(this, getString(R.string.collect_cancle))
            adapter.notifyItemChanged(collectPosition)
        }else{
            ToastUtils.showToast(this,data.errorMsg)
        }
    }


    override fun getQuestionAnswerShow(data: BaseResponse<QuestionAnswerBean>) {
        if(data.errorCode == "0"){
            qaDataList = data.data.datas as MutableList<QaData>
            if(qaDataList.isNotEmpty()){
                offset = data.data.offset
                adapter.loadMoreModule.loadMoreComplete()
                if(isRefresh){
                    adapter.setNewInstance(qaDataList)
                }else{
                    adapter.addData(qaDataList)
                }
            }else{
                adapter.loadMoreModule.loadMoreEnd()
                if (pageNum == 1) {
                    showEmptyView()
                }
            }
            DialogUtils.dismissProgressMD()
        }else{
            DialogUtils.dismissProgressMD()
            ToastUtils.showToast(QuestionAnswerActivity@this,data.errorMsg)
        }
    }
    private fun showEmptyView() {
        adapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.activity_common_empty,null))
    }

    override fun onError(data: ResponseException) {
        ToastUtils.showToast(QuestionAnswerActivity@this,data.getErrorMessage())
        DialogUtils.dismissProgressMD()
    }

    class Adapter(data: List<QaData>?) : BaseQuickAdapter<QaData, BaseViewHolder>(R.layout.item_activity_question_answer,
        data as MutableList<QaData>?
    ), LoadMoreModule {
        override fun convert(holder: BaseViewHolder, item: QaData) {
            var ivSystemArticleCollect = holder.getView<ImageView>(R.id.ivSystemArticleCollect)
            var tvSystemArticleContent = holder.getView<TextView>(R.id.tvSystemArticleContent)
            var tvSystemArticleTime = holder.getView<TextView>(R.id.tvSystemArticleTime)
            tvSystemArticleContent.text = item.title
            tvSystemArticleTime.text =item.niceDate
            if (item.collect) {
                ivSystemArticleCollect.setImageResource(R.drawable.ic_collect_fill)
            } else {
                ivSystemArticleCollect.setImageResource(R.drawable.ic_collect)
            }
        }
    }

    override fun onRefresh() {
        swipeRefreshLayoutQa.isRefreshing = false
        pageNum = 1
        loadMore(true,pageNum)
    }
}