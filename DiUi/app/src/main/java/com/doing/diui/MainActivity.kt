package com.doing.diui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.doing.diui.page.BannerActivity
import com.doing.diui.page.DiTabBottomActivity
import com.doing.diui.page.DiTabTopActivity
import com.doing.diui.page.RefreshActivity
import com.doing.hilibrary.global.DiActivityManager

class MainActivity : AppCompatActivity(), (Boolean) -> Unit {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.MainActivity_btn_bottom).setOnClickListener {
            startActivity(Intent(this, DiTabBottomActivity::class.java))
        }

        findViewById<Button>(R.id.MainActivity_btn_top).setOnClickListener {
            startActivity(Intent(this, DiTabTopActivity::class.java))
        }

        findViewById<Button>(R.id.MainActivity_btn_refresh).setOnClickListener {
            startActivity(Intent(this, RefreshActivity::class.java))
        }

        findViewById<Button>(R.id.MainActivity_btn_banner).setOnClickListener {
            startActivity(Intent(this, BannerActivity::class.java))
        }

        DiActivityManager.instance.registerOnBackgroundChangeListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        DiActivityManager.instance.unRegisterOnBackgroundChangeListener(this)
    }

    override fun invoke(isBackground: Boolean) {
        val content = if (isBackground) "后台" else "前台"
        Toast.makeText(this, content, Toast.LENGTH_LONG).show()
    }
}