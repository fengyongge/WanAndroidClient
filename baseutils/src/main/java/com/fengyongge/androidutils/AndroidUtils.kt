package com.fengyongge.androidutils

import android.content.Context


class AndroidUtils(context: Context) {

    companion object {
        private var instance: AndroidUtils?= null
        private lateinit var context:Context

        fun getInstance(context: Context): AndroidUtils {
            Companion.context = context
            instance =
                AndroidUtils(context)
            return instance!!
        }

        fun getAppContext(): Context{
            return context
        }
    }

}