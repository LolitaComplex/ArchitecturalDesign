package com.doing.hilibrary.log


abstract class DiStacktraceFormatter : DiLogFormatter<Array<StackTraceElement>> {
    override fun format(traces: Array<StackTraceElement>): String {
        if (traces.isEmpty()) {
            return ""
        } else if (traces.size == 1) {
            return "\t-${traces[0]}"
        } else {
            val builder = StringBuilder(128)

            traces.forEachIndexed { index, element ->
                if (index == 0) {
                    builder.append("stackstace: \n")
                }

                if (index != traces.size - 1) {
                    builder.append("\t├ ")
                        .append(element.toString())
                        .append("\n")
                } else {
                    builder.append("\t└ ")
                        .append(element.toString())
                }
            }
            return builder.toString()
        }
    }

    fun stackTraceDepth(): Int {
        return 5
    }
}