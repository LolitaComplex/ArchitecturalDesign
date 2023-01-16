package com.doing.library

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.doing.hilibrary.log.DiLog

class DiLogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_di_log)

        findViewById<Button>(R.id.mBtnPrint).setOnClickListener {
            DiLog.d("哈哈哈", "该开始努力了！", "刚把得！")
        }
    }
}