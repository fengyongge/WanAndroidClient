package com.fengyongge.wanandroidclient.common.view

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.fengyongge.wanandroidclient.R
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.export.external.interfaces.WebResourceResponse
import com.tencent.smtt.sdk.*
import java.io.ByteArrayInputStream
import java.util.*


class MyX5WebView : WebView {
    var progressBar: ProgressBar? = null
    private val tvTitle: TextView? = null

    constructor(context: Context?) : super(context) {
        initUI()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        initUI()
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        initUI()
    }

    fun setShowProgress(showProgress: Boolean) {
        if (showProgress) {
            progressBar!!.visibility = View.VISIBLE
        } else {
            progressBar!!.visibility = View.GONE
        }
    }

    private fun initUI() {
//        x5WebViewExtension.setScrollBarFadingEnabled(false)
        isHorizontalScrollBarEnabled = false //水平不显示小方块
        isVerticalScrollBarEnabled = false //垂直不显示小方块

        progressBar = ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal)
        progressBar!!.max = 100
        progressBar!!.progressDrawable = this.resources.getDrawable(R.drawable.common_load_progress)
        addView(progressBar, LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 6))
        initWebViewSettings()
    }

    private fun initWebViewSettings() {
        setBackgroundColor(resources.getColor(android.R.color.white))
        webViewClient = client
        webChromeClient = chromeClient
        isClickable = true
        setOnTouchListener { v, event -> false }
        val webSetting = settings
        with(webSetting){
            javaScriptEnabled = true
            builtInZoomControls = true
            javaScriptCanOpenWindowsAutomatically = true
            domStorageEnabled = true
            allowFileAccess = true
            layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
            setSupportZoom(false)
            useWideViewPort = true
            setSupportMultipleWindows(true)
            setAppCacheEnabled(true)
            setGeolocationEnabled(true)
            setAppCacheMaxSize(Long.MAX_VALUE)
            setRenderPriority(WebSettings.RenderPriority.HIGH)
            setSupportMultipleWindows(false)
            //适配手机
            layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN;
            loadWithOverviewMode = true;
        }
    }



    private val chromeClient: WebChromeClient =
        object : WebChromeClient() {
            override fun onReceivedTitle(
                view: WebView,
                title: String
            ) {
                if (tvTitle == null || TextUtils.isEmpty(title)) {
                    return
                }
                if (title != null && title.length > MAX_LENGTH) {
                    tvTitle.text = title.subSequence(0,
                        MAX_LENGTH
                    ).toString() + "..."
                } else {
                    tvTitle.text = title
                }
            }

            //监听进度
            override fun onProgressChanged(
                view: WebView,
                newProgress: Int
            ) {
                progressBar!!.progress = newProgress
                if (progressBar != null && newProgress != 100) {
                    progressBar!!.visibility = View.VISIBLE
                } else if (progressBar != null) {
                    progressBar!!.visibility = View.GONE
                }
            }
        }
    private val client: WebViewClient =
        object : WebViewClient() {
            //当页面加载完成的时候
            override fun onPageFinished(
                webView: WebView,
                url: String
            ) {
                val cookieManager =
                    CookieManager.getInstance()
                cookieManager.setAcceptCookie(true)
                val endCookie = cookieManager.getCookie(url)
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    CookieSyncManager.getInstance().sync() //同步cookie
                } else {
                    CookieManager.getInstance().flush()
                }
                super.onPageFinished(webView, url)
            }

            override fun shouldOverrideUrlLoading(
                view: WebView,
                url: String
            ): Boolean {

                return if (url.startsWith("http") || url.startsWith("https") || url.startsWith("ftp") ) {
                    false
                } else {
                    try {
                        val intent = Intent()
                        intent.action = Intent.ACTION_VIEW
                        intent.data = Uri.parse(url)
                        view.context.startActivity(intent)
                    } catch (e: ActivityNotFoundException) {

                    }
                    true
                }
            }

            override fun shouldInterceptRequest(
                view: WebView,
                request: WebResourceRequest
            ): WebResourceResponse {
                return super.shouldInterceptRequest(view, request)
            }


            override fun onLoadResource(
                webView: WebView,
                s: String
            ) {
                super.onLoadResource(webView, s)
                val reUrl = webView.url + ""
                val urlList: MutableList<String> =
                    ArrayList()
                urlList.add(reUrl)
              
            }
        }

    fun syncCookie(url: String?, cookie: String) {
        CookieSyncManager.createInstance(context)
        if (!TextUtils.isEmpty(url)) {
            val cookieManager =
                CookieManager.getInstance()
            cookieManager.setAcceptCookie(true)
            cookieManager.removeSessionCookie() // 移除
            cookieManager.removeAllCookie()

            //这里的拼接方式是伪代码
            val split = cookie.split(";".toRegex()).toTypedArray()
            for (string in split) {
                //为url设置cookie
                // ajax方式下  cookie后面的分号会丢失
                cookieManager.setCookie(url, string)
            }
            val newCookie = cookieManager.getCookie(url)
            //sdk21之后CookieSyncManager被抛弃了，换成了CookieManager来进行管理。
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                CookieSyncManager.getInstance().sync() //同步cookie
            } else {
                CookieManager.getInstance().flush()
            }
        } else {
        }
    }

    //删除Cookie
    private fun removeCookie() {
        CookieSyncManager.createInstance(context)
        val cookieManager =
            CookieManager.getInstance()
        cookieManager.removeSessionCookie()
        cookieManager.removeAllCookie()
        if (Build.VERSION.SDK_INT < 21) {
            CookieSyncManager.getInstance().sync()
        } else {
            CookieManager.getInstance().flush()
        }
    }


    companion object {
        private const val MAX_LENGTH = 8
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && canGoBack()) {
            goBack() 
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}