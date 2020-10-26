package com.fengyongge.wanandroidclient.activity

import android.text.TextUtils
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
import com.fengyongge.androidcommonutils.ktutils.DialogUtils
import com.fengyongge.androidcommonutils.ktutils.ToastUtils
import com.fengyongge.baselib.mvp.BaseMvpActivity
import com.fengyongge.rxhttp.bean.BaseResponse
import com.fengyongge.rxhttp.exception.ResponseException
import com.fengyongge.wanandroidclient.R
import com.fengyongge.wanandroidclient.bean.MyShareBean
import com.fengyongge.wanandroidclient.bean.MyShareData
import com.fengyongge.wanandroidclient.constant.Const
import com.fengyongge.wanandroidclient.mvp.contract.ShareContract
import com.fengyongge.wanandroidclient.mvp.presenterImpl.SharePresenterImpl
import kotlinx.android.synthetic.main.activity_my_share.*

/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class MyShareActivity : BaseMvpActivity<SharePresenterImpl>(),ShareContract.View,SwipeRefreshLayout.OnRefreshListener {
    var list = mutableListOf<MyShareData>()
    private lateinit var myAdapter: MyAdapter
    private var totleCount = 0
    private var pageNum = 1
    private var isRefresh = false
    private var collectPosition: Int = 0

    override fun initLayout(): Int {
        return R.layout.activity_my_share
    }

    override fun initView() {
        initTitle()
        swipeRefreshLayoutMyShare.setOnRefreshListener(this)
        swipeRefreshLayoutMyShare.setColorSchemeColors(
            ContextCompat.getColor(
                this,
                R.color.colorPrimary
            )
        )
        rvMyShare.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        myAdapter = MyAdapter(list)
        rvMyShare.adapter = myAdapter
        myAdapter.loadMoreModule.apply {
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
        myAdapter.apply {
            setOnItemClickListener { _, _, position ->
                startActivity(WebViewActivity.getIntent(this@MyShareActivity,myAdapter.data[position].link,"我的分享"))
            }
            setOnItemLongClickListener { adapter, view, position ->
                collectPosition = position
                deleteItem(myAdapter.data[position].title,myAdapter.data[position].id)
                true
            }
        }
        loadMore(true,0)
    }

    private fun deleteItem(title: String,id: Int){
        DialogUtils.showAlertDialog(
            this, "确定", "取消", null, "确定删除${title}吗",
            object : DialogUtils.Companion.OnOkClickListener{
                override fun onOkClick() {
                    mPresenter?.postDeleteMyShare(id)
                }
            },
            object : DialogUtils.Companion.OnCancelClickListener{
                override fun onCancelClick() {
                    DialogUtils.dismissAlertDialog()
                }
            })
    }


    private fun initTitle(){
        var tvTitle = findViewById<TextView>(R.id.tvTitle)
        tvTitle?.text = "我的分享"
        var ivLeft = findViewById<ImageView>(R.id.ivLeft)
        ivLeft.visibility = View.VISIBLE
        ivLeft.setBackgroundResource(R.drawable.ic_back)
        ivLeft.setOnClickListener { finish() }
    }

    private fun loadMore(isRefresh: Boolean, pageNum: Int) {
        this.isRefresh = isRefresh
        if(isRefresh){
            DialogUtils.showProgress(MyShareActivity@this,getString(R.string.load_hint1))
        }
        mPresenter?.getShareList(pageNum)
    }
    

    override fun initData() {
    }

    override fun initPresenter(): SharePresenterImpl {
        return SharePresenterImpl()
    }

    override fun postShareShow(data: BaseResponse<String>) {
    }

    override fun getShareListShow(data: BaseResponse<MyShareBean>) {

        if (data.errorCode == "0") {
            list = data.data.shareArticles.datas as MutableList<MyShareData>
            totleCount = data.data.shareArticles.total
            if (list.isNotEmpty()) {
                myAdapter.loadMoreModule.loadMoreComplete()
                if (isRefresh) {
                    myAdapter.setNewInstance(list)
                } else {
                    myAdapter.addData(list)
                }
            } else {
                myAdapter.loadMoreModule.loadMoreEnd()
                if(pageNum==1){
                    showEmpty()
                }
            }
            DialogUtils.dismissProgressMD()
        } else {
            DialogUtils.dismissProgressMD()
            ToastUtils.showToast(this, data.errorMsg)
        }
    }

    private fun showEmpty(){
        myAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.activity_common_empty,null))
    }


    override fun postDeleteMyShareShow(data: BaseResponse<String>) {
        if(data.errorCode == "0"){
            myAdapter.removeAt(collectPosition)
        }
    }

    override fun onError(data: ResponseException) {
        ToastUtils.showToast(ArticleSearchActivity@this,data.getErrorMessage())
        DialogUtils.dismissProgressMD()
    }

    class MyAdapter(data: MutableList<MyShareData>?) :
        BaseQuickAdapter<MyShareData, BaseViewHolder>(
            R.layout.item_activity_my_share,
            data
        ),
        LoadMoreModule {
        override fun convert(holder: BaseViewHolder, item: MyShareData) {
            var tvMyShareType = holder.getView<TextView>(R.id.tvMyShareType)
            var tvMyShareContent = holder.getView<TextView>(R.id.tvMyShareContent)
            var tvMyShareAuthor = holder.getView<TextView>(R.id.tvMyShareAuthor)
            var tvMyShareTime = holder.getView<TextView>(R.id.tvMyShareTime)
            tvMyShareContent.text = item.title
            tvMyShareTime.text = item.niceDate
            if(TextUtils.isEmpty(item.author)){
                tvMyShareAuthor.visibility = View.GONE
            }else{
                tvMyShareAuthor.visibility = View.VISIBLE
                tvMyShareAuthor.text = item.author
            }
            tvMyShareType.text = item.chapterName
        }

    }

    override fun onRefresh() {
        swipeRefreshLayoutMyShare.isRefreshing = false
        pageNum = 1
        loadMore(true,pageNum)
    }
}