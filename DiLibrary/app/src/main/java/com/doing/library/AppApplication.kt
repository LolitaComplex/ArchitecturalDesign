package com.doing.library

import android.app.Application
import com.doing.hilibrary.log.DiLogManager
import com.doing.hilibrary.log.DiLogConfig

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        DiLogManager.init(object : DiLogConfig() {
            
        })
    }
}