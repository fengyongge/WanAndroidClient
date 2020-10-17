package com.fengyongge.wanandroidclient.activity

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
import com.fengyongge.wanandroidclient.bean.CollectDataItem
import com.fengyongge.wanandroidclient.bean.MyCollectBean
import com.fengyongge.wanandroidclient.constant.Const
import com.fengyongge.wanandroidclient.mvp.contract.MyCollectContact
import com.fengyongge.wanandroidclient.mvp.presenterImpl.MyCollectPresenterImpl
import kotlinx.android.synthetic.main.activity_collect_article.*

/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class MyCollectArticleActivity : BaseMvpActivity<MyCollectPresenterImpl>(),
    MyCollectContact.View, SwipeRefreshLayout.OnRefreshListener {

    var list = mutableListOf<CollectDataItem>()
    private lateinit var myAdapter: MyAdapter
    private var offset = 0
    private var pageNum = 0
    private var isRefresh = false
    private var collectPosition: Int = 0

    override fun initLayout(): Int {
        return R.layout.activity_collect_article
    }

    override fun initView() {
        initTitle()
        swipeRefreshLayoutMyCollect.setOnRefreshListener(this)
        swipeRefreshLayoutMyCollect.setColorSchemeColors(
            ContextCompat.getColor(
                this,
                R.color.colorPrimary
            )
        )
        rvMyCollect.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        myAdapter = MyAdapter(list)
        rvMyCollect.adapter = myAdapter
        myAdapter.loadMoreModule.apply {
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
        myAdapter.apply {
            addChildClickViewIds(R.id.ivMyCollect)
            setOnItemClickListener { _, _, position ->
                startActivity(WebViewActivity.getIntent(this@MyCollectArticleActivity,myAdapter.data[position].link))
            }
            setOnItemChildClickListener { _, view, position ->
                when (view?.id) {
                    R.id.ivMyCollect -> {
                        collectPosition = position
                        mPresenter?.postCancleCollect(""+myAdapter.data[position].id,myAdapter.data[position].originId)
                    }
                }
            }
        }
        loadMore(true,0)
    }
    private fun initTitle(){
        var tvTitle = findViewById<TextView>(R.id.tvTitle)
        tvTitle?.text = "我的收藏"
        var ivLeft = findViewById<ImageView>(R.id.ivLeft)
        ivLeft.visibility = View.VISIBLE
        ivLeft.setBackgroundResource(R.drawable.ic_back)
        ivLeft.setOnClickListener { finish() }
    }

    private fun loadMore(isRefresh: Boolean, pageNum: Int) {
        this.isRefresh = isRefresh
        mPresenter?.getCollectList(pageNum)

    }

    override fun initData() {

    }

    override fun initPresenter(): MyCollectPresenterImpl {
        return MyCollectPresenterImpl()
    }

    override fun getCollectListShow(data: BaseResponse<MyCollectBean>) {
        if (data.errorCode == "0") {
            list = data.data.datas as MutableList<CollectDataItem>
            offset = data.data.offset
            if (list.isNotEmpty()) {
                myAdapter.loadMoreModule.loadMoreComplete()
                if (isRefresh) {
                    myAdapter.setNewInstance(list)
                } else {
                    myAdapter.addData(list)
                }
            } else {
                myAdapter.loadMoreModule.loadMoreEnd()
                if(pageNum==0){
                    showNotEmpty()
                }

            }

        } else {
            ToastUtils.showToast(this, data.errorMsg)
        }
    }

    private fun showNotEmpty(){

    }

    override fun postCancleCollectShow(data: BaseResponse<String>) {
        if(data.errorCode == "0"){
            myAdapter.removeAt(collectPosition)
            ToastUtils.showToast(this, getString(R.string.collect_cancle))

        }else{
            ToastUtils.showToast(this, data.errorMsg)
        }
    }

    override fun onError(data: ResponseException) {
        ToastUtils.showToast(this, data.getErrorMessage())
        DialogUtils.dismissProgressMD()
    }

    class MyAdapter(data: MutableList<CollectDataItem>?) :
        BaseQuickAdapter<CollectDataItem, BaseViewHolder>(
            R.layout.item_activity_collect_article,
            data
        ),
        LoadMoreModule {
        override fun convert(holder: BaseViewHolder, item: CollectDataItem) {
            var ivMyCollect = holder.getView<ImageView>(R.id.ivMyCollect)
            var tvMyCollectContent = holder.getView<TextView>(R.id.tvMyCollectContent)
            var tvMyCollectTime = holder.getView<TextView>(R.id.tvMyCollectTime)
            tvMyCollectContent.text = item.title
            tvMyCollectTime.text = item.niceDate
            ivMyCollect.setImageResource(R.drawable.ic_collect_fill)
        }

    }

    override fun onRefresh() {
        swipeRefreshLayoutMyCollect.isRefreshing = false
        pageNum = 0
        loadMore(true,pageNum)
    }
}