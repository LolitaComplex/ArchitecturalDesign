package com.doing.hilibrary.log

import android.util.Log

class DiLogcatPrinter : DiLogPrinter {

    override fun print(config: DiLogConfig, priority: Int, tag: String, printContent: String) {
        Log.println(priority, tag, printContent)
    }
}