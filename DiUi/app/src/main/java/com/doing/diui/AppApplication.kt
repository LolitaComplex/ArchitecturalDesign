package com.doing.diui

import android.app.Application
import com.doing.hilibrary.log.DiLogConfig
import com.doing.hilibrary.log.DiLogManager

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        DiLogManager.init(object : DiLogConfig() {

        })
    }
}