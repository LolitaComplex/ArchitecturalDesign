package com.doing.diplayground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.Choreographer
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class BlockCanaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_block_canary)

        window.decorView.post {  }

        trackSysStack()
    }

    private fun trackSysStack() {
        Executors.newSingleThreadExecutor().execute {
            for (i in 0 until 10) {
                val start = System.currentTimeMillis()
                val format = format(Looper.getMainLooper().thread.stackTrace)
                val time = System.currentTimeMillis() - start
                Log.w("BlockCanaryActivity", "time: $time $format")
            }
        }
    }

    private fun format(traces: Array<StackTraceElement>): String {
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
}