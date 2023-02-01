package com.doing.diproject.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.doing.diproject.R
import com.doing.diproject.common.DiBaseActivity
import com.doing.diproject.net.ApiFactory
import com.doing.diui.view.input.InputItemLayout
import com.doing.hilibrary.log.DiLog
import com.doing.hilibrary.restful.DiCallback
import com.doing.hilibrary.restful.DiResponse

class LoginActivity : DiBaseActivity() {

    private lateinit var mEtItemUsername: InputItemLayout
    private lateinit var mEtPassword: InputItemLayout

    companion object {
        const val TAG = "LoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val tvBack = findViewById<TextView>(R.id.LoginActivity_tv_back)
        tvBack.setOnClickListener {
            onBackPressed()
        }

        findViewById<TextView>(R.id.LoginActivity_tv_register).setOnClickListener {
            // 注册
        }

        mEtItemUsername = findViewById<InputItemLayout>(R.id.LoginActivity_input_username)
        mEtPassword = findViewById<InputItemLayout>(R.id.LoginActivity_input_password)

        findViewById<Button>(R.id.LoginActivity_btn_login).setOnClickListener {
            onLogin()
        }
    }

    private fun onLogin() {
        val username: String? = mEtItemUsername.getEditText().text.toString()
        val password: String? = mEtPassword.getEditText().text.toString()

        if (username.isNullOrBlank() || password.isNullOrBlank()) {
            Toast.makeText(this, "输出有误", Toast.LENGTH_SHORT).show()
            return
        }

        ApiFactory.create(AccountService::class.java).login(username, password)
            .enqueue(object : DiCallback<String> {
                override fun onSuccess(response: DiResponse<String>) {
                    DiLog.d(TAG, "login response code: ${response.code} " +
                            "\t msg: ${response.msg} data: ${response.data}")
                }

            })
    }
}