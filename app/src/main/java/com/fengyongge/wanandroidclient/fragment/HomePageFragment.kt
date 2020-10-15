package com.fengyongge.wanandroidclient.fragment

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.fengyongge.baselib.mvp.BaseMvpFragment
import com.fengyongge.baselib.net.BaseResponse
import com.fengyongge.baselib.net.exception.ResponseException
import com.fengyongge.baselib.utils.DialogUtils
import com.fengyongge.baselib.utils.SizeUtils
import com.fengyongge.baselib.utils.ToastUtils
import com.fengyongge.wanandroidclient.R
import com.fengyongge.wanandroidclient.activity.ArticleSearchActivity
import com.fengyongge.wanandroidclient.activity.WebViewActivity
import com.fengyongge.wanandroidclient.bean.ArticleBean
import com.fengyongge.wanandroidclient.bean.BannerBean
import com.fengyongge.wanandroidclient.bean.DataX
import com.fengyongge.wanandroidclient.mvp.contract.HomePageContract
import com.fengyongge.wanandroidclient.mvp.presenterImpl.HomePagePresenterImpl
import kotlinx.android.synthetic.main.common_homepage_title.*
import kotlinx.android.synthetic.main.fragment_homepage.*
import kotlinx.android.synthetic.main.fragment_homepage_header.*

