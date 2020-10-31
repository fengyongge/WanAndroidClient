package com.fengyongge.wanandroidclient.activity

import android.text.Editable
import android.text.Html
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.donkingliang.labels.LabelsView
import com.fengyongge.androidcommonutils.ktutils.DialogUtils
import com.fengyongge.androidcommonutils.ktutils.ToastUtils
import com.fengyongge.baseframework.mvp.BaseMvpActivity
import com.fengyongge.rxhttp.bean.BaseResponse
import com.fengyongge.rxhttp.exception.ResponseException
import com.fengyongge.wanandroidclient.R
import com.fengyongge.wanandroidclient.bean.HotKeyBeanItem
import com.fengyongge.wanandroidclient.bean.SearchContentBean
import com.fengyongge.wanandroidclient.bean.SearchData
import com.fengyongge.wanandroidclient.common.db.AppDataBase
import com.fengyongge.wanandroidclient.common.db.SearchHistoryEntity
import com.fengyongge.basecomponent.constant.Const
import com.fengyongge.wanandroidclient.mvp.contract.SearchContact
import com.fengyongge.wanandroidclient.mvp.presenterImpl.SearchPresenterImpl
import kotlinx.android.synthetic.main.activity_article_search.*
import kotlinx.android.synthetic.main.activity_article_search_homepage.*
import kotlinx.android.synthetic.main.activity_common_search_title.*


