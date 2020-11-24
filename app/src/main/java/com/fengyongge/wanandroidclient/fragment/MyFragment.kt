package com.fengyongge.wanandroidclient.fragment

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.alibaba.android.arouter.launcher.ARouter
import com.fengyongge.androidcommonutils.ktutils.DialogUtils
import com.fengyongge.androidcommonutils.ktutils.SharedPreferencesUtils
import com.fengyongge.androidcommonutils.ktutils.ToastUtils
import com.fengyongge.basecomponent.app.BaseApplication
import com.fengyongge.baseframework.mvp.BaseMvpFragment
import com.fengyongge.basecomponent.utils.RxNotify
import com.fengyongge.rxhttp.bean.BaseResponse
import com.fengyongge.rxhttp.exception.ResponseException
import com.fengyongge.wanandroidclient.R
import com.fengyongge.wanandroidclient.activity.*
import com.fengyongge.basecomponent.bean.LogoutUpdateBean
import com.fengyongge.wanandroidclient.bean.UserInforBean
import com.fengyongge.basecomponent.constant.Const
import com.fengyongge.basecomponent.constant.RouterManageConst
import com.fengyongge.imageloaderutils.ImageLoaderSdk
import com.fengyongge.wanandroidclient.mvp.contract.UserInforContact
import com.fengyongge.wanandroidclient.mvp.presenterImpl.UserInforPresenterImpl
import kotlinx.android.synthetic.main.fragment_my.*

/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class MyFragment: BaseMvpFragment<UserInforPresenterImpl>(),UserInforContact.View,View.OnClickListener {
    private val hintContent = "点击头像登录即可收藏或发布分享"
    override fun initLayout(): Int {
        return R.layout.fragment_my
    }

    override fun initView() {

        ivAvatar.setOnClickListener(this)
        tvSetting.setOnClickListener(this)
        tvCollect.setOnClickListener(this)
        tvShare.setOnClickListener(this)

        RxNotify.instance?.let {
            it.apply {
                toObservable(LogoutUpdateBean::class.java)
                    .subscribe {
                        if(it.isUpdate){
                            setDefault()
                        }
                    }
            }
        }
    }

    override fun initData() {

    }

    override fun initLoad() {
        var tvTitle = activity?.findViewById<TextView>(R.id.tvTitle)
        tvTitle?.text = "我的"
        var ivRight = activity?.findViewById<ImageView>(R.id.ivRight)
        ivRight?.visibility = View.GONE
        setDefault()


    }

    private fun setDefault(){
        if (isLogin()) {
            tvHint.visibility = View.GONE
            llUserInfor.visibility = View.VISIBLE
            tvUserName.text = SharedPreferencesUtils(
                BaseApplication.getAppContext()
            ).get(Const.NICKNAME, hintContent)
            activity?.let {
                ImageLoaderSdk.getInstance().loadImageResources(R.drawable.ic_default_user_blue,ivAvatar)
            }
            tvUserName.text = SharedPreferencesUtils(
                BaseApplication.getAppContext()
            ).get(Const.NICKNAME, hintContent)
            mPresenter?.getAccount()
        } else {
            tvHint.visibility = View.VISIBLE
            llUserInfor.visibility = View.GONE
            tvUserName.text = SharedPreferencesUtils(
                BaseApplication.getAppContext()
            ).get(Const.NICKNAME, hintContent)
            activity?.let {
                ImageLoaderSdk.getInstance().loadImageResources(R.drawable.ic_default_user_gray,ivAvatar)
            }
        }
    }

    override fun initPresenter(): UserInforPresenterImpl {
        return UserInforPresenterImpl()
    }

    override fun getAccountShow(data: BaseResponse<UserInforBean>) {
        if (data.errorCode == "0") {
            tvRank.text = "排名${data.data.rank}"
            tvAccount.text = "${data.data.coinCount}积分"
        } else {
            activity?.let {
                ToastUtils.showToast(it, data.errorMsg)
            }
        }
    }

    override fun onError(data: ResponseException) {
        DialogUtils.dismissProgressMD()
        ToastUtils.showToast(activity,data.getErrorMessage())
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivAvatar -> {
                if (!isLogin()) {
                    activity?.let {
                        ARouter.getInstance().build(RouterManageConst.LOGIN_LOGIN).navigation()
                    }
                }
            }
            R.id.tvSetting -> {
                startActivity(Intent(activity, SettingActivity::class.java))
            }
            R.id.tvCollect -> {
                if (isLogin()) {
                    startActivity(Intent(activity, MyCollectArticleActivity::class.java))
                } else {
                    ARouter.getInstance().build(RouterManageConst.LOGIN_LOGIN).navigation()
                }
            }
            R.id.tvShare -> {
                if (isLogin()) {
                    startActivity(Intent(activity, MyShareActivity::class.java))
                } else {
                    ARouter.getInstance().build(RouterManageConst.LOGIN_LOGIN).navigation()
                }
            }
        }
    }

    private fun isLogin(): Boolean{
        if(SharedPreferencesUtils(BaseApplication.getAppContext())
                .get(Const.IS_LOGIN,false)){
            return true
        }
        return false
    }
}