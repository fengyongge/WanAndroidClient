package com.fengyongge.wanandroidclient.activity


import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.fengyongge.baselib.BaseActivity
import com.fengyongge.wanandroidclient.R
import com.fengyongge.wanandroidclient.bean.OpenSourceBean
import kotlinx.android.synthetic.main.activity_open_source.*

/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class OpenSourceActivity : BaseActivity() {

    var list = mutableListOf<OpenSourceBean>()
    lateinit var myAdapter:MyAdapter

    override fun initLayout(): Int {
        return R.layout.activity_open_source
    }

    override fun initView() {
        var tvTitle = findViewById<TextView>(R.id.tvTitle)
        tvTitle?.text = "开源相关"
        var ivLeft = findViewById<ImageView>(R.id.ivLeft)
        ivLeft.visibility = View.VISIBLE
        ivLeft.setBackgroundResource(R.drawable.ic_back)
        ivLeft.setOnClickListener { finish() }
        recycleViewOpenSource.layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,false)
        myAdapter = MyAdapter()
        recycleViewOpenSource.adapter = myAdapter
        loadData()
        myAdapter.setOnItemClickListener { adapter, view, position ->
            var openSourceBean = adapter.data[position] as OpenSourceBean
            startActivity(WebViewActivity.getIntent(OpenSourceActivity@this,openSourceBean.linkUrl,"开源相关"))
        }
    }

    override fun initData() {

    }

    private fun loadData(){
        list.clear()
        list.add(OpenSourceBean("wanandroidApi","zhanghongyang","android学习开放网站","https://wanandroid.com/blog/show/2"))
        list.add(OpenSourceBean("openeyeApi","kaiyanapp","每日精选视频推介,让你大开眼界","https://www.kaiyanapp.com/"))
        list.add(OpenSourceBean("meiziApi","daimajia","干货集中营","https://gank.io/api"))
        list.add(OpenSourceBean("Jetpack","google","Jetpack 是一个由多个库组成的套件，可帮助开发者遵循最佳做法，减少样板代码并编写可在各种 Android 版本和设备中一致运行的代码，让开发者精力集中编写重要的代码","https://developer.android.google.cn/jetpack"))
        list.add(OpenSourceBean("gson","google","A Java serialization/deserialization library to convert Java Objects into JSON and back","https://github.com/google/gson"))
        list.add(OpenSourceBean("retrofit2","square","A type-safe HTTP client for Android and the JVM","https://github.com/square/retrofit"))
        list.add(OpenSourceBean("Rxjava/RxAndroid","ReactiveX", "RxJava – Reactive Extensions for the JVM – a library for composing asynchronous and event-based programs using observable sequences for the Java VM.\nRxJava bindings for Android\n","https://github.com/ReactiveX"))
        list.add(OpenSourceBean("glide","bumptech","An image loading and caching library for Android focused on smooth scrolling","https://github.com/bumptech/glide"))
        list.add(OpenSourceBean("glide-transformations","wasabeef","An Android transformation library providing a variety of image transformations for Glide.","https://github.com/wasabeef/glide-transformations"))
        list.add(OpenSourceBean("leakcanary","square","A memory leak detection library for Android.","https://github.com/square/leakcanary"))
        list.add(OpenSourceBean("imagepicker","fengyongge","","imagepicker是一款用于在Android设备上获取照片（拍照或从相册、文件中选择）、压缩图片的开源工具库"))
        list.add(OpenSourceBean("BaseRecyclerViewAdapterHelper","CymChad","BRVAH:Powerful and flexible RecyclerAdapter","https://github.com/CymChad/BaseRecyclerViewAdapterHelper"))
        list.add(OpenSourceBean("material-dialogs","afollestad","material-dialogs","https://github.com/afollestad/material-dialogs"))
        list.add(OpenSourceBean("LabelsView","donkingliang","Android的标签列表控件。可以设置标签的选中效果。 可以设置标签的选中类型：不可选中、单选、限数量多选和不限数量多选等， 并支持设置必选项、单行显示、最大显示行数等功能","https://github.com/donkingliang/LabelsView"))
        list.add(OpenSourceBean("jiaozivideoplayer","jzvd","MediaPlayer exoplayer ijkplayer ffmpeg","https://github.com/lipangit/JiaoZiVideoPlayer"))
        list.add(OpenSourceBean("umeng","umeng","友盟数据统计","https://www.umeng.com/"))
        list.add(OpenSourceBean("tbssdk","tencent","腾讯浏览器服务","https://x5.tencent.com/"))
        list.add(OpenSourceBean("bugly","tencent","应用更新,异常上报和运营统计","https://bugly.qq.com/v2/"))
        myAdapter.setNewInstance(list)
    }



    class MyAdapter : BaseQuickAdapter<OpenSourceBean, BaseViewHolder>(R.layout.item_activity_open_source),LoadMoreModule {
        override fun convert(holder: BaseViewHolder, item: OpenSourceBean) {
           var tvOpenSourceTitle = holder.getView<TextView>(R.id.tvOpenSourceTitle)
           var tvOpenSourceAuthor = holder.getView<TextView>(R.id.tvOpenSourceAuthor)
           var tvOpenSourceDes = holder.getView<TextView>(R.id.tvOpenSourceDes)
            tvOpenSourceTitle.text = item.name
            tvOpenSourceAuthor.text = item.author
            tvOpenSourceDes.text = item.des
        }
    }

}