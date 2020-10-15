package com.fengyongge.baselib.utils

import android.content.Context
import android.widget.Toast
/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class ToastUtils {

    companion object{

        fun showToast(context: Context?,messsage: String?){
            Toast.makeText(context,messsage,Toast.LENGTH_SHORT).show()
        }
    }
}