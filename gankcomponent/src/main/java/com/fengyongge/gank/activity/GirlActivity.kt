package com.fengyongge.gank.activity

import android.view.LayoutInflater
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
import com.fengyongge.androidcommonutils.ktutils.DialogUtils
import com.fengyongge.androidcommonutils.ktutils.ToastUtils
import com.fengyongge.baseframework.mvp.BaseMvpActivity
import com.fengyongge.rxhttp.exception.ResponseException
import com.fengyongge.basecomponent.constant.Const
import com.fengyongge.basecomponent.app.BaseApplication
import com.fengyongge.gank.R
import com.fengyongge.gank.bean.GankGirlBean
import com.fengyongge.gank.bean.GirlItemData
import com.fengyongge.gank.mvp.contract.GirlContract
import com.fengyongge.gank.mvp.presenterImpl.GirlPresenterImpl
import com.fengyongge.gank.view.ScaleImageView
import com.zzti.fengyongge.imagepicker.ImagePickerInstance
import com.zzti.fengyongge.imagepicker.model.PhotoModel
import kotlinx.android.synthetic.main.gank_activity_girl.*

/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class GirlActivity : BaseMvpActivity<GirlPresenterImpl>(), GirlContract.view,SwipeRefreshLayout.OnRefreshListener{

    private lateinit var adapter: MyAdapter
    private var list = mutableListOf<GirlItemData>()
    private var isRefresh: Boolean = false
    private var totleCount: Int = 0
    private var pageNum: Int = 1

    override fun initPresenter(): GirlPresenterImpl {
        return GirlPresenterImpl()
    }

    override fun initLayout(): Int {
        return R.layout.gank_activity_girl
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
           DialogUtils.showProgress(GirlActivity@this,getString(R.string.gank_load_hint1))
       }
    }


    class MyAdapter : BaseQuickAdapter<GirlItemData, BaseViewHolder>(R.layout.gank_item_activity_girl),LoadMoreModule {
        override fun convert(holder: BaseViewHolder, item: GirlItemData) {
            var ivGirl = holder.getView<ScaleImageView>(R.id.ivGirl)
            item?.let {
                Glide.with(BaseApplication.getBaseApplicaton())
                    .load(item.url)
                    .placeholder(R.drawable.gank_shape_girl_default_bg)
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

    override fun getGankGirlFail(data: ResponseException) {
        ToastUtils.showToast(GirlActivity@this,data.getErrorMessage())
        DialogUtils.dismissProgressMD()
        adapter.loadMoreModule.loadMoreFail()
    }

    private fun showEmptyView(){
        adapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.activity_common_empty,null))
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