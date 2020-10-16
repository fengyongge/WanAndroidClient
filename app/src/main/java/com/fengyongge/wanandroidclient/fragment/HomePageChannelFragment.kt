package com.fengyongge.wanandroidclient.fragment

import android.content.Intent
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.fengyongge.baselib.BaseFragment
import com.fengyongge.baselib.utils.ScreenUtls
import com.fengyongge.baselib.utils.SizeUtils
import com.fengyongge.wanandroidclient.App
import com.fengyongge.wanandroidclient.R
import com.fengyongge.wanandroidclient.activity.channel.*
import kotlinx.android.synthetic.main.fragment_homepage_header_channel.*

/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class HomePageChannelFragment : BaseFragment() {

    private lateinit var myAdapter: MyAdapter
    private var list = mutableListOf("项目","体系","问答","面试","妹子")

    override fun initLayout(): Int {
        return R.layout.fragment_homepage_header_channel
    }

    override fun initView() {
        recycleViewBanner.layoutManager = GridLayoutManager(activity,5)
        myAdapter = MyAdapter(list)
        recycleViewBanner.adapter = myAdapter

        myAdapter.setOnItemClickListener { adapter, view, position ->

            when(adapter.data[position].toString()){

                "项目" ->{
                    startActivity(Intent(activity, ProjectActivity::class.java))
                }
                "体系" ->{
                    startActivity(Intent(activity, SystemActivity::class.java))
                }
                "问答" ->{
                    startActivity(Intent(activity, QuestionAnswerActivity::class.java))
                }
                "面试" ->{
                    var intent = Intent(context, SystemArticleActivity::class.java)
                    intent.putExtra("cid", 73)
                    intent.putExtra("titleName", "面试")
                    startActivity(intent)
                }
                "妹子" ->{
                    startActivity(Intent(activity,
                        GirlActivity::class.java))
                }
            }
        }

    }

    override fun initData() {

    }

    override fun initLoad() {

    }


    class MyAdapter(data: MutableList<String>?) :
        BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_fragment_homepage_header_banner, data) {

        override fun convert(holder: BaseViewHolder, item: String) {
            val llItem = holder.getView<LinearLayout>(R.id.llItem)
            val tvBanner = holder.getView<TextView>(R.id.tvBanner)
            val ivBanner = holder.getView<ImageView>(R.id.ivBanner)
            var layoutParams = llItem.layoutParams
            layoutParams.width = (ScreenUtls.getScreenWidth(App.getContext())-4*SizeUtils.dp2px(5))/5
            layoutParams.height = layoutParams.width

            item?.let {
                tvBanner.text = item

                when(item){
                    "项目" ->{
                        ivBanner.setImageResource(R.drawable.channel_project)
                    }
                    "体系" ->{
                        ivBanner.setImageResource(R.drawable.channel_system)
                    }
                    "问答" ->{
                        ivBanner.setImageResource(R.drawable.channel_question)
                    }
                    "面试" ->{
                        ivBanner.setImageResource(R.drawable.channel_interview)
                    }
                    "妹子" ->{
                        ivBanner.setImageResource(R.drawable.channel_girl)
                    }

                }
            }
        }
    }

}