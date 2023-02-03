package com.doing.diproject.account

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.doing.diproject.R
import com.doing.diproject.net.ApiFactory
import com.doing.diui.view.input.InputItemLayout
import com.doing.hilibrary.log.DiLog
import com.doing.hilibrary.restful.DiCallback
import com.doing.hilibrary.restful.DiHttpException
import com.doing.hilibrary.restful.DiResponse

@Route(path = AccountConstant.ROUTE_ACTIVITY_REGISTER)
class RegisterActivity : AppCompatActivity() {

    private lateinit var mEtOrderId: InputItemLayout
    private lateinit var mEtMoocId: InputItemLayout
    private lateinit var mEtUserName: InputItemLayout
    private lateinit var mEtPassword: InputItemLayout
    private lateinit var mEtPasswordConfirm: InputItemLayout


    companion object {
        const val TAG = "RegisterActivity"
        const val INTENT_KEY_USERNAME = "userName"
        const val INTENT_KEY_PASSWORD = "password"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val tvBack = findViewById<TextView>(R.id.RegisterActivity_tv_back)
        tvBack.setText(R.string.if_back)
        tvBack.setOnClickListener {
            onBackPressed()
        }

        findViewById<Button>(R.id.RegisterActivity_btn_submit).setOnClickListener {
            onRegister()
        }

        mEtOrderId = findViewById<InputItemLayout>(R.id.RegisterActivity_input_orderid)
        mEtMoocId = findViewById<InputItemLayout>(R.id.RegisterActivity_input_moocid)
        mEtUserName = findViewById<InputItemLayout>(R.id.RegisterActivity_input_username)
        mEtPassword = findViewById<InputItemLayout>(R.id.RegisterActivity_input_password)
        mEtPasswordConfirm = findViewById<InputItemLayout>(
            R.id.RegisterActivity_input_password_confirm)
    }

    private fun onRegister() {
        val orderId = mEtOrderId.getEditText().text.toString()
        val moocId = mEtMoocId.getEditText().text.toString()
        val userName = mEtUserName.getEditText().text.toString()
        val password = mEtPassword.getEditText().text.toString()
        val passwordConfirm = mEtPasswordConfirm.getEditText().text.toString()

        if (orderId.isEmpty() || moocId.isEmpty() || userName.isEmpty() ||
            password.isEmpty() || passwordConfirm.isEmpty()) {
            Toast.makeText(this, "必须全部填写", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != passwordConfirm) {
            Toast.makeText(this, "密码必须相同", Toast.LENGTH_SHORT).show()
            return
        }

        ApiFactory.create(AccountService::class.java)
            .register(userName, password, moocId, orderId)
            .enqueue(object : DiCallback<String> {
                override fun onSuccess(response: DiResponse<String>) {
                    DiLog.d(TAG, "code: ${response.code} msg: ${response.msg} data: ${response.data}")
                    setResult(Activity.RESULT_OK, Intent().apply {
                        putExtra(RegisterActivity.INTENT_KEY_USERNAME, userName)
                        putExtra(RegisterActivity.INTENT_KEY_PASSWORD, password)
                    })
                    finish()
                }

                override fun onFailure(exception: DiHttpException) {
                    DiLog.e(TAG, exception, exception.errorMsg)
                }
            })



    }
}