package com.fengyongge.androidutils.utils

import android.os.Environment
import android.text.TextUtils
import java.io.File
import java.math.BigDecimal

object FileUtils {

    val isSDCardAlive: Boolean
        get() = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

    fun delete(file: File?, except: String?) {
        if (file == null) {
            return
        }
        if (file.isDirectory) {
            val children = file.list()
            for (c in children) {
                val childFile = File(file, c)
                if (!TextUtils.equals(childFile.name, except)) {
                    delete(childFile)
                }
            }
        } else {
            if (!TextUtils.equals(file.name, except)) {
                file.delete()
            }
        }
    }

    fun delete(file: File?): Boolean {
        if (file == null) {
            return false
        }
        if (file.isDirectory) {
            val children = file.list()
            for (c in children) {
                val success =
                    delete(
                        File(
                            file,
                            c
                        )
                    )
                if (!success) {
                    return false
                }
            }
        }
        return file.delete()
    }

    fun getSize(file: File): Long {
        var size: Long = 0
        try {
            val fileList = file.listFiles()
            for (f in fileList) {
                size = if (f.isDirectory) {
                    size + getSize(f)
                } else {
                    size + f.length()
                }
            }
        } catch (ignore: Exception) {
        }
        return size
    }

    /**
     * 格式化单位
     */
    fun formatSize(size: Double): String {
        val kiloByte = size / 1024
        if (kiloByte < 1) {
            return "0KB"
        }
        val megaByte = kiloByte / 1024
        if (megaByte < 1) {
            val result1 =
                BigDecimal(java.lang.Double.toString(kiloByte))
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB"
        }
        val gigaByte = megaByte / 1024
        if (gigaByte < 1) {
            val result2 =
                BigDecimal(java.lang.Double.toString(megaByte))
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB"
        }
        val teraBytes = gigaByte / 1024
        if (teraBytes < 1) {
            val result3 =
                BigDecimal(java.lang.Double.toString(gigaByte))
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB"
        }
        val result4 = BigDecimal(teraBytes)
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB"
    }
}