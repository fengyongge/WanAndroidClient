package com.fengyongge.wanandroidclient.activity.channel

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.fengyongge.baselib.mvp.BaseMvpActivity
import com.fengyongge.baselib.net.exception.ResponseException
import com.fengyongge.baselib.utils.DialogUtils
import com.fengyongge.baselib.utils.ToastUtils
import com.fengyongge.wanandroidclient.App
import com.fengyongge.wanandroidclient.R
import com.fengyongge.wanandroidclient.bean.GankGirlBean
import com.fengyongge.wanandroidclient.bean.GirlItemData
import com.fengyongge.wanandroidclient.common.view.ScaleImageView
import com.fengyongge.wanandroidclient.constant.Const
import com.fengyongge.wanandroidclient.mvp.contract.GirlContract
import com.fengyongge.wanandroidclient.mvp.presenterImpl.GirlPresenterImpl
import com.zzti.fengyongge.imagepicker.ImagePickerInstance
import com.zzti.fengyongge.imagepicker.model.PhotoModel
import kotlinx.android.synthetic.main.activity_girl.*

/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class GirlActivity : BaseMvpActivity<GirlPresenterImpl>(),GirlContract.view,SwipeRefreshLayout.OnRefreshListener{

    private lateinit var adapter: MyAdapter
    private var list = mutableListOf<GirlItemData>()
    private var isRefresh: Boolean = false
    private var totleCount: Int = 0
    private var pageNum: Int = 1

    override fun initPresenter(): GirlPresenterImpl {
        return GirlPresenterImpl()
    }

    override fun initLayout(): Int {
        return R.layout.activity_girl
    }

    override fun initView() {
        initTitle()
        swipeRefreshLayoutGirl.setOnRefreshListener(this)
        swipeRefreshLayoutGirl.setColorSchemeColors(ContextCompat.getColor(this,R.color.colorPrimary))
        recycleViewGirl.layoutManager = StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL)
        adapter =
            MyAdapter()
        recycleViewGirl.adapter = adapter
        adapter.loadMoreModule.setOnLoadMoreListener {
            if(pageNum * Const.PAGE_SIZE >= totleCount){
                adapter.loadMoreModule.loadMoreComplete()
                adapter.loadMoreModule.loadMoreEnd()
            }else{
                pageNum++
                loadMore(false,pageNum)
            }
        }
        adapter.setOnItemClickListener { adapter, view, position ->
            var list = mutableListOf<PhotoModel>()
            var photoModel = PhotoModel()
             var girlItemData =  adapter.data[position] as GirlItemData
            photoModel.originalPath = girlItemData.url
            list.add(photoModel)
            ImagePickerInstance.getInstance().photoPreview(GirlActivity@this,list,0,true)
        }
        loadMore(true,1)
    }

    private fun initTitle(){
        var tvTitle = findViewById<TextView>(R.id.tvTitle)
        tvTitle?.text = "妹子"
        var ivLeft = findViewById<ImageView>(R.id.ivLeft)
        ivLeft.visibility = View.VISIBLE
        ivLeft.setBackgroundResource(R.drawable.ic_back)
        ivLeft.setOnClickListener { finish() }
    }

   private fun loadMore(isRefresh: Boolean,pageNum: Int){
        this.isRefresh = isRefresh
        mPresenter?.getGankGirl(pageNum, Const.PAGE_SIZE)
       if(isRefresh){
           DialogUtils.showProgress(GirlActivity@this,"数据加载中")
       }
    }


    class MyAdapter : BaseQuickAdapter<GirlItemData, BaseViewHolder>(R.layout.item_activity_girl),LoadMoreModule {
        override fun convert(holder: BaseViewHolder, item: GirlItemData) {
            var ivGirl = holder.getView<ScaleImageView>(R.id.ivGirl)
            item?.let {
                Glide.with(App.getContext())
                    .load(item.url)
                    .into(ivGirl)
            }
        }
    }

    override fun initData() {
    }


    override fun getGankGirlShow(data: GankGirlBean) {
        if(data.status == 100) {
            list = data.data as MutableList<GirlItemData>
            totleCount = data.total_counts
            if (list.isNotEmpty()) {
                adapter.loadMoreModule.loadMoreComplete()
                if(isRefresh){
                    adapter.setNewInstance(list)
                }else{
                    adapter.addData(list)
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
        }
    }

    private fun showEmptyView(){

    }

    override fun onError(data: ResponseException) {
        ToastUtils.showToast(GirlActivity@this,data.getErrorMessage())
        DialogUtils.dismissProgressMD()
    }

    override fun onRefresh() {
        swipeRefreshLayoutGirl.isRefreshing = false
        pageNum=1
        loadMore(true,pageNum)
    }
}