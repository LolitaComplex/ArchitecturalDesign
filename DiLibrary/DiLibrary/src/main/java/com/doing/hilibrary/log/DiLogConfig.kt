package com.doing.hilibrary.log

abstract class DiLogConfig  {

    fun getStackTraceFormatter(): DiStacktraceFormatter {
        return object : DiStacktraceFormatter() {}
    }

    fun getThreadFormatter(): DiLogFormatter<Thread>? {
        return DiThreadFormatter()
    }

    fun getDefaultLogPrinter(): DiLogPrinter {
        return DiLogcatPrinter()
    }

    fun getGlobalTag(): String {
        return "DiLog"
    }

    fun enable(): Boolean {
        return true
    }
}