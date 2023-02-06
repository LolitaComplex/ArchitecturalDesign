package com.doing.diproject.common

import com.alibaba.android.arouter.launcher.ARouter
import com.doing.dicommon.BuildConfig
import com.doing.dicommon.DiBaseApplication

class DiAppApplication : DiBaseApplication() {

    override fun onCreate() {
        super.onCreate()

        ARouter.init(this)

        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
    }
}