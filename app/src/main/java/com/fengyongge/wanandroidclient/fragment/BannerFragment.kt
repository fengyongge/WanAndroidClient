package com.fengyongge.wanandroidclient.fragment


import android.os.Bundle
import com.bumptech.glide.Glide
import com.fengyongge.baselib.BaseFragment
import com.fengyongge.wanandroidclient.R
import com.fengyongge.wanandroidclient.bean.BannerBean
import kotlinx.android.synthetic.main.fragment_banner.*

/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class BannerFragment : BaseFragment() {


    private lateinit var bannerBean: BannerBean

    companion object{
        fun getNewInstance(bannerBean: BannerBean): BannerFragment{
            var channelFragment = BannerFragment()
            var bundle = Bundle()
            bundle.putParcelable("bannerBean",bannerBean)
            channelFragment.arguments = bundle
            return channelFragment
        }
    }

    override fun initLayout(): Int {
        return R.layout.fragment_banner
    }

    override fun initView() {
        activity?.let {
            Glide.with(it).load(bannerBean.imagePath)
                .into(ivBanner)
        }
    }

    override fun initData() {
        arguments?.let {
            bannerBean = it.getParcelable<BannerBean>("bannerBean")!!
        }
    }

    override fun initLoad() {

    }

}