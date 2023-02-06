package com.doing.diproject.account

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.doing.diproject.R
import com.doing.dicommon.DiBaseActivity
import com.doing.diproject.net.ApiFactory
import com.doing.diui.view.input.InputItemLayout
import com.doing.hilibrary.log.DiLog
import com.doing.hilibrary.restful.DiCallback
import com.doing.hilibrary.restful.DiResponse
import com.doing.hilibrary.util.SPUtil

@Route(path = AccountConstant.ROUTE_ACTIVITY_LOGIN)
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
        tvBack.setText(R.string.if_back)
        tvBack.setOnClickListener {
            onBackPressed()
        }

        findViewById<TextView>(R.id.LoginActivity_tv_register).setOnClickListener {
            ARouter.getInstance()
                .build(AccountConstant.ROUTE_ACTIVITY_REGISTER)
                .navigation()
        }

        mEtItemUsername = findViewById<InputItemLayout>(R.id.LoginActivity_input_username)
        mEtPassword = findViewById<InputItemLayout>(R.id.LoginActivity_input_password)

        findViewById<Button>(R.id.LoginActivity_btn_login).setOnClickListener {
            onLogin()
        }

    }

    private fun onLogin() {
        val username: String = mEtItemUsername.getEditText().text.toString()
        val password: String = mEtPassword.getEditText().text.toString()

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "输出有误", Toast.LENGTH_SHORT).show()
            return
        }

        ApiFactory.create(AccountService::class.java).login(username, password)
            .enqueue(object : DiCallback<String> {
                override fun onSuccess(response: DiResponse<String>) {
                    if (response.isSuccess()) {
                        val token = response.data ?: ""
                        SPUtil.putString(AccountConstant.KEY_LOGIN_SUCCESS_TOKEN, token)
                        onBackPressed()
                    }


                    DiLog.d(TAG, "login response code: ${response.code} " +
                            "\t msg: ${response.msg} data: ${response.data}")
                }

            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null) {
            val userName = data.getStringExtra(RegisterActivity.INTENT_KEY_USERNAME)
            val password = data.getStringExtra(RegisterActivity.INTENT_KEY_PASSWORD)
            mEtPassword.getEditText().setText(password)
            mEtItemUsername.getEditText().setText(userName)
        }
    }
}