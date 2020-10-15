package com.fengyongge.wanandroidclient.fragment

import android.content.Intent
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
import com.fengyongge.wanandroidclient.activity.channel.SystemArticleActivity
import com.fengyongge.wanandroidclient.bean.Children
import com.fengyongge.wanandroidclient.bean.SystemArticleBean
import com.fengyongge.wanandroidclient.bean.SystemCategoryBean
import com.fengyongge.wanandroidclient.mvp.contract.SystemContact
import com.fengyongge.wanandroidclient.mvp.presenterImpl.SystemPresenterImpl
import kotlinx.android.synthetic.main.fragment_system.*
/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class SystemFragment: BaseMvpFragment<SystemPresenterImpl>(),SystemContact.View {

    private var systemCategoryList = mutableListOf<SystemCategoryBean>()
    private lateinit var systemAdapter: SystemAdapter

    override fun initPresenter(): SystemPresenterImpl {
        return SystemPresenterImpl()
    }

    override fun initLayout(): Int {
        return R.layout.fragment_system
    }

    override fun initView() {
        recycleViewSystem.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        systemAdapter = SystemAdapter(systemCategoryList)
        recycleViewSystem.adapter = systemAdapter
        activity?.let { DialogUtils.showProgress(it,"数据加载中") }
        mPresenter?.getSystemCategory()
    }

    override fun initLoad() {
    }

    override fun initData() {

    }

    override fun postCollectShow(data: BaseResponse<String>) {
        TODO("Not yet implemented")
    }

    override fun postCancleCollectShow(data: BaseResponse<String>) {
        TODO("Not yet implemented")
    }


    override fun getSystemCategoryShow(data: BaseResponse<List<SystemCategoryBean>>) {
        if(data.errorCode == "0") {
            if (data.data.isNotEmpty()) {
                systemCategoryList =  data.data as MutableList<SystemCategoryBean>
                systemAdapter.setNewInstance(systemCategoryList)
            }
            DialogUtils.dismissProgressMD()
        }else{
            DialogUtils.dismissProgressMD()
            ToastUtils.showToast(activity,data.errorMsg)
        }
    }

    override fun getSystemArticleShow(data: BaseResponse<SystemArticleBean>) {

    }

    override fun onError(data: ResponseException) {
        DialogUtils.dismissProgressMD()
        ToastUtils.showToast(activity,data.getErrorMessage())
    }


    class SystemAdapter(data: MutableList<SystemCategoryBean>?) : BaseQuickAdapter<SystemCategoryBean, BaseViewHolder>(R.layout.item_activity_system,data){
        override fun convert(holder: BaseViewHolder, item: SystemCategoryBean) {

            var tvSystemCategoryName = holder.getView<TextView>(R.id.tvSystemCategoryName)
            var labels = holder.getView<LabelsView>(R.id.labels)

            tvSystemCategoryName.text = item.name

            if(item.children.isNotEmpty()){
                var tagList = mutableListOf<Children>()
                for(item in item.children){
                    tagList.add(item)
                }
                labels.setLabels(tagList
                ) { label, position, data -> data?.name }
            }

            labels.setOnLabelClickListener { label, data, position ->
                var children = data as Children
                var intent = Intent(context, SystemArticleActivity::class.java)
                intent.putExtra("cid", children.id)
                intent.putExtra("titleName", children.name)
                context.startActivity(intent)
            }

        }
    }


}