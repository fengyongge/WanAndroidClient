package com.fengyongge.wanandroidclient.fragment

import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.fengyongge.androidcommonutils.ktutils.DialogUtils
import com.fengyongge.androidcommonutils.ktutils.ToastUtils
import com.fengyongge.baseframework.mvp.BaseMvpFragment
import com.fengyongge.imageloaderutils.ImageLoaderSdk
import com.fengyongge.rxhttp.exception.ResponseException
import com.fengyongge.wanandroidclient.R
import com.fengyongge.wanandroidclient.activity.OpenEyeDetailActivity
import com.fengyongge.wanandroidclient.bean.openeye.*
import com.fengyongge.wanandroidclient.bean.openeye.Item.Companion.TYPE_ONE
import com.fengyongge.wanandroidclient.bean.openeye.Item.Companion.TYPE_THREE
import com.fengyongge.wanandroidclient.bean.openeye.Item.Companion.TYPE_TWO
import com.fengyongge.wanandroidclient.mvp.contract.OpenEyeContract
import com.fengyongge.wanandroidclient.mvp.presenterImpl.OpenEysPresenterImpl
import com.zzti.fengyongge.imagepicker.ImagePickerInstance
import com.zzti.fengyongge.imagepicker.model.PhotoModel
import kotlinx.android.synthetic.main.fragment_openeye.*

