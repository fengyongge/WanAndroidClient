package com.fengyongge.wanandroidclient.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.TextUtils
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import com.fengyongge.baselib.BaseActivity
import com.fengyongge.baselib.utils.NetUtils
import com.fengyongge.wanandroidclient.R
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

    companion object{
        const val LINK = "link"
        fun getIntent(
            context: Context,
            link: String): Intent{
            var intent = Intent(context,WebViewActivity::class.java)
            intent.putExtra(LINK,link)
            return intent
        }
    }

    override fun initLayout(): Int {
        return R.layout.activity_article_detail
    }

    fun initTitle(){
        var tvTitle = findViewById<TextView>(R.id.tvTitle)
        tvTitle?.text = "详情"
        var ivLeft = findViewById<ImageView>(R.id.ivLeft)
        ivLeft.visibility = View.VISIBLE
        ivLeft.setBackgroundResource(R.drawable.ic_back)
        ivLeft.setOnClickListener { finish() }
    }

    override fun initView() {
        initTitle()
        articleWebView.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
        articleWebView.isScrollbarFadingEnabled = true
        articleWebView.addJavascriptInterface(this, "jsApi")
        val settings: WebSettings = articleWebView.settings
        settings.javaScriptEnabled = true
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true
        settings.blockNetworkImage = false //解决图片不显示
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

        articleWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return super.shouldOverrideUrlLoading(view, url)
            }
        }
    }

    override fun initData() {
        var intent = intent
        link = intent.getStringExtra(LINK)
        articleWebView.loadUrl(link)
    }


    override fun onResume() {
        super.onResume()
        if (!NetUtils.isConnected(this@WebViewActivity)) {
            articleWebView.loadUrl("file:///android_asset/offline.html")
        } else if (!TextUtils.isEmpty(link)) {
            articleWebView.loadUrl(link)
        }
    }


    @JavascriptInterface
    fun webReload() {
        articleWebView.post(Runnable {
            articleWebView.clearHistory()
            if (!NetUtils.isConnected(this@WebViewActivity)) {
                articleWebView.loadUrl("file:///android_asset/offline.html")
            } else if (!TextUtils.isEmpty(link)) {
                articleWebView.loadUrl(link)
            }
        })
    }


}