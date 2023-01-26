package com.doing.dirooter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.doing.dinavigator.NavMainActivity
import com.doing.navigatorannotation.DestinationJava

@DestinationJava(pageUrl = "dirooter://activity/main")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.MainActivity_btn_navigation).setOnClickListener {
            startActivity(Intent(this, NavMainActivity::class.java))
        }
    }
}