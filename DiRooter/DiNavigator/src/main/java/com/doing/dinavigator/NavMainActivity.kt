package com.doing.dinavigator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class NavMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_main)

        findViewById<Button>(R.id.NavMainActivity_btn_bottom_nav).setOnClickListener {
            startActivity(Intent(this, BottomNavigationActivity::class.java))
        }
    }
}