/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class HomePageFragment : BaseMvpFragment<HomePagePresenterImpl>(), HomePageContract.View,
    SwipeRefreshLayout.OnRefreshListener {
    private var bannerList = mutableListOf<BannerBean>()
    private var list = mutableListOf<DataX>()
    private lateinit var myAdapter: MyAdapter
    private var pageNum = 0
    private var offset = 0
    private var isRefresh = false
    private var isProject = false
    private var collectPosition: Int = 0


    override fun initPresenter(): HomePagePresenterImpl {
        return  HomePagePresenterImpl()
    }


    override fun initLayout(): Int {
        return R.layout.fragment_homepage
    }

    override fun initView() {

        activity?.let {
            swipeRefreshLayout.setOnRefreshListener(this)
            swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(
                    it,
                    R.color.colorPrimary
                )
            )
        }
        recycleViewHomePage.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        myAdapter = MyAdapter()
        recycleViewHomePage.adapter = myAdapter
        myAdapter.setHeaderView(getHeadView(), 0, LinearLayout.VERTICAL)
        initChooseTitle()

        myAdapter.loadMoreModule.apply {
            setOnLoadMoreListener {
                if (pageNum * 20 < offset) {
                    loadMoreComplete()
                    loadMoreEnd()
                } else {
                    pageNum++
                    loadMore(false, pageNum)
                }
            }
        }

        myAdapter.apply {
            addChildClickViewIds(R.id.ivHomePageCollect)
            setOnItemClickListener { _, _, position ->
                activity?.let {
                    startActivity(WebViewActivity.getIntent(it,myAdapter.data[position].link))
                }
            }
            setOnItemChildClickListener { adapter, view, position ->
                when (view?.id) {
                    R.id.ivHomePageCollect -> {
                        var dataX = adapter.data[position] as DataX
                        collectPosition = position
                        if(dataX.collect){
                            dataX.collect = false
                            mPresenter?.postCancleCollect(myAdapter.data[position].id)
                        }else{
                            dataX.collect = true
                            mPresenter?.postCollect(myAdapter.data[position].id)
                        }
                    }
                }
            }
        }


        recycleViewHomePage.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var scrollHeight = 0
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                scrollHeight += dy
                var temptHeight  = SizeUtils.dp2px(170)+  SizeUtils.dp2px(116)
                Log.i("fyg","scrollHeight$scrollHeight"+"temptHeight${temptHeight}")
                if(scrollHeight >temptHeight){
                    title1.visibility = View.VISIBLE
                    title2.visibility = View.GONE
                }else{
                    title1.visibility = View.GONE
                    title2.visibility = View.VISIBLE
                }
            }
        })
        llHomeArticle.setOnClickListener {
            isProject = false
            justChoose(isProject)
            loadMore(true,0)
        }

        llHomeProject.setOnClickListener {
            isProject = true
            justChoose(isProject)
            loadMore(true,0)
        }

    }

    private fun justChoose(isProject: Boolean){
        if(isProject){
            viewHeaderArticle.visibility = View.GONE
            viewHeaderProject.visibility = View.VISIBLE
            tvHeaderProject.isSelected = true
            tvHeaderArticle.isSelected = false
            viewArticle.visibility = View.GONE
            viewProject.visibility = View.VISIBLE
            tvProject.isSelected = true
            tvArticle.isSelected = false


        }else{
            viewHeaderArticle.visibility = View.VISIBLE
            viewHeaderProject.visibility = View.GONE
            tvHeaderProject.isSelected = false
            tvHeaderArticle.isSelected = true
            viewArticle.visibility = View.VISIBLE
            viewProject.visibility = View.GONE
            tvProject.isSelected = false
            tvArticle.isSelected = true
        }
    }

    private fun initChooseTitle(){
        viewArticle.visibility = View.VISIBLE
        viewProject.visibility = View.GONE

        viewHeaderArticle.visibility = View.VISIBLE
        viewHeaderProject.visibility = View.GONE

        tvHeaderArticle.isSelected = true
        tvHeaderProject.isSelected = false

        tvArticle.isSelected = true
        tvProject.isSelected = false
    }


    override fun initData() {

    }


    override fun initLoad() {
        loadMore(true, 0)
        var tvTitle = activity?.findViewById<TextView>(R.id.tvTitle)
        tvTitle?.text = "首页"
        var ivRight = activity?.findViewById<ImageView>(R.id.ivRight)
        ivRight?.visibility = View.VISIBLE
        ivRight?.setBackgroundResource(R.drawable.ic_search_blue)
        ivRight?.setOnClickListener {
            startActivity(Intent(activity, ArticleSearchActivity::class.java))
        }
    }


    class MyAdapter : BaseQuickAdapter<DataX, BaseViewHolder>(R.layout.item_fragment_homepage),
        LoadMoreModule {
        override fun convert(holder: BaseViewHolder, item: DataX) {
            val ivHomePageCollect = holder.getView<ImageView>(R.id.ivHomePageCollect)
            val tvContent = holder.getView<TextView>(R.id.tvContent)
            val tvTime = holder.getView<TextView>(R.id.tvTime)
            with(item){
                tvContent.text = title
                tvTime.text = niceDate
            }
            if (item.collect) {
                ivHomePageCollect.setImageResource(R.drawable.ic_collect_fill)
            } else {
                ivHomePageCollect.setImageResource(R.drawable.ic_collect)
            }
        }
    }


    private fun loadMore(isRefresh: Boolean, pageNum: Int) {
        mPresenter?.bannerList()
        this.isRefresh = isRefresh

        if(isProject){
            mPresenter?.projectList(pageNum)
        }else{
            mPresenter?.articleList(pageNum)
        }
        if(isRefresh){
            activity?.let { DialogUtils.showProgress(it,"数据加载中") }
        }
    }

    private fun showEmptyView() {

    }

    override fun onRefresh() {
        swipeRefreshLayout.isRefreshing = false
        pageNum = 0
        loadMore(true, pageNum)
    }

    override fun bannerListShow(data: BaseResponse<List<BannerBean>>) {
        if (data.errorCode == "0") {
            if (data.data.isNotEmpty()) {
                bannerList.clear()
                fragmentList.clear()
                for (index in data.data.indices) {
                    bannerList.add(data.data[index])
                    fragmentList.add(BannerFragment.getNewInstance(bannerList[index]))
                }
                bannerFragmentStatePagerAdapter.setAdapter(fragmentList)
            } else {
                ToastUtils.showToast(activity, data.errorMsg)
            }
        }
    }



    override fun articleListShow(data: BaseResponse<ArticleBean>) {
        if (data.errorCode == "0") {
            list = data.data.datas as MutableList<DataX>
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

    override fun projectListShow(data: BaseResponse<ArticleBean>) {
        if (data.errorCode == "0") {
            list = data.data.datas as MutableList<DataX>
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

    override fun onError(data: ResponseException) {
        DialogUtils.dismissProgressMD()
        ToastUtils.showToast(activity, data.getErrorMessage())
    }

    override fun postCollectShow(data: BaseResponse<String>) {
        if (data.errorCode == "0") {
            ToastUtils.showToast(activity, "收藏成功")
            myAdapter.notifyItemChanged(collectPosition+1)
        }else{
            ToastUtils.showToast(activity,data.errorMsg)
        }
    }

    override fun postCancleCollectShow(data: BaseResponse<String>) {
        if (data.errorCode == "0") {
            ToastUtils.showToast(activity, "取消收藏成功")
            myAdapter.notifyItemChanged(collectPosition+1)
        }else{
            ToastUtils.showToast(activity,data.errorMsg)
        }
    }

    lateinit var viewHeaderArticle: View
    lateinit var viewHeaderProject: View
    lateinit var tvHeaderArticle: TextView
    private lateinit var tvHeaderProject: TextView
    private var fragmentList = mutableListOf<Fragment>()
    private lateinit var vp2Banner: ViewPager
    private lateinit var bannerFragmentStatePagerAdapter: BannerFragmentStateAdapter
    private fun getHeadView(): View {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_homepage_header, null)
        val llHomeHeaderArticle = view.findViewById<LinearLayout>(R.id.llHomeHeaderArticle)
        val llHomeHeaderProject = view.findViewById<LinearLayout>(R.id.llHomeHeaderProject)
        viewHeaderArticle = view.findViewById(R.id.viewHeaderArticle)
        viewHeaderProject = view.findViewById(R.id.viewHeaderProject)
        tvHeaderArticle = view.findViewById(R.id.tvHeaderArticle)
        tvHeaderProject = view.findViewById(R.id.tvHeaderProject)
        vp2Banner = view.findViewById(R.id.vp2Banner)
        bannerFragmentStatePagerAdapter = activity?.let { BannerFragmentStateAdapter(childFragmentManager) }!!

        val layoutParams = vp2Banner.layoutParams
        layoutParams.height = SizeUtils.dp2px( 170)
        vp2Banner.layoutParams = layoutParams
        vp2Banner.adapter = bannerFragmentStatePagerAdapter

        val vp2Channel = view.findViewById<ViewPager2>(R.id.vp2Channel)
        var myFragmentStateAdapter = activity?.let { ChannelFragmentStateAdapter(it) }
        vp2Channel.adapter = myFragmentStateAdapter

        llHomeHeaderArticle.setOnClickListener {
            isProject = false
            justChoose(isProject)
            loadMore(true,0)
        }

        llHomeHeaderProject.setOnClickListener {
            isProject = true
            justChoose(isProject)
            loadMore(true,0)
        }

        return view
    }

//    class BannerFragmentStateAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm) {
//
//        private var fragmentList = mutableListOf<Fragment>()
//
//        fun setAdapter(fragmentList: MutableList<Fragment>){
//            this.fragmentList.clear()
//            this.fragmentList .addAll(fragmentList)
//            notifyDataSetChanged()
//        }
//
//        override fun getItemCount(): Int {
//            return fragmentList.size
//        }
//
//        override fun createFragment(position: Int): Fragment {
//            return fragmentList[position]
//        }
//    }

    class BannerFragmentStateAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

        private var fragmentList = mutableListOf<Fragment>()

        fun setAdapter(fragmentList: MutableList<Fragment>){
            this.fragmentList.clear()
            this.fragmentList .addAll(fragmentList)
            notifyDataSetChanged()
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }
    }


    class ChannelFragmentStateAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int {
            return 1
        }

        override fun createFragment(position: Int): Fragment {
            return HomePageChannelFragment()
        }

    }

}


