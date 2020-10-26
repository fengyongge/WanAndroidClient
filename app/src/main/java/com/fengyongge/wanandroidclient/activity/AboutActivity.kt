package com.fengyongge.wanandroidclient.activity

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.fengyongge.androidcommonutils.ktutils.ToolsUtils
import com.fengyongge.baselib.BaseActivity
import com.fengyongge.wanandroidclient.R
import kotlinx.android.synthetic.main.activity_about.*

/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class AboutActivity : BaseActivity() {

    private val officialUrl = "https://www.wanandroid.com"
    private val githubUrl = "https://github.com/fengyongge/WanAndroidClient"

    override fun initLayout(): Int {
        return R.layout.activity_about
    }

    override fun initView() {
        initTitle()
        tvVersionName.text = "V${ToolsUtils.getVersionName(this)}"
        llWeb.setOnClickListener {
            startActivity(WebViewActivity.getIntent(AboutActivity@this,officialUrl,"官网"))
        }
        llGithub.setOnClickListener {
            startActivity(WebViewActivity.getIntent(AboutActivity@this,githubUrl,"开源地址"))
        }
    }

    private fun initTitle(){
        var tvTitle = findViewById<TextView>(R.id.tvTitle)
        tvTitle?.text = "关于"
        var ivLeft = findViewById<ImageView>(R.id.ivLeft)
        ivLeft.visibility = View.VISIBLE
        ivLeft.setBackgroundResource(R.drawable.ic_back)
        ivLeft.setOnClickListener { finish() }
    }

    override fun initData() {
    }

}