package com.doing.didebugtool

import android.content.Intent
import android.os.Process
import com.doing.dicommon.AppGlobal
import com.doing.hilibrary.util.SPUtil

class DebugTool {

    fun buildVersion(): String {
        return "构建版本: ${BuildConfig.VSERSION_NAME} : ${BuildConfig.VERSION_CODE}"
    }

    fun buildTime(): String {
        return "构建时间: ${BuildConfig.BUILD_TIME}"
    }

    fun buildEnvironment(): String {
        return "构建环境: ${BuildConfig.BUILD_TYPE}"
    }

    @DiDebug(name = "一键开启Https降级", desc = "将继承Http，可以使用抓包工具明文抓包")
    fun degrade2Http() {
        SPUtil.putBoolean("${BuildConfig.BUILD_TYPE} degrade_http", true)

        val context = AppGlobal.instance.globalContext()
        val intent = context.packageManager
            .getLaunchIntentForPackage(context.packageName)
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)

            Process.killProcess(Process.myPid())
        }
    }
}