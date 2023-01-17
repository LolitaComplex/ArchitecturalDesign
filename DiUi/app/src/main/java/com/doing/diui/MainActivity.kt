package com.doing.diui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.MainActivity_btn_bottom).setOnClickListener {
            startActivity(Intent(this, DiTabBottomActivity::class.java))
        }

        findViewById<Button>(R.id.MainActivity_btn_top).setOnClickListener {
            startActivity(Intent(this, DiTabTopActivity::class.java))
        }

    }
}