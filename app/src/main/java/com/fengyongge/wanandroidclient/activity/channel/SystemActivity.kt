package com.fengyongge.wanandroidclient.activity.channel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.fengyongge.baseframework.BaseActivity
import com.fengyongge.wanandroidclient.R
import com.fengyongge.wanandroidclient.fragment.NavigationFragment
import com.fengyongge.wanandroidclient.fragment.SystemFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_system.*

/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class SystemActivity : com.fengyongge.baseframework.BaseActivity(){

    private val tabTitles = arrayListOf("体系","导航")
    private lateinit var myFragmentStateAdapter: MyFragmentStateAdapter

    override fun initLayout(): Int {
        return R.layout.activity_system
    }

    override fun initView() {
        vp2System.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        myFragmentStateAdapter =
            MyFragmentStateAdapter(
                tabTitles,
                this
            )
        vp2System.adapter = myFragmentStateAdapter

        TabLayoutMediator(tlSystem,vp2System,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = tabTitles[position]
            }).attach()
    }


    class MyFragmentStateAdapter(private val titleList: ArrayList<String>,fragmentActivity: FragmentActivity) :

        FragmentStateAdapter(fragmentActivity) {


        override fun getItemCount(): Int {
            return titleList.size
        }

        override fun createFragment(position: Int): Fragment {
            return if(position==0){
                SystemFragment()
            }else{
                NavigationFragment()
            }
        }

    }

    override fun initData() {
    }


}