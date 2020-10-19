package com.fengyongge.wanandroidclient.activity

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.fengyongge.baselib.mvp.BaseMvpActivity
import com.fengyongge.baselib.net.BaseResponse
import com.fengyongge.baselib.net.exception.ResponseException
import com.fengyongge.baselib.utils.*
import com.fengyongge.wanandroidclient.App
import com.fengyongge.wanandroidclient.R
import com.fengyongge.wanandroidclient.bean.LogoutUpdateBean
import com.fengyongge.wanandroidclient.bean.UserInforBean
import com.fengyongge.wanandroidclient.constant.Const
import com.fengyongge.wanandroidclient.mvp.contract.UserInforContact
import com.fengyongge.wanandroidclient.mvp.presenterImpl.UserInforPresenterImpl
import com.tencent.bugly.beta.Beta
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
        llContract.setOnClickListener(this)
        llVersion.setOnClickListener(this)
        rlAbout.setOnClickListener(this)
        rlOpenSource.setOnClickListener(this)
        initTitle()
    }


    private fun initTitle() {
        var tvTitle = findViewById<TextView>(R.id.tvTitle)
        tvTitle?.text = "设置"
        var ivLeft = findViewById<ImageView>(R.id.ivLeft)
        ivLeft.visibility = View.VISIBLE
        ivLeft.setBackgroundResource(R.drawable.ic_back)
        ivLeft.setOnClickListener { finish() }
        tvVersionCode.text = "V${ToolsUtils.getVersionName(this)}"

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
            var logoutUpdateBean = LogoutUpdateBean()
            logoutUpdateBean.isUpdate = true
            RxNotify.instance?.post(logoutUpdateBean)
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
                var intent = Intent(SettingActivity@this,WebViewActivity::class.java)
                intent.putExtra("isPrivacy",true)
                intent.putExtra("title","隐私政策与用户说明")
                startActivity(intent)
            }
            R.id.rlOpenSource ->{
                startActivity(Intent(SettingActivity@this, OpenSourceActivity::class.java))
            }
            R.id.llVersion ->{
                loadUpgradeInfo(this)
            }
            R.id.rlAbout ->{
                startActivity(Intent(SettingActivity@this, AboutActivity::class.java))
            }
            else ->{

            }
        }
    }

    private fun loadUpgradeInfo(context: Context) {
        /***** 获取升级信息  */
        val upgradeInfo = Beta.getUpgradeInfo()
        if (upgradeInfo == null) {
            ToastUtils.showToast(context,"已经是最新版本了")
            return
        }
        Beta.checkUpgrade(true, false)
        val info = StringBuilder()
        info.append("id: ").append(upgradeInfo.id).append("\n")
        info.append("标题: ").append(upgradeInfo.title).append("\n")
        info.append("升级说明: ").append(upgradeInfo.newFeature).append("\n")
        info.append("versionCode: ").append(upgradeInfo.versionCode).append("\n")
        info.append("versionName: ").append(upgradeInfo.versionName).append("\n")
        info.append("发布时间: ").append(upgradeInfo.publishTime).append("\n")
        info.append("安装包Md5: ").append(upgradeInfo.apkMd5).append("\n")
        info.append("安装包下载地址: ").append(upgradeInfo.apkUrl).append("\n")
        info.append("安装包大小: ").append(upgradeInfo.fileSize).append("\n")
        info.append("弹窗间隔（ms）: ").append(upgradeInfo.popInterval).append("\n")
        info.append("弹窗次数: ").append(upgradeInfo.popTimes).append("\n")
        info.append("发布类型（0:测试 1:正式）: ").append(upgradeInfo.publishType).append("\n")
        info.append("弹窗类型（1:建议 2:强制 3:手工）: ").append(upgradeInfo.upgradeType)
            .append("\n")
        info.append("图片地址：").append(upgradeInfo.imageUrl)
    }


    private fun isLogin(): Boolean{
        if(SharedPreferencesUtils(App.getContext()).get(Const.IS_LOGIN,false)){
            return true
        }
        return false
    }
}