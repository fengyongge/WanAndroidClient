package com.fengyongge.wanandroidclient.activity

import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.Html
import android.text.TextUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
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
import com.fengyongge.wanandroidclient.bean.WxAccountBeanItem
import com.fengyongge.wanandroidclient.bean.WxAccountSearchBean
import com.fengyongge.wanandroidclient.bean.WxHistoryBean
import com.fengyongge.wanandroidclient.bean.WxSearchData
import com.fengyongge.basecomponent.constant.Const
import com.fengyongge.wanandroidclient.mvp.contract.WxAccountContact
import com.fengyongge.wanandroidclient.mvp.presenterImpl.WxAccountPresenterImpl
import kotlinx.android.synthetic.main.activity_common_search_title.*
import kotlinx.android.synthetic.main.activity_wx_account_search.*


/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class WxAccountSearchActivity : BaseMvpActivity<WxAccountPresenterImpl>(), WxAccountContact.View,SwipeRefreshLayout.OnRefreshListener{

    private  var wxAccountId = 0
    private  var searchContent =""
    private var isRefresh = false
    private var totleCount = 0
    private var pageNum = 1
    lateinit var contentAdapter: ContentAdapter
    private var contentList = mutableListOf<WxSearchData>()
    private var collectPosition: Int = 0

    override fun initPresenter(): WxAccountPresenterImpl {
        return WxAccountPresenterImpl()
    }

    override fun initLayout(): Int {
        return R.layout.activity_wx_account_search
    }

    override fun initView() {
        ivSearchBack.setOnClickListener { finish() }

        swipeRefreshLayoutWxSearch.setOnRefreshListener(this)
        swipeRefreshLayoutWxSearch.setColorSchemeColors(ContextCompat.getColor(this,R.color.colorPrimary))
        recycleViewWxSearch.layoutManager =
            LinearLayoutManager(WxAccountSearchActivity@this, LinearLayoutManager.VERTICAL, false)
        contentAdapter = ContentAdapter(contentList)
        recycleViewWxSearch.adapter = contentAdapter

        contentAdapter.loadMoreModule?.apply {
            setOnLoadMoreListener {
                if (pageNum * Const.PAGE_SIZE >= totleCount) {
                    loadMoreComplete()
                    loadMoreEnd()
                } else {
                    pageNum++
                    loadMore(false, pageNum)
                }
            }
        }

        contentAdapter.apply {
            addChildClickViewIds(R.id.ivWxAccountCollect)
            setOnItemClickListener { adapter, view, position ->
                startActivity( WebViewActivity.getIntent(this@WxAccountSearchActivity,contentAdapter.data[position].link,"${contentAdapter.data[position].chapterName}文章搜索"))
            }
            setOnItemChildClickListener { adapter, view, position ->
                when(view?.id){
                    R.id.ivWxAccountCollect ->{
                        var wxSearchData = adapter.data[position] as WxSearchData
                        collectPosition = position
                        if(wxSearchData.collect){
                            wxSearchData.collect = false
                            mPresenter?.postCancleCollect(contentAdapter.data[position].id)
                        }else{
                            wxSearchData.collect = true
                            mPresenter?.postCollect(contentAdapter.data[position].id)
                        }
                    }
                }
            }
        }

        getIntentParms()

        onClick()
    }

    private fun onClick(){
        etSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchContent = etSearch.text.toString()
                if (TextUtils.isEmpty(searchContent)) {
                    ToastUtils.showToast(this@WxAccountSearchActivity, "搜索内容不能为空")
                } else {
                    mPresenter?.getSearchWxContent(wxAccountId,pageNum,searchContent)
                }
            }
            false
        }

        ivSearchClose.setOnClickListener {
            etSearch.text = Editable.Factory.getInstance().newEditable("")
            hideKeyboard(this)
        }
    }

    /**
     * 打开关闭相互切换
     * @param activity
     */
    fun hideKeyboard(activity: Activity) {
        val imm =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive) {
            if (activity.currentFocus!!.windowToken != null) {
                imm.hideSoftInputFromWindow(
                    activity.currentFocus!!.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }
    }

    private fun loadMore(isRefresh: Boolean, pageNum: Int) {
        this.isRefresh = isRefresh
        mPresenter?.getSearchWxContent( wxAccountId, pageNum,searchContent)
        if(isRefresh){
            DialogUtils.showProgress(this,getString(R.string.load_hint1))
        }
    }

    private fun getIntentParms(){
        wxAccountId = intent.getIntExtra("wxAccountId",0)
    }

    override fun initData() {
    }


    override fun getSearchWxContentShow(data: BaseResponse<WxAccountSearchBean>) {
        if (data.errorCode == "0") {
            contentList = data.data.datas as MutableList<WxSearchData>
            if (contentList.isNotEmpty()) {
                totleCount = data.data.total
                contentAdapter.loadMoreModule.loadMoreComplete()
                if (isRefresh) {
                    contentAdapter.setNewInstance(contentList)
                } else {
                    contentAdapter.addData(contentList)
                }
            } else {
                contentAdapter.loadMoreModule.loadMoreEnd()
                showEmpty()
            }
            DialogUtils.dismissProgressMD()
        } else {
            DialogUtils.dismissProgressMD()
            ToastUtils.showToast(WxAccountSearchActivity@this, data.errorMsg)
        }
    }

    private fun showEmpty() {
        ToastUtils.showToast(WxAccountSearchActivity@this,"暂无搜索结果")
    }


    override fun postCollectShow(data: BaseResponse<String>) {
        if (data.errorCode == "0") {
            ToastUtils.showToast(this, getString(R.string.collect_success))
            contentAdapter.notifyItemChanged(collectPosition)
        }else{
            ToastUtils.showToast(this,data.errorMsg)
        }
    }

    override fun postCancleCollectShow(data: BaseResponse<String>) {
        if (data.errorCode == "0") {
            ToastUtils.showToast(this, getString(R.string.collect_cancle))
            contentAdapter.notifyItemChanged(collectPosition)
        }else{
            ToastUtils.showToast(this,data.errorMsg)
        }
    }
    override fun getWxAccountShow(data: BaseResponse<List<WxAccountBeanItem>>) {
    }

    override fun getWxHistoryList(data: BaseResponse<WxHistoryBean>) {

    }

    override fun getWxHistoryListFail(e: ResponseException) {
        TODO("Not yet implemented")
    }

    override fun onError(e: ResponseException) {
        DialogUtils.dismissProgressMD()
        ToastUtils.showToast(WxAccountSearchActivity@this,e.getErrorMessage())
    }

    class ContentAdapter(data: MutableList<WxSearchData>?) :
        BaseQuickAdapter<WxSearchData, BaseViewHolder>(R.layout.item_fragment_wxaccount_content, data),
        LoadMoreModule {
        override fun convert(holder: BaseViewHolder, item: WxSearchData) {
            var ivWxAccountCollect = holder.getView<ImageView>(R.id.ivWxAccountCollect)
            var tvWxAccountTitle = holder.getView<TextView>(R.id.tvWxAccountTitle)
            var tvWxAccountTime = holder.getView<TextView>(R.id.tvWxAccountTime)
            var filtTitle = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                Html.fromHtml(item.title, Html.FROM_HTML_MODE_LEGACY).toString()
            } else {
                Html.fromHtml(item.title).toString()
            }
            tvWxAccountTitle.text = filtTitle
            tvWxAccountTime.text = item.niceDate
            if (item.collect) {
                ivWxAccountCollect.setImageResource(R.drawable.ic_collect_fill)
            } else {
                ivWxAccountCollect.setImageResource(R.drawable.ic_collect)
            }
        }
    }

    override fun onRefresh() {
        swipeRefreshLayoutWxSearch.isRefreshing = false
        if (TextUtils.isEmpty(searchContent)) {
            ToastUtils.showToast(this@WxAccountSearchActivity, "搜索内容不能为空")
        } else {
            pageNum = 1
            loadMore(true, pageNum)    }
        }
}