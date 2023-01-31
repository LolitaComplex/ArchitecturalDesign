package com.doing.kt.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.doing.kt.coroutines.R

class KtCoroutinesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kt_coroutines)

        val coroutine: KtCoroutine = KtCoroutine()
        findViewById<Button>(R.id.KtCoroutines_btn_scene1).setOnClickListener {
            coroutine.startScene1()
        }

        findViewById<Button>(R.id.KtCoroutines_btn_scene2).setOnClickListener {
            coroutine.startScene2()
        }

        findViewById<Button>(R.id.KtCoroutines_btn_scene3).setOnClickListener {
        }

    }
}