package com.fengyongge.basecomponent.app

import androidx.multidex.MultiDexApplication

class BaseApplication: MultiDexApplication() {

    companion object{

        private var baseApplication: MultiDexApplication

        init {
            baseApplication = getBaseApplicaton()
        }


        fun getBaseApplicaton(): MultiDexApplication{

            return baseApplication
        }

    }

}