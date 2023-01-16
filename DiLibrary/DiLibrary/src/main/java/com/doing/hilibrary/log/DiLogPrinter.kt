package com.doing.hilibrary.log

interface DiLogPrinter {

    fun print(config: DiLogConfig, priority: Int, tag: String, printContent: String)
}