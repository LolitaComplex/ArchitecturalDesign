package com.doing.diproject.common

import android.app.Application
import com.doing.hilibrary.log.DiLogConfig
import com.doing.hilibrary.log.DiLogManager

open class DiBaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DiLogManager.init(object : DiLogConfig() {})
    }
}