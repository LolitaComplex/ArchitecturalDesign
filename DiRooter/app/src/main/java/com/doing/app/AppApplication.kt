package com.doing.app

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            ARouter.openDebug()
            ARouter.openLog()
        }

        ARouter.init(this)
    }
}