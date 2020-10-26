package com.fengyongge.wanandroidclient.activity

import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.fengyongge.androidcommonutils.ktutils.DialogUtils
import com.fengyongge.androidcommonutils.ktutils.SharedPreferencesUtils
import com.fengyongge.androidcommonutils.ktutils.ToastUtils
import com.fengyongge.androidcommonutils.ktutils.ToolsUtils
import com.fengyongge.baselib.mvp.BaseMvpActivity
import com.fengyongge.wanandroidclient.common.RxNotify
import com.fengyongge.rxhttp.bean.BaseResponse
import com.fengyongge.rxhttp.exception.ResponseException
import com.fengyongge.wanandroidclient.App
import com.fengyongge.wanandroidclient.R
import com.fengyongge.wanandroidclient.bean.LoginBean
import com.fengyongge.wanandroidclient.bean.LogoutUpdateBean
import com.fengyongge.wanandroidclient.bean.RegisterBean
import com.fengyongge.wanandroidclient.constant.Const
import com.fengyongge.wanandroidclient.mvp.contract.LoginContact
import com.fengyongge.wanandroidclient.mvp.presenterImpl.LoginPresenterImpl
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.tvVersionName

/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class RegisterActivity : BaseMvpActivity<LoginPresenterImpl>(), LoginContact.View, View.OnClickListener {
    private var flag = false
    private var confrimFlag = false
    private var isReset = false

    override fun initPresenter(): LoginPresenterImpl {
        return LoginPresenterImpl()
    }

    override fun initLayout(): Int {
        return R.layout.activity_register
    }

    override fun initView() {
        tvVersionName.text = "V${ToolsUtils.getVersionName(this)}"
        isReset = intent.getBooleanExtra("isReset", false)
        if (isReset) {
            etRegisterPassword.hint = Editable.Factory.getInstance().newEditable("新密码")
            etRegisterConfirmPassword.hint = Editable.Factory.getInstance().newEditable("确认新密码")
            btRegister.text = "重置密码"

        } else {
            etRegisterPassword.hint = Editable.Factory.getInstance().newEditable("密码")
            etRegisterConfirmPassword.hint = Editable.Factory.getInstance().newEditable("确认密码")
            btRegister.text = "注册"
        }

        etRegisterUserName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                justContent()
            }
        })


        etRegisterPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (etRegisterPassword.text.toString().isNotEmpty()) {
                    ivRegisterPasswordShow.visibility = View.VISIBLE
                    ivRegisterClearPassword.visibility = View.VISIBLE
                } else {
                    ivRegisterPasswordShow.visibility = View.INVISIBLE
                    ivRegisterClearPassword.visibility = View.INVISIBLE
                }
                justContent()
            }
        })

        etRegisterConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (etRegisterConfirmPassword.text.toString().isNotEmpty()) {
                    ivConfirmPasswordShow.visibility = View.VISIBLE
                    ivConfirmClearPassword.visibility = View.VISIBLE
                } else {
                    ivConfirmPasswordShow.visibility = View.INVISIBLE
                    ivConfirmClearPassword.visibility = View.INVISIBLE
                }
                justContent()
            }
        })
        ivRegisterPasswordShow.setOnClickListener(this)
        ivRegisterClearPassword.setOnClickListener(this)
        ivConfirmPasswordShow.setOnClickListener(this)
        ivConfirmClearPassword.setOnClickListener(this)
        btRegister.setOnClickListener(this)
        btRegister.background.alpha = 100
        btRegister.isEnabled = false
    }

    private fun justContent() {
        if (TextUtils.isEmpty(etRegisterUserName.text.toString().trim())
            || TextUtils.isEmpty(etRegisterPassword.text.toString().trim())
            || TextUtils.isEmpty(etRegisterConfirmPassword.text.toString().trim())
        ) {
            btRegister.background.alpha = 100
            btRegister.isEnabled = false
        } else {
            btRegister.background.alpha = 255
            btRegister.isEnabled = true
        }
    }

    override fun initData() {

    }

    override fun postLoginShow(data: BaseResponse<LoginBean>) {
        if (data.errorCode == "0") {
            with(SharedPreferencesUtils(App.getContext())){
                put(Const.IS_LOGIN,true)
                put(Const.NICKNAME,data.data.nickname)
                put(Const.ICON,data.data.icon)
                put(Const.USER_ID,data.data.id)
            }
            startActivity(Intent(LoginActivity@this,MainActivity::class.java))
            var logoutUpdateBean = LogoutUpdateBean()
            logoutUpdateBean.isUpdate = true
            RxNotify.instance?.post(logoutUpdateBean)
            finish()
        }else{
            ToastUtils.showToast(LoginActivity@this,data.errorMsg)
        }
    }

    override fun postRegisterShow(data: BaseResponse<RegisterBean>) {
        if (data.errorCode == "0") {
            DialogUtils.dismissProgressMD()
            mPresenter?.postLogin(etRegisterUserName.text.toString(), etRegisterConfirmPassword.text.toString())
        } else {
            DialogUtils.dismissProgressMD()
            ToastUtils.showToast(LoginActivity@ this, data.errorMsg)
        }
    }


    override fun onError(data: ResponseException) {
        ToastUtils.showToast(ArticleSearchActivity@ this, data.getErrorMessage())
        DialogUtils.dismissProgressMD()
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btRegister -> {
                if (isReset) {

                }else{
                    if (etRegisterPassword.text.toString() != etRegisterConfirmPassword.text.toString()) {
                        ToastUtils.showToast(RegisterActivity@ this, "两次密码不一样，请重新输入")
                    } else {
                        DialogUtils.showProgress(RegisterActivity@ this, "账号申请中...")
                        mPresenter?.postRegister(
                            etRegisterUserName.text.toString().trim(),
                            etRegisterPassword.text.toString().trim(),
                            etRegisterConfirmPassword.text.toString().trim()
                        )
                    }
                }
            }
            R.id.ivRegisterPasswordShow -> {
                if (flag) {
                    etRegisterPassword.transformationMethod =
                        PasswordTransformationMethod.getInstance()
                    ivRegisterPasswordShow.setImageResource(R.drawable.icon_pwd_hide)
                    etRegisterPassword.setSelection(etRegisterPassword.text.length)
                    flag = false
                } else {
                    etRegisterPassword.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                    ivRegisterPasswordShow.setImageResource(R.drawable.icon_pwd_show)
                    etRegisterPassword.setSelection(etRegisterPassword.text.length)
                    flag = true
                }
            }

            R.id.ivConfirmPasswordShow -> {
                if (confrimFlag) {
                    etRegisterConfirmPassword.transformationMethod =
                        PasswordTransformationMethod.getInstance()
                    ivConfirmPasswordShow.setImageResource(R.drawable.icon_pwd_hide)
                    etRegisterConfirmPassword.setSelection(etRegisterConfirmPassword.text.length)
                    confrimFlag = false
                } else {
                    etRegisterConfirmPassword.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                    ivConfirmPasswordShow.setImageResource(R.drawable.icon_pwd_show)
                    etRegisterConfirmPassword.setSelection(etRegisterConfirmPassword.text.length)
                    confrimFlag = true
                }
            }
            R.id.ivRegisterClearPassword -> {
                etRegisterPassword.text = Editable.Factory.getInstance().newEditable("")
            }
            R.id.ivConfirmClearPassword -> {
                etRegisterConfirmPassword.text = Editable.Factory.getInstance().newEditable("")
            }
        }
    }

}