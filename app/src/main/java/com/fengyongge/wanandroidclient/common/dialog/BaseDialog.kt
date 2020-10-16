package com.fengyongge.wanandroidclient.common.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.WindowManager

class BaseDialog {
    /**
     * //设置弹出框宽度
     *
     * @param dialog       对话框对象
     * @param context      上下文对象
     * @param marginValues 弹出框居左距右距离设定
     */
    fun setDialogWidth(
        dialog: Dialog,
        context: Context?,
        marginValues: Int,
        Location: Int
    ) {
        val dialogWindow = dialog.window
        //获取屏幕大小
        if (context == null) {
            return
        }
        val wm =
            context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val width = wm.defaultDisplay.width //屏幕宽度
        val height = wm.defaultDisplay.height
        val lp = dialogWindow!!.attributes
        lp.width = width - marginValues //marginValues这个值设置越大。弹窗窗口越小。
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialogWindow.setBackgroundDrawable(ColorDrawable(Color.parseColor("#44000000")))
        dialogWindow.setGravity(Location)
        dialogWindow.attributes = lp
    }
}