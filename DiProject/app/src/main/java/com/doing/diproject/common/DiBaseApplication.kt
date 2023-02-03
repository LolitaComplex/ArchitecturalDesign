package com.doing.diproject.common

import android.app.Application
import android.os.Build
import com.alibaba.android.arouter.launcher.ARouter
import com.doing.diproject.BuildConfig
import com.doing.hilibrary.log.DiLogConfig
import com.doing.hilibrary.log.DiLogManager

open class DiBaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DiLogManager.init(object : DiLogConfig() {})

        ARouter.init(this)

        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
    }
}