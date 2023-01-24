package com.doing.hilibrary.log

object DiLog {

    fun d(tag: String = defaultTag(), vararg content: Any) {
        log(DiLogType.D, tag, *content)
    }


    fun v(tag: String = defaultTag(), vararg content: Any) {
        log(DiLogType.V, tag, *content)
    }


    fun i(tag: String = defaultTag(), vararg content: Any) {
        log(DiLogType.I, tag, *content)
    }

    fun w(tag: String = defaultTag(), vararg content: Any) {
        log(DiLogType.W, tag, *content)
    }

    fun e(tag: String = defaultTag(), e: Throwable, vararg content: Any) {
        log(DiLogType.E, tag, *content)
    }

    fun a(tag: String = defaultTag(), vararg content: Any) {
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