package com.fengyongge.wanandroidclient.fragment

import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.donkingliang.labels.LabelsView
import com.fengyongge.baselib.mvp.BaseMvpFragment
import com.fengyongge.baselib.net.BaseResponse
import com.fengyongge.baselib.net.exception.ResponseException
import com.fengyongge.baselib.utils.DialogUtils
import com.fengyongge.baselib.utils.ToastUtils
import com.fengyongge.wanandroidclient.R
import com.fengyongge.wanandroidclient.activity.WebViewActivity
import com.fengyongge.wanandroidclient.bean.Article
import com.fengyongge.wanandroidclient.bean.NavigationBean
import com.fengyongge.wanandroidclient.mvp.contract.NavigationContact
import com.fengyongge.wanandroidclient.mvp.presenterImpl.NavigationPresenterImpl
import kotlinx.android.synthetic.main.fragment_navigation.*
/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class NavigationFragment : BaseMvpFragment<NavigationPresenterImpl>(),NavigationContact.View {

    private var navigationList = mutableListOf<NavigationBean>()
    private lateinit var navigationAdapter: NavigationAdapter


    override fun initPresenter(): NavigationPresenterImpl {
        return NavigationPresenterImpl()
    }

    override fun initLayout(): Int {
        return R.layout.fragment_navigation
    }

    override fun initView() {
        recycleViewNavigation.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        navigationAdapter = NavigationAdapter(navigationList)
        recycleViewNavigation.adapter = navigationAdapter
        activity?.let { DialogUtils.showProgress(it,getString(R.string.load_hint1)) }
        mPresenter?.getNavigation()
    }

    override fun initData() {
    }

    override fun initLoad() {
    }


    override fun getNavigationShow(data: BaseResponse<List<NavigationBean>>) {
        if (data.errorCode == "0") {
            if (data.data.isNotEmpty()) {
                navigationList = data.data as MutableList<NavigationBean>
                 navigationAdapter.setNewInstance(navigationList)
            }
            DialogUtils.dismissProgressMD()
        } else {
            DialogUtils.dismissProgressMD()
            ToastUtils.showToast(activity, data.errorCode)
        }
    }

    override fun onError(data: ResponseException) {
        DialogUtils.dismissProgressMD()
        ToastUtils.showToast(activity,data.getErrorMessage())
    }


    class NavigationAdapter(data: MutableList<NavigationBean>?) : BaseQuickAdapter<NavigationBean, BaseViewHolder>(R.layout.item_activity_navigation,data){
        override fun convert(holder: BaseViewHolder, item: NavigationBean) {

            var tvNavigationCategoryName = holder.getView<TextView>(R.id.tvNavigationCategoryName)
            var labels = holder.getView<LabelsView>(R.id.navigationLabels)

            tvNavigationCategoryName.text = item.name

            if(item.articles.isNotEmpty()){
                var tagList = mutableListOf<Article>()
                for(item in item.articles){
                    tagList.add(item)
                }
                labels.setLabels(tagList
                ) { label, position, data -> data?.title }
            }

            labels.setOnLabelClickListener(LabelsView.OnLabelClickListener { label, data, position ->
                var article = data as Article
                context.startActivity(WebViewActivity.getIntent(context,article.link))

            })

        }

    }
}