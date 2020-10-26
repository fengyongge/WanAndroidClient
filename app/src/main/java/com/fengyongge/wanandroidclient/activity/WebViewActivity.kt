package com.fengyongge.wanandroidclient.activity

import android.content.Context
import android.content.Intent
import android.text.Html
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.fengyongge.baselib.BaseActivity
import com.fengyongge.wanandroidclient.R
import kotlinx.android.synthetic.main.activity_article_detail.*
import kotlinx.android.synthetic.main.common_title.*


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
        var filtTitle = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Html.fromHtml(title, Html.FROM_HTML_MODE_LEGACY).toString()
        } else {
            Html.fromHtml(title).toString()
        }
        tvTitle?.text = filtTitle
        var ivLeft = findViewById<ImageView>(R.id.ivLeft)
        ivLeft.visibility = View.VISIBLE
        ivSecondLeft.visibility = View.VISIBLE
        ivSecondLeft.setBackgroundResource(R.drawable.ic_close)
        ivLeft.setBackgroundResource(R.drawable.ic_back)
        ivLeft.setOnClickListener {
            articleWebView.handleKeyEvent(KeyEvent.KEYCODE_BACK)
        }
        ivSecondLeft.setOnClickListener {
            finish()
        }
        if(tvTitle.text == "隐私政策与用户协议"){
            ivRight.visibility = View.GONE
        }else{
            ivRight.visibility = View.VISIBLE
            ivRight.setBackgroundResource(R.drawable.ic_share)
            ivRight.setOnClickListener {
                shareArticle(title+"\n"+link)
            }
        }
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

    /**
     * Android原生分享功能
     * 默认选取手机所有可以分享的APP
     */
    private fun shareArticle(shareContent: String) {
        var share_intent = Intent()
        share_intent.action = Intent.ACTION_SEND
        share_intent.type = "text/plain"
        share_intent.putExtra(Intent.EXTRA_SUBJECT, "share")
        share_intent.putExtra(Intent.EXTRA_TEXT, shareContent)
        share_intent = Intent.createChooser(share_intent, "分享文章")
        startActivity(share_intent)
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

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (articleWebView.handleKeyEvent(keyCode)) {
            true
        } else super.onKeyDown(keyCode, event)
    }


}