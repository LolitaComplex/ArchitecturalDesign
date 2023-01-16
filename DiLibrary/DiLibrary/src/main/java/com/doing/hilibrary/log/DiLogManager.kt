package com.doing.hilibrary.log

class DiLogManager private constructor(

    private val config: DiLogConfig
) {

    private val printers = ArrayList<DiLogPrinter>()

    companion object {
        private lateinit var instance: DiLogManager

        fun init(config: DiLogConfig) {
            instance = DiLogManager(config).apply {
                registerLogPrinter(config.getDefaultLogPrinter())
            }
        }

        fun getInstance(): DiLogManager {
            return instance
        }
    }

    fun getConfig(): DiLogConfig {
        return config
    }

    fun registerLogPrinter(printer: DiLogPrinter) {
        printers.add(printer)
    }

    fun getPrinters(): List<DiLogPrinter> {
        return printers
    }
}