package com.fengyongge.androidutils.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.afollestad.materialdialogs.MaterialDialog
/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class DialogUtils {

    companion object {

        private var mDialogMd: MaterialDialog?= null

        /**
         * 创建md风格进度条
         * @param ctx
         * @param content
         */
        fun showProgress(
            ctx: Context,
            content: String
        ) {
            mDialogMd = MaterialDialog.Builder(ctx)
                    .content(content)
                    .progress(true, 0)
                    .cancelable(true)
                    .canceledOnTouchOutside(false)
                    .show()
        }

        /**
         * 取消md风格进度条
         */
        fun dismissProgressMD() {
            mDialogMd?.let {
                if(it.isShowing){
                    it.dismiss()
                }
            }
        }


    interface OnOkClickListener {
        fun onOkClick()
    }
    interface OnCancelClickListener {
        fun onCancelClick()
    }

    /**
     * 创建md风格alert
     */
    private var mDialog: AlertDialog? = null
    fun showAlertDialog(
        context: Context?,
        ok: String?,
        cancel: String?,
        title: String?,
        content: String?,
        listenerYes: OnOkClickListener?,
        listenerNo: OnCancelClickListener?
    ) {
        val builder =
            AlertDialog.Builder(context!!)
        builder.setMessage(content)
        builder.setTitle(title)
        builder.setPositiveButton(
            ok
        ) { dialog, which ->
            listenerYes?.onOkClick()
            mDialog = null
        }
        // 取消
        builder.setNegativeButton(
            cancel
        ) { dialog, which ->
                listenerNo?.onCancelClick()
            mDialog = null
        }

        builder.setCancelable(false)
        mDialog = builder.create()
        mDialog?.let {
            if(!it.isShowing){
                it.show()
            }
        }
    }

     fun dismissAlertDialog() {
        mDialog?.let {
            it.dismiss()
        }
    }

 }

}