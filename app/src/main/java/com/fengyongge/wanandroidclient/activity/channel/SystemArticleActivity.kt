package com.fengyongge.wanandroidclient.activity.channel

import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.fengyongge.androidcommonutils.ktutils.DialogUtils
import com.fengyongge.androidcommonutils.ktutils.ToastUtils
import com.fengyongge.baseframework.mvp.BaseMvpActivity
import com.fengyongge.rxhttp.bean.BaseResponse
import com.fengyongge.rxhttp.exception.ResponseException
import com.fengyongge.wanandroidclient.R
import com.fengyongge.wanandroidclient.activity.WebViewActivity
import com.fengyongge.wanandroidclient.bean.SystemArticleBean
import com.fengyongge.wanandroidclient.bean.SystemCategoryBean
import com.fengyongge.wanandroidclient.bean.SystemDataItem
import com.fengyongge.basecomponent.constant.Const
import com.fengyongge.wanandroidclient.mvp.contract.SystemContact
import com.fengyongge.wanandroidclient.mvp.presenterImpl.SystemPresenterImpl
import kotlinx.android.synthetic.main.activity_system_article.*

/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class SystemArticleActivity : BaseMvpActivity<SystemPresenterImpl>(), SystemContact.View,SwipeRefreshLayout.OnRefreshListener{

    private var offset = 0
    private var pageNum = 0
    private var isRefresh = false
    private var cid = 0
    private var titleName = ""
    private var systemArticleList = mutableListOf<SystemDataItem>()
    private lateinit var systemArticleAdapter: SystemArticleAdapter
    private var collectPosition: Int = 0


    override fun initLayout(): Int {
        return R.layout.activity_system_article
    }

    override fun initView() {
        initTitle()
        swipeRefreshLayoutSystemArticle.setOnRefreshListener(this)
        swipeRefreshLayoutSystemArticle.setColorSchemeColors(
            ContextCompat.getColor(
                this,
                R.color.colorPrimary
            )
        )

        recycleViewSystemArticle.layoutManager = LinearLayoutManager(NavigationActivity@this, LinearLayoutManager.VERTICAL,false)
        systemArticleAdapter =
            SystemArticleAdapter(
                systemArticleList
            )
        recycleViewSystemArticle.adapter = systemArticleAdapter

        systemArticleAdapter.loadMoreModule.apply {
            setOnLoadMoreListener {
                if(pageNum*Const.PAGE_SIZE < offset){
                    loadMoreComplete()
                    loadMoreEnd()
                }else{
                    pageNum++
                    loadMore(false,pageNum)
                }
            }
        }

        systemArticleAdapter.apply {

            addChildClickViewIds(R.id.ivSystemArticleCollect)

            setOnItemClickListener { adapter, view, position ->
                startActivity(
                    WebViewActivity.getIntent(
                        this@SystemArticleActivity,
                        systemArticleAdapter.data[position].link,"体系"
                    )
                )
            }

            setOnItemChildClickListener { adapter, view, position ->

                when (view?.id) {
                    R.id.ivSystemArticleCollect -> {
                        var systemDataItem = adapter.data[position] as SystemDataItem
                        collectPosition = position
                        if(systemDataItem.collect){
                            systemDataItem.collect = false
                            mPresenter?.postCancleCollect(systemArticleAdapter.data[position].id)
                        }else{
                            systemDataItem.collect = true
                            mPresenter?.postCollect(systemArticleAdapter.data[position].id)
                        }
                    }
                }
            }
        }
        loadMore(true,0)
    }

    private fun initTitle(){
        cid = intent.getIntExtra("cid",0)
        titleName = intent.getStringExtra("titleName")
        var tvTitle = findViewById<TextView>(R.id.tvTitle)
        tvTitle?.text = titleName
        var ivLeft = findViewById<ImageView>(R.id.ivLeft)
        ivLeft.visibility = View.VISIBLE
        ivLeft.setBackgroundResource(R.drawable.ic_back)
        ivLeft.setOnClickListener { finish() }
    }

    private fun loadMore(isRefresh: Boolean,pageNum: Int){
        this.isRefresh = isRefresh
        mPresenter?.getSystemArticle(pageNum,cid)
        if(isRefresh){
            DialogUtils.showProgress(SystemArticleActivity@this,getString(R.string.load_hint1))
        }
    }

    override fun initData() {

    }

    override fun initPresenter(): SystemPresenterImpl {
        return SystemPresenterImpl()
    }

    override fun postCollectShow(data: BaseResponse<String>) {
        if (data.errorCode == "0") {
            ToastUtils.showToast(this, getString(R.string.collect_success))
            systemArticleAdapter.notifyItemChanged(collectPosition)
        }else{
            ToastUtils.showToast(this,data.errorMsg)
        }
    }

    override fun postCancleCollectShow(data: BaseResponse<String>) {
        if (data.errorCode == "0") {
            ToastUtils.showToast(this, getString(R.string.collect_cancle))
            systemArticleAdapter.notifyItemChanged(collectPosition)
        }else{
            ToastUtils.showToast(this,data.errorMsg)
        }
    }


    override fun getSystemCategoryShow(data: BaseResponse<List<SystemCategoryBean>>) {

    }

    override fun getSystemArticleShow(data: BaseResponse<SystemArticleBean>) {

        if(data.errorCode == "0"){
            systemArticleList = data.data.datas as MutableList<SystemDataItem>
            if(systemArticleList.isNotEmpty()){
                offset = data.data.offset
                systemArticleAdapter.loadMoreModule.loadMoreComplete()
                if(isRefresh){
                    systemArticleAdapter.setNewInstance(systemArticleList)
                }else{
                    systemArticleAdapter.addData(systemArticleList)
                }
            }else{
                systemArticleAdapter.loadMoreModule.loadMoreEnd()
            }
            DialogUtils.dismissProgressMD()
        }else{
            DialogUtils.dismissProgressMD()
            ToastUtils.showToast(SystemArticleActivity@this,data.errorMsg)
        }
    }

    override fun getSystemArticleFail(data: ResponseException) {
        DialogUtils.dismissProgressMD()
        ToastUtils.showToast(SystemArticleActivity@this,data.getErrorMessage())
        systemArticleAdapter.loadMoreModule.loadMoreFail()
    }

    override fun onError(data: ResponseException) {
        ToastUtils.showToast(SystemArticleActivity@this,data.getErrorMessage())
        DialogUtils.dismissProgressMD()
    }

    class SystemArticleAdapter(data: List<SystemDataItem>?) : BaseQuickAdapter<SystemDataItem, BaseViewHolder>(R.layout.item_activity_system_article,
        data as MutableList<SystemDataItem>?
    ),LoadMoreModule{
        override fun convert(holder: BaseViewHolder, item: SystemDataItem) {
            var ivSystemArticleCollect = holder.getView<ImageView>(R.id.ivSystemArticleCollect)
            var tvSystemArticleContent = holder.getView<TextView>(R.id.tvSystemArticleContent)
            var tvSystemArticleTime = holder.getView<TextView>(R.id.tvSystemArticleTime)
            var filtTitle = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                Html.fromHtml(item.title, Html.FROM_HTML_MODE_LEGACY).toString()
            } else {
                Html.fromHtml(item.title).toString()
            }
            tvSystemArticleContent.text = filtTitle
            tvSystemArticleTime.text =item.niceDate
            if (item.collect) {
                ivSystemArticleCollect.setImageResource(R.drawable.ic_collect_fill)
            } else {
                ivSystemArticleCollect.setImageResource(R.drawable.ic_collect)
            }
        }
    }

    override fun onRefresh() {
        swipeRefreshLayoutSystemArticle.isRefreshing = false
        pageNum = 0
        loadMore(true,pageNum)
    }
}