package com.doing.diui

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.doing.diui.page.*
import com.doing.diui.page.adapter.AdapterActivity
import com.doing.diui.page.lifecycle.LifecycleActivity
import com.doing.diui.page.navigation.NavigationActivity
import com.doing.hilibrary.global.DiActivityManager
import com.doing.hilibrary.util.DiDataBus
import kotlinx.android.synthetic.main.activity_main.*


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

        findViewById<Button>(R.id.MainActivity_btn_view_pager2).setOnClickListener {
            startActivity(Intent(this, ViewPager2Activity::class.java))
        }

        findViewById<Button>(R.id.MainActivity_btn_navigation).setOnClickListener {
            startActivity(Intent(this, NavigationActivity::class.java))
        }

        DiActivityManager.instance.registerOnBackgroundChangeListener(this)

        MainActivity_btn_adapter.setOnClickListener {
            startActivity(Intent(this, AdapterActivity::class.java))
        }

        MainActivity_btn_lifecycle.setOnClickListener {
            startActivity(Intent(this, LifecycleActivity::class.java))
        }
        DiDataBus.with<String>("hello_live_bus").postStickData("这是发送的第一个消息")
        DiDataBus.with<String>("live_data_bus").observe(this) {data ->
            Toast.makeText(this, "LiveData: $data", Toast.LENGTH_SHORT).show()
        }
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