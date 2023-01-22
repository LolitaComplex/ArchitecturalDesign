package com.doing.diui

import android.app.ActivityManager
import android.app.Application
import com.doing.hilibrary.global.DiActivityManager
import com.doing.hilibrary.log.DiLogConfig
import com.doing.hilibrary.log.DiLogManager

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        DiLogManager.init(object : DiLogConfig() {

        })

        DiActivityManager.instance.init(this)
    }
}