package com.doing.dicommon.component

import android.app.Application
import com.doing.dicommon.AppGlobal
import com.doing.hilibrary.log.DiLogConfig
import com.doing.hilibrary.log.DiLogManager

open class DiBaseApplication : Application() {



    override fun onCreate() {
        super.onCreate()
        DiLogManager.init(object : DiLogConfig() {})

        AppGlobal.instance.init(this)
    }
}