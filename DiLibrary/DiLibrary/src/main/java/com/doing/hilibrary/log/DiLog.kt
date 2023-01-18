package com.doing.hilibrary.log

import android.util.Log
import java.lang.Exception
import kotlin.text.StringBuilder

object DiLog {

    fun d(vararg content: Any) {
        log(DiLogType.D, defaultTag(), *content)
    }

    fun dt(tag: String, vararg content: Any) {
        log(DiLogType.D, tag, *content)
    }

    fun v(vararg content: Any) {
        log(DiLogType.V, defaultTag(), *content)
    }

    fun vt(tag: String, vararg content: Any) {
        log(DiLogType.V, tag, *content)
    }

    fun i(vararg content: Any) {
        log(DiLogType.I, defaultTag(), *content)
    }

    fun it(tag: String, vararg content: Any) {
        log(DiLogType.I, tag, *content)
    }

    fun w(vararg content: Any) {
        log(DiLogType.W, defaultTag(), *content)
    }

    fun wt(tag: String, vararg content: Any) {
        log(DiLogType.W, tag, *content)
    }

    fun e(e: Throwable, vararg content: Any) {
        log(DiLogType.E, defaultTag(), *content)
    }

    fun et(tag: String, e: Throwable, vararg content: Any) {
        log(DiLogType.E, tag, *content)
    }

    fun a(vararg content: Any) {
        log(DiLogType.A, defaultTag(), *content)
    }

    fun at(tag: String, vararg content: Any) {
        log(DiLogType.A, tag, *content)
    }

    fun log(type: DiLogType.Type, tag: String, vararg content: Any) {
        val config = DiLogManager.getInstance().getConfig()

        if (!config.enable()) {
            return;
        }

        val builder = StringBuilder()
        if (config.getThreadFormatter() != null) {
            builder.append(config.getThreadFormatter()!!.format(Thread.currentThread()))
//                .append("\n")
        }

//        if (config.getStackTraceFormatter().stackTraceDepth() > 0) {
//            builder.append(config.getStackTraceFormatter().format(Throwable().stackTrace))
//                .append("\n")
//        }

        content.forEach {
            builder.append("$it ;")
        }

        if (content.isNotEmpty()) {
            builder.deleteCharAt(builder.length - 1)
        }

        DiLogManager.getInstance().getPrinters().forEach { printer ->
            printer.print(config, type.type, tag, builder.toString())
        }
    }

    private fun defaultTag(): String {
        return DiLogManager.getInstance().getConfig().getGlobalTag();
    }
}