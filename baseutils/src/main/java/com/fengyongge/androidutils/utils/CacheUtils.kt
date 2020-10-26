package com.fengyongge.androidutils.utils

import com.fengyongge.androidutils.AndroidUtils
import java.io.File

object CacheUtils {

    /**
     * 获取系统默认缓存文件夹
     * 优先返回SD卡中的缓存文件夹
     */
    fun cacheDir(): String {
        var cacheDir = ""
        var cacheFile: File? = null

        if (FileUtils.isSDCardAlive) {
            cacheFile = AndroidUtils.getAppContext().externalCacheDir
        }
        cacheFile?.let {
            cacheFile = AndroidUtils.getAppContext().cacheDir
            cacheDir = it.absolutePath
        }
        return cacheDir
    }

    /**
     * 获取系统默认缓存文件夹内的缓存大小
     */
    fun totalCacheSize(): String {
        var cacheSize: Long = FileUtils.getSize(AndroidUtils.getAppContext().cacheDir)
        if (FileUtils.isSDCardAlive) {
            var cacheFile = AndroidUtils.getAppContext().externalCacheDir
            cacheFile?.let {
                cacheSize += FileUtils.getSize(it)
            }
        }
        return FileUtils.formatSize(cacheSize.toDouble())
    }

    /**
     * 清除系统默认缓存文件夹内的缓存
     */
    fun clearAllCache() {
        FileUtils.delete(AndroidUtils.getAppContext().cacheDir)
        if (FileUtils.isSDCardAlive) {
            FileUtils.delete(AndroidUtils.getAppContext().externalCacheDir)
        }
    }
}