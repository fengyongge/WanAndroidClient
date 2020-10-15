package com.fengyongge.wanandroidclient.activity

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.fengyongge.baselib.BaseActivity
import com.fengyongge.wanandroidclient.R

/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class AboutActivity : BaseActivity() {
    override fun initLayout(): Int {
        return R.layout.activity_about
    }

    override fun initView() {
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