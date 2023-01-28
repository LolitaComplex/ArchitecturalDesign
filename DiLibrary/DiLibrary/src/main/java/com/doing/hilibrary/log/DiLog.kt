package com.doing.hilibrary.log

object DiLog {

    @JvmStatic
    fun d(tag: String = defaultTag(), vararg content: Any) {
        log(DiLogType.D, tag, *content)
    }


    @JvmStatic
    fun v(tag: String = defaultTag(), vararg content: Any) {
        log(DiLogType.V, tag, *content)
    }

    @JvmStatic
    fun i(tag: String = defaultTag(), vararg content: Any) {
        log(DiLogType.I, tag, *content)
    }

    @JvmStatic
    fun w(tag: String = defaultTag(), vararg content: Any) {
        log(DiLogType.W, tag, *content)
    }

    @JvmStatic
    fun e(tag: String = defaultTag(), e: Throwable, vararg content: Any) {
        log(DiLogType.E, tag, *content)
    }

    @JvmStatic
    fun a(tag: String = defaultTag(), vararg content: Any) {
        log(DiLogType.A, tag, *content)
    }

    @JvmStatic
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