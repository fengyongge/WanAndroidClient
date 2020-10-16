package com.fengyongge.wanandroidclient.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.fengyongge.baselib.mvp.BaseMvpActivity
import com.fengyongge.baselib.net.BaseResponse
import com.fengyongge.baselib.net.exception.ResponseException
import com.fengyongge.baselib.utils.DialogUtils
import com.fengyongge.baselib.utils.ToastUtils
import com.fengyongge.wanandroidclient.R
import com.fengyongge.wanandroidclient.mvp.contract.ShareContract
import com.fengyongge.wanandroidclient.mvp.presenterImpl.SharePresenterImpl

/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class MyShareActivity : BaseMvpActivity<SharePresenterImpl>(),ShareContract.View {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_share)
    }

    override fun initLayout(): Int {
        return R.layout.activity_my_share
    }

    override fun initView() {
        initTitle()
    }


    private fun initTitle(){
        var tvTitle = findViewById<TextView>(R.id.tvTitle)
        tvTitle?.text = "我的分享"
        var ivLeft = findViewById<ImageView>(R.id.ivLeft)
        ivLeft.visibility = View.VISIBLE
        ivLeft.setBackgroundResource(R.drawable.ic_back)
        ivLeft.setOnClickListener { finish() }
    }

    override fun initData() {
    }

    override fun initPresenter(): SharePresenterImpl {
        return SharePresenterImpl()
    }

    override fun postShareShow(data: BaseResponse<String>) {
    }

    override fun getShareListShow(data: BaseResponse<String>) {
    }

    override fun postDeleteMyShareShow(data: BaseResponse<String>) {
    }

    override fun onError(data: ResponseException) {
        ToastUtils.showToast(ArticleSearchActivity@this,data.getErrorMessage())
        DialogUtils.dismissProgressMD()
    }
}