/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class OpenEyeFragment : BaseMvpFragment<OpenEysPresenterImpl>(),OpenEyeContract.View,SwipeRefreshLayout.OnRefreshListener{

    private var dateString = ""
    private var isRefresh = false
    var list = mutableListOf<Item>()
    private lateinit var myAdapter: MyAdapter


    override fun initPresenter(): OpenEysPresenterImpl {
        return OpenEysPresenterImpl()
    }

    override fun initLayout(): Int {
        return R.layout.fragment_openeye
    }

    override fun initView() {
        activity?.let {
            swipeRefreshLayoutOpenEye.setOnRefreshListener(this)
            swipeRefreshLayoutOpenEye.setColorSchemeColors(
                ContextCompat.getColor(
                    it,
                    R.color.colorPrimary
                )
            )
        }

        fabBack.hide()
        fabBack.setOnClickListener {
            recycleViewOpenEye.smoothScrollToPosition(0)
        }

        var linearLayoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        recycleViewOpenEye.layoutManager = linearLayoutManager
        myAdapter = MyAdapter(list)
        recycleViewOpenEye.adapter = myAdapter

        recycleViewOpenEye.run {
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (linearLayoutManager.findLastVisibleItemPosition() > 10) {
                        fabBack.show()
                    } else {
                        fabBack.hide()
                    }
                }
            })
        }

        myAdapter.loadMoreModule.apply {
            setOnLoadMoreListener {
                if (TextUtils.isEmpty(dateString)) {
                    loadMoreComplete()
                    loadMoreEnd()
                } else {
                    loadMore(false)
                }
            }
        }

        myAdapter.apply {
            setOnItemClickListener { adapter, view, position ->
                activity?.let {
                    when (myAdapter.data[position].itemType) {
                        2 -> {
                            var item = adapter.data[position] as Item
                            var intent = Intent(activity, OpenEyeDetailActivity::class.java)
                            var openEyeCusBean = OpenEyeCusBean(
                                item.data.content.data.title,
                                item.data.content.data.description,
                                item.data.content.data.playUrl,
                                "" + item.data.content.data.id,
                                "" + item.data.content.data.cover.homepage,
                                item.data.content.data.category
                            )
                            intent.putExtra("openEyeCusBean", openEyeCusBean)
                            startActivity(intent)
                        }
                        3 -> {
                            var list = mutableListOf<PhotoModel>()
                            var photoModel = PhotoModel()
                            var item =  adapter.data[position] as Item
                            photoModel.originalPath = item.data.content.data.url
                            list.add(photoModel)
                            ImagePickerInstance.getInstance().photoPreview(activity,list,0,true)
                        }
                    }
                }
            }
        }
    }

    override fun initData() {

    }

    override fun initLoad() {
        var tvTitle = activity?.findViewById<TextView>(R.id.tvTitle)
        tvTitle?.text = "每日精选"
        var ivRight = activity?.findViewById<ImageView>(R.id.ivRight)
        ivRight?.visibility = View.GONE
        loadMore(true)
    }

    class MyAdapter(data: MutableList<Item>?) :
        BaseMultiItemQuickAdapter<Item, BaseViewHolder>(data),LoadMoreModule {
        init {
            addItemType(TYPE_ONE,R.layout.item_textcard_fragment_openeye)
            addItemType(TYPE_TWO,R.layout.item_followcard_fragment_openeye)
            addItemType(TYPE_THREE,R.layout.item_picturefollowcard_fragment_openeye)
        }

        override fun convert(holder: BaseViewHolder, item: Item) {
            when(item?.itemType){
                TYPE_ONE ->{
                    var tvOpenEyeTitleName = holder.getView<TextView>(R.id.tvOpenEyeTitleName)
                    tvOpenEyeTitleName.text = item.data.text
                    if(item.data.text == "今日社区精选"){
                        tvOpenEyeTitleName.visibility = View.GONE
                    }else{
                        tvOpenEyeTitleName.visibility = View.VISIBLE
                    }
                }
                TYPE_TWO ->{
                    var ivOpenEyeCoverImage = holder.getView<ImageView>(R.id.ivOpenEyeCoverImage)
                    var tvOpenEyeDuration = holder.getView<TextView>(R.id.tvOpenEyeDuration)
                    var tvOpenEyeTitle = holder.getView<TextView>(R.id.tvOpenEyeTitle)
                    var ivOpenEyeAuthorIcon = holder.getView<ImageView>(R.id.ivOpenEyeAuthorIcon)
                    var tvOpenEyeAuthorName = holder.getView<TextView>(R.id.tvOpenEyeAuthorName)
                    var tvOpenEyeCategoryName = holder.getView<TextView>(R.id.tvOpenEyeCategoryName)

                    ImageLoaderSdk.getInstance().loadRoundImage(20,item.data.content.data.cover.feed,ivOpenEyeCoverImage)
                    tvOpenEyeDuration.text =
                        TimeUtils.convertMinAndSec(item.data.content.data.duration)
                    tvOpenEyeTitle.text = item.data.content.data.title
                    ImageLoaderSdk.getInstance().placeholder = R.drawable.common_shape_image_default_bg
                    ImageLoaderSdk.getInstance().error = R.drawable.common_shape_image_default_bg
                    ImageLoaderSdk.getInstance().fallback = R.drawable.common_shape_image_default_bg
                    ImageLoaderSdk.getInstance().loadImage(item.data.content.data.author?.icon,ivOpenEyeAuthorIcon)
                    tvOpenEyeAuthorName.text = item.data.content.data.author?.name
                    tvOpenEyeCategoryName.text = " #${item.data.content.data.category}"
                }
                else ->{
                    var tvOpenEyePic = holder.getView<ImageView>(R.id.tvOpenEyePic)
                    ImageLoaderSdk.getInstance().placeholder = R.drawable.common_shape_image_default_bg
                    ImageLoaderSdk.getInstance().error = R.drawable.common_shape_image_default_bg
                    ImageLoaderSdk.getInstance().fallback = R.drawable.common_shape_image_default_bg
                    ImageLoaderSdk.getInstance().loadImage(item.data.content.data.url,tvOpenEyePic)
                }
            }
        }
    }

    override fun getOpenEyeDailyShow(data: OpenEyeDailyBean) {
        myAdapter.loadMoreModule.loadMoreComplete()
        if( data?.nextPageUrl.contains("date")){
            var url = data?.nextPageUrl
            dateString =  url.substring(url.indexOf("&")-13, url.indexOf("&"))
        }
        if(data.itemList.isNotEmpty()){
            list = data.itemList as MutableList<Item>
            for(item in list){
                when(item.type){
                    "textCard" ->{
                        item.itemType = TYPE_ONE
                    }
                    "followCard" ->{
                        item.itemType = TYPE_TWO
                    }
                    else ->{
                        item.itemType = TYPE_THREE
                    }
                }
            }
            if(isRefresh){
                myAdapter.setNewInstance(list)
            }else{
                myAdapter.addData(list)
            }
            DialogUtils.dismissProgressMD()
        }else{
            DialogUtils.dismissProgressMD()
            myAdapter.loadMoreModule.loadMoreEnd()
        }
    }

    override fun getOpenEyeDailyShowFail(data: ResponseException) {
        ToastUtils.showToast(activity, data.getErrorMessage())
        DialogUtils.dismissProgressMD()
        myAdapter.loadMoreModule.loadMoreFail()
    }

    override fun getOeRelateVideoShow(data: OpenEyeRelateVideoBean) {
    }

    override fun getOeRelateCommentShow(data: OpenEyeRelateCommentBean) {
    }

    override fun onError(data: ResponseException) {
        DialogUtils.dismissProgressMD()
        ToastUtils.showToast(activity,data.getErrorMessage())
    }

    override fun onRefresh() {
        swipeRefreshLayoutOpenEye.isRefreshing = false
        loadMore(true)
    }
    private fun loadMore(isRefresh: Boolean) {
        this.isRefresh = isRefresh
        if(isRefresh){
            dateString = ""
            activity?.let { DialogUtils.showProgress(it,getString(R.string.load_hint1)) }
        }
        mPresenter?.getOpenEyeDaily(dateString,0)
    }
}