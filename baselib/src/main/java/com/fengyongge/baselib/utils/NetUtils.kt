package com.fengyongge.baselib.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
/**
 * describe
 *
 * @author fengyongge(fengyongge98@gmail.com)
 * @version V1.0
 * @date 2020/09/08
 */
class NetUtils{

    companion object {

        /**
         * 判断网络是否连接
         */
        fun isConnected(context: Context): Boolean {
            val cm =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                    ?: return false
            val info = cm.activeNetworkInfo
            if (null != info && info.isConnected) {
                if (info.state == NetworkInfo.State.CONNECTED) {
                    return true
                }
            }
            return false
        }
    }

}