package com.fengyongge.wanandroidclient.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.fengyongge.baselib.mvp.BaseMvpFragment
import com.fengyongge.baselib.net.BaseResponse
import com.fengyongge.baselib.net.exception.ResponseException
import com.fengyongge.baselib.utils.DialogUtils
import com.fengyongge.baselib.utils.TimeUtils
import com.fengyongge.baselib.utils.ToastUtils
import com.fengyongge.wanandroidclient.R
import com.fengyongge.wanandroidclient.activity.WebViewActivity
import com.fengyongge.wanandroidclient.bean.Data
import com.fengyongge.wanandroidclient.bean.ProjectBean
import com.fengyongge.wanandroidclient.bean.ProjectTypeBeanItem
import com.fengyongge.wanandroidclient.constant.Const
import com.fengyongge.wanandroidclient.mvp.contract.ProjectContract
import com.fengyongge.wanandroidclient.mvp.presenterImpl.ProjectPresenterImpl
import kotlinx.android.synthetic.main.fragment_project_item.*
/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class ProjectItemFragment: BaseMvpFragment<ProjectPresenterImpl>(),SwipeRefreshLayout.OnRefreshListener,ProjectContract.View{

    private lateinit var myAdapter: MyAdapter
    private var list = mutableListOf<Data>()
    private var cid = 0
    private var totleCount = 0
    private var pageNum = 1
    private var isRefresh = false
    private var collectPosition: Int = 0

    companion object{
        private const val CID = "cid"
        fun newInstance(cid: Int)= ProjectItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(CID,cid)
                }
            }
    }

    override fun initLayout(): Int {
        return R.layout.fragment_project_item
    }


    override fun initPresenter(): ProjectPresenterImpl {
        return ProjectPresenterImpl()
    }

    override fun initView() {
        swipeRefreshLayoutProjectItem.setOnRefreshListener(this)
        context?.let {
            swipeRefreshLayoutProjectItem.setColorSchemeColors(
                ContextCompat.getColor(
                    it,
                    R.color.colorPrimary
                )
            )
        }
        rvProject.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        myAdapter = MyAdapter()
        rvProject.adapter = myAdapter

        myAdapter.loadMoreModule?.apply {
            setOnLoadMoreListener {
                if(pageNum*Const.PAGE_SIZE_15 >= totleCount){
                    loadMoreComplete()
                    loadMoreEnd()
                }else{
                    pageNum++
                    loadMore(false,pageNum)
                }
            }
        }

        myAdapter.apply {
            addChildClickViewIds(R.id.ivProjectCollect)
            setOnItemClickListener { _, _, position ->
                startActivity(activity?.let { WebViewActivity.getIntent(it,myAdapter.data[position].link,myAdapter.data[position].title) })
            }
            setOnItemChildClickListener { adapter, view, position ->
                when (view?.id) {
                    R.id.ivProjectCollect -> {
                        var data = adapter.data[position] as Data
                        collectPosition = position
                        if(data.collect){
                            data.collect = false
                            mPresenter?.postCancleCollect(myAdapter.data[position].id)
                        }else{
                            data.collect = true
                            mPresenter?.postCollect(myAdapter.data[position].id)
                        }
                    }
                }
            }
        }

    }

    override fun initData() {
        arguments?.let {
            cid = it.getInt(CID)
        }

    }

    override fun initLoad() {
        loadMore(true,1)
    }

    class MyAdapter: BaseQuickAdapter<Data, BaseViewHolder>(R.layout.fragment_project_item_item),LoadMoreModule {
        override fun convert(holder: BaseViewHolder, item: Data) {
            var tvTitle = holder.getView<TextView>(R.id.tvTitle)
            var tvContent = holder.getView<TextView>(R.id.tvContent)
            var tvTime = holder.getView<TextView>(R.id.tvTime)
            var ivProjectCollect = holder.getView<ImageView>(R.id.ivProjectCollect)
            var ivProjectCover = holder.getView<ImageView>(R.id.ivProjectCover)
            if(item.collect){
                ivProjectCollect.setImageResource(R.drawable.ic_collect_fill)
            }else{
                ivProjectCollect.setImageResource(R.drawable.ic_collect)
            }
            with(item){
                tvTitle.text = title
                tvContent.text = desc
                tvTime.text = TimeUtils.formatDateLongToString(publishTime,"yyyy-MM-dd HH:mm")
                Glide.with(context)
                    .load(envelopePic)
                    .error(R.drawable.default_project_img)
                    .placeholder(R.drawable.default_project_img)
                    .into(ivProjectCover)
            }
        }

    }

    override fun onRefresh() {
        swipeRefreshLayoutProjectItem.isRefreshing = false
        loadMore(true,1)
    }

    private fun loadMore(isRefresh: Boolean,pageNum: Int){
        this.isRefresh = isRefresh
        mPresenter?.getProjectByType(pageNum,""+cid)
    }


    override fun postCollectShow(data: BaseResponse<String>) {
        if (data.errorCode == "0") {
            ToastUtils.showToast(activity, getString(R.string.collect_success))
            myAdapter.notifyItemChanged(collectPosition)
        }else{
            ToastUtils.showToast(activity,data.errorMsg)
        }
    }

    override fun postCancleCollectShow(data: BaseResponse<String>) {
        if (data.errorCode == "0") {
            ToastUtils.showToast(activity, getString(R.string.collect_cancle))
            myAdapter.notifyItemChanged(collectPosition)
        }else{
            ToastUtils.showToast(activity,data.errorMsg)
        }
    }


    override fun getProjectTypeShow(data: BaseResponse<List<ProjectTypeBeanItem>>) {

    }

    override fun getProjectByType(data: BaseResponse<ProjectBean>) {
        if(data.errorCode == "0"){
            list = data.data.datas as MutableList<Data>
            if(list.isNotEmpty()){
                totleCount = data.data.total
                myAdapter.loadMoreModule.loadMoreComplete()
                if(isRefresh){
                    myAdapter.setNewInstance(list)
                }else{
                    myAdapter.addData(list)
                }
            }else{
                myAdapter.loadMoreModule.loadMoreEnd()
                if(pageNum ==1){
                    showEmptyView()
                }
            }
        }
    }

    private fun showEmptyView(){
        myAdapter.setEmptyView(LayoutInflater.from(activity).inflate(R.layout.activity_common_empty,null))
    }

    override fun onError(data: ResponseException) {
        ToastUtils.showToast(activity,data.getErrorMessage())
    }



}