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
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.fengyongge.baselib.mvp.BaseMvpFragment
import com.fengyongge.baselib.net.BaseResponse
import com.fengyongge.baselib.net.exception.ResponseException
import com.fengyongge.baselib.utils.DialogUtils
import com.fengyongge.baselib.utils.SharedPreferencesUtils
import com.fengyongge.baselib.utils.ToastUtils
import com.fengyongge.wanandroidclient.App
import com.fengyongge.wanandroidclient.R
import com.fengyongge.wanandroidclient.activity.ArticleSearchActivity
import com.fengyongge.wanandroidclient.activity.LoginActivity
import com.fengyongge.wanandroidclient.activity.ShareProjectActivity
import com.fengyongge.wanandroidclient.activity.WebViewActivity
import com.fengyongge.wanandroidclient.bean.SquareBean
import com.fengyongge.wanandroidclient.bean.SquareItemData
import com.fengyongge.wanandroidclient.constant.Const
import com.fengyongge.wanandroidclient.mvp.contract.SquareContract
import com.fengyongge.wanandroidclient.mvp.presenterImpl.SquarePresenterImpl
import kotlinx.android.synthetic.main.fragment_square.*
/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class SquareFragement : BaseMvpFragment<SquarePresenterImpl>(), SquareContract.View,
    SwipeRefreshLayout.OnRefreshListener {
    var list = mutableListOf<SquareItemData>()
    private lateinit var myAdapter: MyAdapter
    private var offset = 0
    private var pageNum = 0
    private var isRefresh = false
    private var collectPosition: Int = 0

    override fun initPresenter(): SquarePresenterImpl {
        return SquarePresenterImpl()
    }

    override fun initLayout(): Int {
        return R.layout.fragment_square
    }

    override fun initView() {
        activity?.let {
            swipeRefreshLayoutSquare.setOnRefreshListener(this)
            swipeRefreshLayoutSquare.setColorSchemeColors(
                ContextCompat.getColor(
                    it,
                    R.color.colorPrimary
                )
            )
        }
        recycleViewSquare.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        myAdapter = MyAdapter(list)
        recycleViewSquare.adapter = myAdapter
        myAdapter.loadMoreModule.apply {
            setOnLoadMoreListener {
                // TODO: 2020/9/24 api存在bug，在第几页时候获取
                if (pageNum * Const.PAGE_SIZE < offset) {
                    loadMoreComplete()
                    loadMoreEnd()
                } else {
                    pageNum++
                    loadMore(false, pageNum)
                }
            }
        }

        myAdapter.apply {
            addChildClickViewIds(R.id.ivLike)
            setOnItemClickListener { _, _, position ->
                activity?.let {
                    startActivity(WebViewActivity.getIntent(it,myAdapter.data[position].link))
                }
            }
            setOnItemChildClickListener { adapter, view, position ->
                when (view?.id) {
                    R.id.ivLike -> {
                        var squareItemData = adapter.data[position] as SquareItemData
                        collectPosition = position
                        if(squareItemData.collect){
                            squareItemData.collect = false
                            mPresenter?.postCancleCollect(myAdapter.data[position].id)
                        }else{
                            squareItemData.collect = true
                            mPresenter?.postCollect(myAdapter.data[position].id)
                        }
                    }
                }
            }
        }

        fabShare.setOnClickListener {
            if (SharedPreferencesUtils(App.getContext()).get(Const.IS_LOGIN, false)) {
                startActivity(Intent(activity, ShareProjectActivity::class.java))
            } else {
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }
    }

    override fun initData() {
    }

    override fun initLoad() {
        var tvTitle = activity?.findViewById<TextView>(R.id.tvTitle)
        tvTitle?.text = "广场"
        var ivRight = activity?.findViewById<ImageView>(R.id.ivRight)
        ivRight?.visibility = View.VISIBLE
        ivRight?.setBackgroundResource(R.drawable.ic_search_blue)
        loadMore(true, 0)
        ivRight?.setOnClickListener {
            startActivity(Intent(activity, ArticleSearchActivity::class.java))
        }
    }


    private fun loadMore(isRefresh: Boolean, pageNum: Int) {
        this.isRefresh = isRefresh
        if(isRefresh){
            activity?.let { DialogUtils.showProgress(it,"数据加载中") }
        }
        mPresenter?.getSquareProject(pageNum)
    }

    class MyAdapter(data: MutableList<SquareItemData>?) :
        BaseQuickAdapter<SquareItemData, BaseViewHolder>(R.layout.item_fragment_square, data),
        LoadMoreModule {
        override fun convert(holder: BaseViewHolder, item: SquareItemData) {
            var ivLike = holder.getView<ImageView>(R.id.ivLike)
            var tvSquareContent = holder.getView<TextView>(R.id.tvSquareContent)
            var tvSquareTime = holder.getView<TextView>(R.id.tvSquareTime)
            tvSquareContent.text = item.title
            tvSquareTime.text = item.niceDate
            if (item.collect) {
                ivLike.setImageResource(R.drawable.ic_collect_fill)
            } else {
                ivLike.setImageResource(R.drawable.ic_collect)
            }
        }
    }

    override fun postCollectShow(data: BaseResponse<String>) {
        if (data.errorCode == "0") {
            ToastUtils.showToast(activity, "收藏成功")
            myAdapter.notifyItemChanged(collectPosition)
        }else{
            ToastUtils.showToast(activity,data.errorMsg)
        }
    }

    override fun postCancleCollectShow(data: BaseResponse<String>) {
        if (data.errorCode == "0") {
            ToastUtils.showToast(activity, "取消收藏成功")
            myAdapter.notifyItemChanged(collectPosition)
        }else{
            ToastUtils.showToast(activity,data.errorMsg)
        }
    }

    override fun getSquareProjectShow(data: BaseResponse<SquareBean>) {
        if (data.errorCode == "0") {
            list = data.data.datas as MutableList<SquareItemData>
            if (list.isNotEmpty()) {
                offset = data.data.offset
                myAdapter.loadMoreModule.loadMoreComplete()
                if (isRefresh) {
                    myAdapter.setNewInstance(list)
                } else {
                    myAdapter.addData(list)
                }
            } else {
                myAdapter.loadMoreModule.loadMoreEnd()
                if (pageNum == 0) {
                    showEmptyView()
                }
            }
            DialogUtils.dismissProgressMD()
        } else {
            DialogUtils.dismissProgressMD()
            activity?.let { ToastUtils.showToast(it, data.errorMsg) }
        }
    }

    private fun showEmptyView() {
        myAdapter.setEmptyView(LayoutInflater.from(activity).inflate(R.layout.activity_common_empty,null))
    }


    override fun onError(data: ResponseException) {
        DialogUtils.dismissProgressMD()
        ToastUtils.showToast(activity,data.getErrorMessage())
    }

    override fun onRefresh() {
        swipeRefreshLayoutSquare.isRefreshing = false
        pageNum = 0
        loadMore(true, pageNum)
    }
}