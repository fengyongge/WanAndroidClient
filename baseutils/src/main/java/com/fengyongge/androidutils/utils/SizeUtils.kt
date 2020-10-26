package com.fengyongge.androidutils.utils

import android.content.res.Resources
import android.util.TypedValue
/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class SizeUtils {

    companion object{

        /**
         * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
         */
        fun dp2px(dp: Int): Int {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), Resources.getSystem().displayMetrics).toInt()
        }

    }

}