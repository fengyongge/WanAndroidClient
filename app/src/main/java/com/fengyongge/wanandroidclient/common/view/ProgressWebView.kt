package com.fengyongge.wanandroidclient.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.webkit.WebView
import android.widget.ProgressBar
import com.fengyongge.wanandroidclient.R
/**
 * describe
 * 自定义进度条webview
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class ProgressWebView(
    context: Context,
    attrs: AttributeSet?
) : WebView(context, attrs) {
    private val progressbar: ProgressBar = ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal)
    private var mWebChromeClientListener: WebChromeClientListener? = null

    open inner class WebChromeClient : android.webkit.WebChromeClient() {
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            if (newProgress == 100) {
                progressbar.visibility = View.GONE
            } else {
                if (progressbar.visibility == View.GONE) {
                    progressbar.visibility = View.VISIBLE
                }
                progressbar.progress = newProgress
            }
            super.onProgressChanged(view, newProgress)
        }
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        val lp =
            progressbar.layoutParams as LayoutParams
        lp.x = l
        lp.y = t
        progressbar.layoutParams = lp
        super.onScrollChanged(l, t, oldl, oldt)
    }

    fun setWebChromeClientListener(listener: WebChromeClientListener?) {
        mWebChromeClientListener = listener
    }

    interface WebChromeClientListener {
        fun onReceivedTitle(title: String?)
    }

    init {
        progressbar.layoutParams = LayoutParams(
            LayoutParams.MATCH_PARENT,
            4,
            0,
            0
        )
        val drawable =
            context.resources.getDrawable(R.drawable.layer_progress)
        progressbar.progressDrawable = drawable

        addView(progressbar)
        val webChromeClient: WebChromeClient =
            object : WebChromeClient() {
                override fun onReceivedTitle(
                    view: WebView,
                    title: String
                ) {
                    super.onReceivedTitle(view, title)
                    if (null != mWebChromeClientListener) {
                        mWebChromeClientListener!!.onReceivedTitle(title)
                    }
                }
            }
        setWebChromeClient(webChromeClient)

        settings.setSupportZoom(true)
        settings.builtInZoomControls = true
    }
}