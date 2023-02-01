package com.doing.diproject.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.doing.diproject.R
import com.doing.diproject.common.DiBaseActivity

class LoginActivity : DiBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val tvBack = findViewById<TextView>(R.id.LoginActivity_tv_back)
        tvBack.text = getString(R.string.if_back)
        tvBack.setOnClickListener {
            finish()
        }
    }
}