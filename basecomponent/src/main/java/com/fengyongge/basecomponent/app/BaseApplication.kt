package com.fengyongge.basecomponent.app

import android.app.Application
import android.content.Context

open class BaseApplication: Application() {

    companion object {
        private lateinit var context:Context
        fun init(context: Context){
            Companion.context = context
        }

        fun getAppContext(): Context{
            return context
        }
    }

}