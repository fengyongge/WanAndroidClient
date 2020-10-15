package com.fengyongge.wanandroidclient.activity

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.fengyongge.baselib.mvp.BaseMvpActivity
import com.fengyongge.baselib.net.BaseResponse
import com.fengyongge.baselib.net.exception.ResponseException
import com.fengyongge.baselib.utils.DialogUtils
import com.fengyongge.baselib.utils.SharedPreferencesUtils
import com.fengyongge.baselib.utils.ToastUtils
import com.fengyongge.baselib.utils.ToolsUtils
import com.fengyongge.wanandroidclient.App
import com.fengyongge.wanandroidclient.R
import com.fengyongge.wanandroidclient.bean.UserInforBean
import com.fengyongge.wanandroidclient.constant.Const
import com.fengyongge.wanandroidclient.mvp.contract.UserInforContact
import com.fengyongge.wanandroidclient.mvp.presenterImpl.UserInforPresenterImpl
import kotlinx.android.synthetic.main.activity_setting.*

/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class SettingActivity : BaseMvpActivity<UserInforPresenterImpl>(), UserInforContact.View,View.OnClickListener {
    override fun initPresenter(): UserInforPresenterImpl {
        return UserInforPresenterImpl()
    }

    override fun initLayout(): Int {
        return R.layout.activity_setting
    }

    override fun initView() {
        rlLogout.setOnClickListener(this)
        initTitle()
    }


    private fun initTitle() {
        var tvTitle = findViewById<TextView>(R.id.tvTitle)
        tvTitle?.text = "设置"
        var ivLeft = findViewById<ImageView>(R.id.ivLeft)
        ivLeft.visibility = View.VISIBLE
        ivLeft.setBackgroundResource(R.drawable.ic_back)
        ivLeft.setOnClickListener { finish() }
        tvVersionCode.text = "版本号 V${ToolsUtils.getVersionName(this)}"

        if (isLogin()) {
            rlLogout.visibility = View.VISIBLE
        } else {
            rlLogout.visibility = View.GONE
        }
    }

    override fun initData() {
    }

    override fun getLogoutShow(data: BaseResponse<String>) {
        if (data.errorCode == "0") {
            ToastUtils.showToast(this, "登出成功")
            logoutHandle()
            finish()
        } else {
            ToastUtils.showToast(this, data.errorMsg)
        }
    }

    private fun logoutHandle() {
        SharedPreferencesUtils(App.getContext()).remove(Const.IS_LOGIN)
        SharedPreferencesUtils(App.getContext()).remove(Const.COOKIE)
        SharedPreferencesUtils(App.getContext()).remove(Const.USER_ID)
        SharedPreferencesUtils(App.getContext()).remove(Const.NICKNAME)
        SharedPreferencesUtils(App.getContext()).remove(Const.ICON)
    }

    override fun getAccountShow(data: BaseResponse<UserInforBean>) {
    }

    override fun onError(data: ResponseException) {
    }

    override fun onClick(v: View?) {
        when(v?.id){

            R.id.rlLogout ->{
                if (isLogin()) {
                    DialogUtils.showAlertDialog(
                        this, "一刻也不想待", "草率了", "玩android", "真的要退出吗?学累了可以看看妹子,刷个视频",
                        object : DialogUtils.Companion.OnOkClickListener{
                            override fun onOkClick() {
                                mPresenter?.getLogout()
                            }
                        },
                        object : DialogUtils.Companion.OnCancelClickListener{
                            override fun onCancelClick() {
                                DialogUtils.dismissAlertDialog()
                            }

                        })

                } else {
                    ToastUtils.showToast(this, "还没有登录呢")
                }
            }
            R.id.llContract ->{

            }
            else ->{

            }

        }

    }

    private fun isLogin(): Boolean{
        if(SharedPreferencesUtils(App.getContext()).get(Const.IS_LOGIN,false)){
            return true
        }
        return false
    }
}