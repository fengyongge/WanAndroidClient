package com.fengyongge.androidutils.utils

import android.content.Context
import android.content.pm.PackageManager

class ToolsUtils {

    companion object{

        /**
         * 检查软件是否有更新版本
         */
        fun isUpdate(context: Context?, code: Int): Boolean {
            try {
                if (context == null) return false
                val versionCode =
                    getVersionCode(
                        context
                    )
                if (code > versionCode) {
                    return true
                }
            } catch (e: Exception) {
                return false
            }
            return false
        }

        /**
         * 获取软件版本号
         */
        fun getVersionCode(context: Context?): Int {
            var versionCode = 0
            if (context == null) return versionCode
            try {
                versionCode =
                    context.packageManager.getPackageInfo(context.packageName, 0).versionCode
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return versionCode
        }

        /**
         * 获取软件版本
         */
        fun getVersionName(context: Context?): String? {
            var versionName: String? = null
            if (context == null) return null
            versionName = try {
                context.packageManager.getPackageInfo(context.packageName, 0).versionName
            } catch (e: PackageManager.NameNotFoundException) {
                return versionName
            }
            return versionName
        }
    }




}