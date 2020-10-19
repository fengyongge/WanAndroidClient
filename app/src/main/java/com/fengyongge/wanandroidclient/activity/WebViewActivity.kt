package com.fengyongge.wanandroidclient.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import android.widget.ImageView
import android.widget.TextView
import com.fengyongge.baselib.BaseActivity
import com.fengyongge.wanandroidclient.App
import com.fengyongge.wanandroidclient.R
import com.fengyongge.wanandroidclient.common.view.ProgressWebView
import com.tencent.smtt.sdk.CookieSyncManager
import com.tencent.smtt.sdk.WebView
import kotlinx.android.synthetic.main.activity_article_detail.*


/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class WebViewActivity : BaseActivity() {

    lateinit var link: String
    lateinit var title: String
    var isPrivacy =false

    companion object {
        const val LINK = "link"
        const val TITLE = "title"
        fun getIntent(
            context: Context,
            link: String,
            title: String
            ): Intent {
            var intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(LINK, link)
            intent.putExtra(TITLE, title)
            return intent
        }
    }

    override fun initLayout(): Int {
        return R.layout.activity_article_detail
    }

    private fun initTitle() {
        var tvTitle = findViewById<TextView>(R.id.tvTitle)
        tvTitle?.text = title
        var ivLeft = findViewById<ImageView>(R.id.ivLeft)
        ivLeft.visibility = View.VISIBLE
        ivLeft.setBackgroundResource(R.drawable.ic_back)
        ivLeft.setOnClickListener { finish() }
    }

    override fun initView() {
        initTitle()
    }

    override fun initData() {
        var intent = intent
        isPrivacy = intent.getBooleanExtra("isPrivacy",false)
        title = intent.getStringExtra(TITLE)
        if(isPrivacy){
            articleWebView.loadUrl("file:///android_asset/privacy_policy.html");
        }else{
            link = intent.getStringExtra(LINK)
            articleWebView.loadUrl(link)
        }
    }

    override fun onResume() {
        super.onResume()
        articleWebView.onResume()
    }

    override fun onPause() {
        super.onPause()
        articleWebView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        articleWebView?.let {
            articleWebView.destroy()
        }
    }

}