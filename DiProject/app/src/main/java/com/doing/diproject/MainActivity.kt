package com.doing.diproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.doing.diproject.common.DiBaseActivity

class MainActivity : DiBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}