/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class ArticleSearchActivity : BaseMvpActivity<SearchPresenterImpl>(), SearchContact.View,
    SwipeRefreshLayout.OnRefreshListener {

    private var offset = 0
    private var pageNum = 0
    private var isRefresh = false
    private var searchDataList = mutableListOf<SearchData>()
    private lateinit var articleSearchAdapter: ArticleSearchAdapter
    private var collectPosition: Int = 0
    private var searchContent = ""

    override fun initPresenter(): SearchPresenterImpl {
        return SearchPresenterImpl()
    }

    override fun initLayout(): Int {
        return R.layout.activity_article_search
    }

    override fun initView() {
        ivSearchBack.setOnClickListener { finish() }

        swipeRefreshLayoutSearch.setOnRefreshListener(this)
        swipeRefreshLayoutSearch.setColorSchemeColors(
            ContextCompat.getColor(
                this,
                R.color.colorPrimary
            )
        )
        recycleViewSearch.layoutManager =
            LinearLayoutManager(NavigationActivity@ this, LinearLayoutManager.VERTICAL, false)
        articleSearchAdapter = ArticleSearchAdapter(searchDataList)
        recycleViewSearch.adapter = articleSearchAdapter

        articleSearchAdapter.loadMoreModule.apply {
            setOnLoadMoreListener {
                if (pageNum * Const.PAGE_SIZE < offset) {
                    loadMoreComplete()
                    loadMoreEnd()
                } else {
                    pageNum++
                    loadMore(false, pageNum)
                }
            }
        }

        articleSearchAdapter.apply {

            addChildClickViewIds(R.id.ivSearchCollect)

            setOnItemClickListener { adapter, view, position ->
                startActivity(
                    WebViewActivity.getIntent(
                        this@ArticleSearchActivity,
                        articleSearchAdapter.data[position].link,"文章搜索"
                    )
                )
            }

            setOnItemChildClickListener { adapter, view, position ->
                when (view?.id) {
                    R.id.ivSearchCollect -> {
                        var searchData = adapter.data[position] as SearchData
                        collectPosition = position
                        if (searchData.collect) {
                            searchData.collect = false
                            mPresenter?.postCancleCollect(articleSearchAdapter.data[position].id)
                        } else {
                            searchData.collect = true
                            mPresenter?.postCollect(articleSearchAdapter.data[position].id)
                        }
                    }
                }
            }
        }
        onClick()
        var searchHistoryEntityList =
            AppDataBase.getInstance(this).serchHistoryDao().getSearchContent()
        searchHistoryLabels.setLabels(searchHistoryEntityList) { _, _, data -> data?.searchContent }
        mPresenter?.getHotKey()
    }

    override fun initData() {
    }

    private fun onClick() {

        ivSearchClose.setOnClickListener {
            scrollViewSearch.visibility = View.VISIBLE
            swipeRefreshLayoutSearch.visibility = View.GONE
            etSearch.text = Editable.Factory.getInstance().newEditable("")
        }
        searchLabels.setOnLabelClickListener { label, data, position ->
            var hotKeyBeanItem = data as HotKeyBeanItem
            searchContent = hotKeyBeanItem.name
            loadMore(true, 0)
            etSearch.text = Editable.Factory.getInstance().newEditable(searchContent)
        }

        searchHistoryLabels.setOnLabelClickListener { label, data, position ->
            var seachHistoryEntityList = data as SearchHistoryEntity
            searchContent = seachHistoryEntityList.searchContent.toString()
            loadMore(true, 0)
            etSearch.text = Editable.Factory.getInstance().newEditable(searchContent)
        }



        ivClearHistory.setOnClickListener {
            deleteNotify()
        }

        etSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchContent = etSearch.text.toString()
                    if (TextUtils.isEmpty(searchContent)) {
                        ToastUtils.showToast(this@ArticleSearchActivity, getString(R.string.search_hint))
                    } else {
                        loadMore(true, 0)
                    }
                }
                return false
            }
        })
    }

    private fun addNotify() {
        var searchHistoryEntityList =
            AppDataBase.getInstance(this).serchHistoryDao().getSearchContent()
        if (searchHistoryEntityList.size >= 10) {
            var search = SearchHistoryEntity(searchHistoryEntityList[0].uid, searchContent)
            AppDataBase.getInstance(this).serchHistoryDao().updateSearchContent(search)
            searchHistoryLabels.setLabels(AppDataBase.getInstance(this).serchHistoryDao().getSearchContent()) { _, _, data -> data?.searchContent }
        }else{
            var search = SearchHistoryEntity(0, searchContent)
            AppDataBase.getInstance(this).serchHistoryDao().insertSearchContent(search)
            searchHistoryLabels.setLabels(AppDataBase.getInstance(this).serchHistoryDao().getSearchContent()) { _, _, data -> data?.searchContent }
        }
    }

    private fun deleteNotify() {

        if(AppDataBase.getInstance(this@ArticleSearchActivity).serchHistoryDao().getSearchContent().size>0){
            DialogUtils.showAlertDialog(
                this, "是的,给老子删了", "草率了", null, "真的要删除搜索记录吗?",
                object : DialogUtils.Companion.OnOkClickListener {
                    override fun onOkClick() {

                        var searchHistoryEntityList =
                            AppDataBase.getInstance(this@ArticleSearchActivity).serchHistoryDao().getSearchContent()
                        for(item in searchHistoryEntityList){
                            AppDataBase.getInstance(this@ArticleSearchActivity).serchHistoryDao().deleteSearchContent(item)
                        }
                        searchHistoryLabels.setLabels( AppDataBase.getInstance(this@ArticleSearchActivity).serchHistoryDao().getSearchContent()) {
                                label, position, data -> data?.searchContent }
                    }
                },
                object : DialogUtils.Companion.OnCancelClickListener {
                    override fun onCancelClick() {
                        DialogUtils.dismissAlertDialog()
                    }
                })
        }else{
            ToastUtils.showToast(this,"胸弟,先搜索")
        }

    }


    override fun postCollectShow(data: BaseResponse<String>) {
        if (data.errorCode == "0") {
            ToastUtils.showToast(this, getString(R.string.collect_success))
            articleSearchAdapter.notifyItemChanged(collectPosition)
        } else {
            ToastUtils.showToast(this, data.errorMsg)
        }
    }

    override fun postCancleCollectShow(data: BaseResponse<String>) {
        if (data.errorCode == "0") {
            ToastUtils.showToast(this, getString(R.string.collect_cancle))
            articleSearchAdapter.notifyItemChanged(collectPosition)
        } else {
            ToastUtils.showToast(this, data.errorMsg)
        }
    }


    override fun getHotKeyShow(data: BaseResponse<List<HotKeyBeanItem>>) {
        if (data.errorCode == "0") {
            if (data.data.isNotEmpty()) {
                var hotKeyList = data.data
                searchLabels.setLabels(hotKeyList) { label, position, data -> data?.name }
            }
        }
    }

    override fun postSearchContentShow(data: BaseResponse<SearchContentBean>) {
        if (data.errorCode == "0") {
            searchDataList = data.data.datas as MutableList<SearchData>
            if (searchDataList.isNotEmpty()) {
                addNotify()
                scrollViewSearch.visibility = View.GONE
                swipeRefreshLayoutSearch.visibility = View.VISIBLE
                offset = data.data.offset
                articleSearchAdapter.loadMoreModule.loadMoreComplete()
                if (isRefresh) {
                    articleSearchAdapter.setNewInstance(searchDataList)
                } else {
                    articleSearchAdapter.addData(searchDataList)
                }
            } else {
                ToastUtils.showToast(this, "暂无相关内容")
                articleSearchAdapter.loadMoreModule.loadMoreEnd()
            }
        } else {
            ToastUtils.showToast(SystemArticleActivity@ this, data.errorMsg)
        }
    }

    override fun onError(data: ResponseException) {
        ToastUtils.showToast(ArticleSearchActivity@ this, data.getErrorMessage())
        DialogUtils.dismissProgressMD()
    }


    class ArticleSearchAdapter(data: List<SearchData>?) :
        BaseQuickAdapter<SearchData, BaseViewHolder>(
            R.layout.item_activity_article_search,
            data as MutableList<SearchData>?
        ), LoadMoreModule {
        override fun convert(holder: BaseViewHolder, item: SearchData) {
            var ivSearchCollect = holder.getView<ImageView>(R.id.ivSearchCollect)
            var tvSearchArticleContent = holder.getView<TextView>(R.id.tvSearchArticleContent)
            var tvSearchArticleAuthor = holder.getView<TextView>(R.id.tvSearchArticleAuthor)
            var tvSearchArticleType = holder.getView<TextView>(R.id.tvSearchArticleType)
            var tvSearchArticleTime = holder.getView<TextView>(R.id.tvSearchArticleTime)
            var tvSearchArticleNew = holder.getView<TextView>(R.id.tvSearchArticleNew)
            val lvSearchArticleTag = holder.getView<LabelsView>(R.id.lvSearchArticleTag)
            with(item){
                var filtTitle = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    Html.fromHtml(item.title, Html.FROM_HTML_MODE_LEGACY).toString()
                } else {
                    Html.fromHtml(item.title).toString()
                }
                tvSearchArticleContent.text = filtTitle
                tvSearchArticleAuthor.text = if(shareUser == "") author else shareUser
                tvSearchArticleTime.text = niceDate
                tvSearchArticleType.text = item.superChapterName+"/"+item.chapterName
                if(item.tags.isNotEmpty()){
                    lvSearchArticleTag.visibility = View.VISIBLE
                    lvSearchArticleTag.setLabels(item.tags) { _, _, data -> data?.name }
                }else{
                    lvSearchArticleTag.visibility = View.GONE
                }
                if (item.collect) {
                    ivSearchCollect.setImageResource(R.drawable.ic_collect_fill)
                } else {
                    ivSearchCollect.setImageResource(R.drawable.ic_collect)
                }
                if(item.fresh){
                    tvSearchArticleNew.visibility = View.VISIBLE
                }else{
                    tvSearchArticleNew.visibility = View.GONE
                }
            }

        }
    }

    private fun loadMore(isRefresh: Boolean, pageNum: Int) {
        this.isRefresh = isRefresh
        mPresenter?.postSearchContent(pageNum, searchContent)
    }


    override fun onRefresh() {
        swipeRefreshLayoutSearch.isRefreshing = false
        pageNum = 0
        mPresenter?.postSearchContent(pageNum, searchContent)
    }

}