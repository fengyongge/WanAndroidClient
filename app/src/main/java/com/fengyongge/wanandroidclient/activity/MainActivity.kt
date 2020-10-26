package com.fengyongge.wanandroidclient.activity

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.fengyongge.androidcommonutils.ktutils.SharedPreferencesUtils
import com.fengyongge.baselib.BaseActivity
import com.fengyongge.wanandroidclient.App
import com.fengyongge.wanandroidclient.R
import com.fengyongge.wanandroidclient.common.dialog.AgreementDialog
import com.fengyongge.wanandroidclient.fragment.*
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class MainActivity : BaseActivity() {

    private lateinit var tab: TabLayout.Tab
    private val titleList = mutableListOf<String>()
    private val iconList = mutableListOf<ImageView>()
    private val textViewList = mutableListOf<TextView>()
    private val fragmentList = mutableListOf<Fragment>()

    override fun initLayout(): Int {
        return R.layout.activity_main
    }

    override fun initView() {
        SharedPreferencesUtils(App.getContext())
            ?.run {
            if(!get("isShow",false)){
                readAgreement()
            }
        }

        var viewPagerAdapter = MyFragmentPagerAdapter(supportFragmentManager, fragmentList)
        viewPager.adapter = viewPagerAdapter
        tablayout.setupWithViewPager(viewPager)
        setCustomView(titleList)
        viewPager.offscreenPageLimit = 5

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                set(position)
            }
        })
    }

    private fun readAgreement(){
        val agreementDialog =
            AgreementDialog(MainActivity@this, object :
                AgreementDialog.AnimationDialogEventListener {
                override fun clickAnimationView(view: View?, vararg parames: Any?) {
                    val dialogContent = parames[0] as String
                    if (dialogContent == "cancle") {
                        exitApp()
                        return
                    } else if (dialogContent == "confirm") {
                        SharedPreferencesUtils(
                            App.getContext()
                        )?.let {
                            it.put("isShow",true)
                        }
                    }
                }
            })
        agreementDialog.setCancelable(false)
        agreementDialog.setCanceledOnTouchOutside(false)
        agreementDialog.show()
    }

    override fun initData() {
        titleList.clear()
        titleList.add("首页")
        titleList.add("公众号")
        titleList.add("广场")
        titleList.add("开眼")
        titleList.add("我的")
        fragmentList.clear()
        fragmentList.add(HomePageFragment())
        fragmentList.add(WxAccountFragment())
        fragmentList.add(SquareFragement())
        fragmentList.add(OpenEyeFragment())
        fragmentList.add(MyFragment())
    }


    fun set(position: Int) {
        when (position) {
            0 -> {
                iconList[0].setImageResource(R.drawable.ic_homepage_fill)
                iconList[1].setImageResource(R.drawable.ic_wxaccount)
                iconList[2].setImageResource(R.drawable.ic_square)
                iconList[3].setImageResource(R.drawable.ic_openeye)
                iconList[4].setImageResource(R.drawable.ic_my)
            }
            1 -> {
                iconList[0].setImageResource(R.drawable.ic_homepage)
                iconList[1].setImageResource(R.drawable.ic_wxaccount_fill)
                iconList[2].setImageResource(R.drawable.ic_square)
                iconList[3].setImageResource(R.drawable.ic_openeye)
                iconList[4].setImageResource(R.drawable.ic_my)
            }
            2 -> {
                iconList[0].setImageResource(R.drawable.ic_homepage)
                iconList[1].setImageResource(R.drawable.ic_wxaccount)
                iconList[2].setImageResource(R.drawable.ic_square_fill)
                iconList[3].setImageResource(R.drawable.ic_openeye)
                iconList[4].setImageResource(R.drawable.ic_my)
            }
            3 -> {
                iconList[0].setImageResource(R.drawable.ic_homepage)
                iconList[1].setImageResource(R.drawable.ic_wxaccount)
                iconList[2].setImageResource(R.drawable.ic_square)
                iconList[3].setImageResource(R.drawable.ic_openeye_fill)
                iconList[4].setImageResource(R.drawable.ic_my)
            }
            4 -> {
                iconList[0].setImageResource(R.drawable.ic_homepage)
                iconList[1].setImageResource(R.drawable.ic_wxaccount)
                iconList[2].setImageResource(R.drawable.ic_square)
                iconList[3].setImageResource(R.drawable.ic_openeye)
                iconList[4].setImageResource(R.drawable.ic_my_fill)
            }
        }
    }

    private fun setDefault() {
        iconList[0].setImageResource(R.drawable.ic_homepage_fill)
        iconList[1].setImageResource(R.drawable.ic_wxaccount)
        iconList[2].setImageResource(R.drawable.ic_square)
        iconList[3].setImageResource(R.drawable.ic_openeye)
        iconList[4].setImageResource(R.drawable.ic_my)
        textViewList[0].text = titleList[0]
        textViewList[1].text = titleList[1]
        textViewList[2].text = titleList[2]
        textViewList[3].text = titleList[3]
        textViewList[4].text = titleList[4]
    }


    class MyFragmentPagerAdapter : FragmentPagerAdapter {

        private var fragmentList = mutableListOf<Fragment>()

        constructor(fm: FragmentManager, fragmentList: MutableList<Fragment>) : super(fm) {
            this.fragmentList = fragmentList
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int = fragmentList.size;

    }

   private fun setCustomView(titleList: List<String>) {

        for (index in titleList.indices) {
            tab = tablayout.getTabAt(index)!!
            var view = LayoutInflater.from(this@MainActivity)
                .inflate(R.layout.activity_bottom_customview, null)
            val ivTabIcon = view.findViewById<ImageView>(R.id.ivTabIcon)
            val tvTabTitle = view.findViewById<TextView>(R.id.tvTabTitle)
            iconList.add(ivTabIcon)
            textViewList.add(tvTabTitle)
            tab.customView = view

        }
        setDefault();
    }


}

