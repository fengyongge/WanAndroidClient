package com.fengyongge.wanandroidclient.fragment


import android.os.Bundle
import com.fengyongge.imageloaderutils.ImageLoaderSdk
import com.fengyongge.wanandroidclient.R
import com.fengyongge.wanandroidclient.activity.WebViewActivity
import com.fengyongge.wanandroidclient.bean.BannerBean
import kotlinx.android.synthetic.main.fragment_banner.*

/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class BannerFragment : com.fengyongge.baseframework.BaseFragment() {


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

        ImageLoaderSdk.getInstance().placeholder = R.drawable.common_shape_image_default_bg
        ImageLoaderSdk.getInstance().error = R.drawable.common_shape_image_default_bg
        ImageLoaderSdk.getInstance().fallback = R.drawable.common_shape_image_default_bg
        ImageLoaderSdk.getInstance().loadImage(bannerBean.imagePath,ivBanner)

        cdBanner.setOnClickListener {
            activity?.let {
                startActivity(WebViewActivity.getIntent(it,bannerBean.url,bannerBean.title))
            }
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