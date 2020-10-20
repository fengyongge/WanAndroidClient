package com.fengyongge.wanandroidclient.common.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.fengyongge.wanandroidclient.R
import com.fengyongge.wanandroidclient.activity.WebViewActivity


/**
 * describe
 * 用户协议，隐私协议
 *
 * @author fengyongge(fengyongge98 @ gmail.com)
 * @version V1.0
 * @date 2020/3/30
 */
open class AgreementDialog : Dialog {
    var animationDialogEventListener: AnimationDialogEventListener? = null
    var mContext: Context

    interface AnimationDialogEventListener {
        fun clickAnimationView(
            view: View?,
            vararg parames: Any?
        )
    }

    constructor(
        context: Context,
        animationDialogEventListener: AnimationDialogEventListener?
    ) : super(context) {
        mContext = context
        this.animationDialogEventListener = animationDialogEventListener
    }

    constructor(context: Context, themeResId: Int) : super(
        context,
        R.style.dialog
    ) {
        mContext = context
    }

    protected constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(context, cancelable, cancelListener) {
        mContext = context
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.dialog_agreement, null)
        setContentView(view)
        init(view)
    }

    override fun onStart() {
        super.onStart()
        setCanceledOnTouchOutside(false)
        window!!.setBackgroundDrawableResource(android.R.color.transparent)
    }

    var tvHint: TextView? = null
    var url: String = ""
    private fun init(view: View) {
        tvHint = view.findViewById<View>(R.id.tvHint) as TextView
        val tvContent = view.findViewById<View>(R.id.tvContent) as TextView
        val tvCancle = view.findViewById<View>(R.id.tvCancle) as TextView
        val tvConfirm = view.findViewById<View>(R.id.tvConfirm) as TextView
        val message = "<br>玩Android尊重并保护所有使用服务用户的个人隐私权。" +
                "<br><br>为了给您提供更准确、更优质的服务，玩Android会按照本隐私权政策的规定使用和披露您的个人信息。" +
                "<br><br>除本隐私权政策另有规定外，在未征得您事先许可的情况下，" +
                "玩Android不会将这些信息对外披露或向第三方提供。" +
                "<br><br>您在同意玩Android服务使用协议之时，即视为您已经同意本隐私权政策全部内容。"
        tvContent.text = Html.fromHtml(message)
        customLink()
        tvCancle.setOnClickListener {
            animationDialogEventListener?.let {
                animationDialogEventListener!!.clickAnimationView(null, "cancle")
            }
        }
        tvConfirm.setOnClickListener {
            animationDialogEventListener?.let {
                animationDialogEventListener!!.clickAnimationView(null, "confirm")
                dismiss()
            }
        }
    }

    private fun customLink() {
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
//            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            //记得修改com.xxx.fileprovider与androidmanifest相同
//            uri = FileProvider.getUriForFile(mContext,"com.ice.AAAAA.fileprovider",apkFile);
//            intent.setDataAndType(uri,"application/vnd.android.package-archive");
//        }else{
//            uri = Uri.parse("file://" + apkFile.toString());
//        }
//        "file:///android_asset/privacy_policy.html

        val content1 = "您可通过阅读完整的"
        val content2 = "《用户协议》"
        val content3 = "和"
        val content4 = "《隐私政策》"
        val content5 = "来了解详细信息"
        val content = content1 + content2 + content3 + content4 + content5
        val firsrStart = content1.length
        val firsrEnd = firsrStart + content2.length
        val secondStart = firsrEnd + content3.length
        val secondEnd = secondStart + content4.length
        val spannableString1 = SpannableString(content)
        spannableString1.setSpan(
            URLSpanNoUnderline(),
            firsrStart, firsrEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString1.setSpan(
            URLSpanNoUnderline(),
            secondStart, secondEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        tvHint!!.text = spannableString1
        tvHint!!.movementMethod = LinkMovementMethod.getInstance()
    }

    inner class URLSpanNoUnderline : ClickableSpan() {
        override fun onClick(widget: View) {
            var intent = Intent(mContext,WebViewActivity::class.java)
            intent.putExtra("isPrivacy",true)
            intent.putExtra("title","隐私政策与用户协议")
            mContext.startActivity(intent)
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = false
            ds.color = mContext.resources.getColor(R.color.blue_theme)
        }
    }


}