package com.fengyongge.wanandroidclient.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.fengyongge.baselib.mvp.BaseMvpFragment
import com.fengyongge.baselib.net.BaseResponse
import com.fengyongge.baselib.net.exception.ResponseException
import com.fengyongge.baselib.utils.DialogUtils
import com.fengyongge.baselib.utils.ToastUtils
import com.fengyongge.wanandroidclient.R
import com.fengyongge.wanandroidclient.activity.WebViewActivity
import com.fengyongge.wanandroidclient.activity.WxAccountSearchActivity
import com.fengyongge.wanandroidclient.bean.DataItem
import com.fengyongge.wanandroidclient.bean.WxAccountBeanItem
import com.fengyongge.wanandroidclient.bean.WxAccountSearchBean
import com.fengyongge.wanandroidclient.bean.WxHistoryBean
import com.fengyongge.wanandroidclient.constant.Const
import com.fengyongge.wanandroidclient.mvp.contract.WxAccountContact
import com.fengyongge.wanandroidclient.mvp.presenterImpl.WxAccountPresenterImpl
import kotlinx.android.synthetic.main.fragment_wxaccount.*

/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class WxAccountFragment : BaseMvpFragment<WxAccountPresenterImpl>(), WxAccountContact.View,
    SwipeRefreshLayout.OnRefreshListener {

    lateinit var categoryAdapter: CategoryAdapter
    lateinit var contentAdapter: ContentAdapter
    private var categotyList = mutableListOf<WxAccountBeanItem>()
    private var contentList = mutableListOf<DataItem>()
    private var wxAccountId = 0
    private var isRefresh = false
    private var totleCount = 0
    private var pageNum = 1
    private var collectPosition: Int = 0


    override fun initPresenter(): WxAccountPresenterImpl {
        return WxAccountPresenterImpl()
    }

    override fun initLayout(): Int {
        return R.layout.fragment_wxaccount
    }

    override fun initView() {
        swipeRefreshLayoutWxAccount.setOnRefreshListener(this)
        activity?.let { ContextCompat.getColor(it, R.color.colorPrimary) }?.let {
            swipeRefreshLayoutWxAccount.setColorSchemeColors(
                it
            )
        }

        rvCategory.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        categoryAdapter = CategoryAdapter(categotyList)
        rvCategory.adapter = categoryAdapter

        rvContent.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        contentAdapter = ContentAdapter(contentList)
        rvContent.adapter = contentAdapter

        categoryAdapter.setOnItemClickListener { adapter, view, position ->
            for (index in categoryAdapter.data.indices) {
                if (index == position) {
                    categoryAdapter.data[position].choose = true
                } else {
                    categoryAdapter.data[index].choose = false
                }
            }
            categoryAdapter.notifyDataSetChanged()
            wxAccountId = categoryAdapter.data[position].id
            loadMore(true, 1)
        }

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
                startActivity(activity?.let {
                    WebViewActivity.getIntent(
                        it,
                        contentAdapter.data[position].link
                    )
                })
            }

            setOnItemChildClickListener(object : OnItemChildClickListener{
                override fun onItemChildClick(
                    adapter: BaseQuickAdapter<*, *>,
                    view: View,
                    position: Int
                ) {
                    when(view?.id){
                        R.id.ivWxAccountCollect ->{
                            var dataItem = adapter.data[position] as DataItem
                            collectPosition = position
                            if(dataItem.collect){
                                dataItem.collect = false
                                mPresenter?.postCancleCollect(contentAdapter.data[position].id)
                            }else{
                                dataItem.collect = true
                                mPresenter?.postCollect(contentAdapter.data[position].id)
                            }
                        }
                    }
                }
            })
        }

    }


    override fun initData() {
    }

    override fun initLoad() {
        var tvTitle = activity?.findViewById<TextView>(R.id.tvTitle)
        tvTitle?.text = "公众号"
        var ivRight = activity?.findViewById<ImageView>(R.id.ivRight)
        ivRight?.visibility = View.VISIBLE
        ivRight?.setBackgroundResource(R.drawable.ic_search_blue)
        ivRight?.setOnClickListener {
            var intent = Intent(activity, WxAccountSearchActivity::class.java)
            intent.putExtra("wxAccountId",wxAccountId)
            startActivity(intent)
        }
        mPresenter?.getWxAccount()
    }


    override fun getSearchWxContentShow(data: BaseResponse<WxAccountSearchBean>) {
        TODO("Not yet implemented")
    }

    override fun postCollectShow(data: BaseResponse<String>) {
        if (data.errorCode == "0") {
            ToastUtils.showToast(activity, "收藏成功")
            contentAdapter.notifyItemChanged(collectPosition)
        }else{
            ToastUtils.showToast(activity,data.errorMsg)
        }
    }

    override fun postCancleCollectShow(data: BaseResponse<String>) {
        if (data.errorCode == "0") {
            ToastUtils.showToast(activity, "取消收藏成功")
            contentAdapter.notifyItemChanged(collectPosition)
        }else{
            ToastUtils.showToast(activity,data.errorMsg)
        }
    }

    override fun getWxAccountShow(data: BaseResponse<List<WxAccountBeanItem>>) {
        if (data.errorCode == "0") {
            categotyList = data.data as MutableList<WxAccountBeanItem>
            if (categotyList.isNotEmpty()) {
                categoryAdapter.setNewInstance(categotyList)
                wxAccountId = categotyList[0].id
                categotyList[0].choose = true
                loadMore(true, 1)
            }
        } else {
            ToastUtils.showToast(activity, data.errorMsg)
        }
    }

    override fun getWxHistoryList(data: BaseResponse<WxHistoryBean>) {
        if (data.errorCode == "0") {
            contentList = data.data.datas as MutableList<DataItem>
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
            ToastUtils.showToast(activity, data.errorMsg)
        }
    }

    private fun showEmpty() {
        contentAdapter.setEmptyView(LayoutInflater.from(activity).inflate(R.layout.activity_common_empty,null))
    }

    override fun onError(e: ResponseException) {
        DialogUtils.dismissProgressMD()
        ToastUtils.showToast(activity,e.getErrorMessage())
    }

    class CategoryAdapter(data: MutableList<WxAccountBeanItem>?) :
        BaseQuickAdapter<WxAccountBeanItem, BaseViewHolder>(
            R.layout.item_fragment_wxaccount_category,
            data
        ) {
        override fun convert(holder: BaseViewHolder, item: WxAccountBeanItem) {
            var indicatorView = holder.getView<View>(R.id.indicatorView)
            var tvCategoryName = holder.getView<TextView>(R.id.tvCategoryName)
            tvCategoryName.text = item.name
            if (item.choose) {
                tvCategoryName.isSelected = true
                indicatorView.visibility = View.VISIBLE
            } else {
                tvCategoryName.isSelected = false
                indicatorView.visibility = View.GONE
            }
        }

    }

    class ContentAdapter(data: MutableList<DataItem>?) :
        BaseQuickAdapter<DataItem, BaseViewHolder>(R.layout.item_fragment_wxaccount_content, data),
        LoadMoreModule {
        override fun convert(holder: BaseViewHolder, item: DataItem) {
            var ivWxAccountCollect = holder.getView<ImageView>(R.id.ivWxAccountCollect)
            var tvWxAccountTitle = holder.getView<TextView>(R.id.tvWxAccountTitle)
            var tvWxAccountTime = holder.getView<TextView>(R.id.tvWxAccountTime)
            tvWxAccountTitle.text = item.title
            tvWxAccountTime.text = item.niceDate
            if (item.collect) {
                ivWxAccountCollect.setImageResource(R.drawable.ic_collect_fill)
            } else {
                ivWxAccountCollect.setImageResource(R.drawable.ic_collect)
            }
        }
    }

    private fun loadMore(isRefresh: Boolean, pageNum: Int) {
        this.isRefresh = isRefresh
        mPresenter?.getWxHistoryList("" + wxAccountId, pageNum)
        if(isRefresh){
            activity?.let { DialogUtils.showProgress(it,"数据加载中") }
        }
    }


    override fun onRefresh() {
        swipeRefreshLayoutWxAccount.isRefreshing = false
        pageNum = 1
        loadMore(true, pageNum)
    }

}