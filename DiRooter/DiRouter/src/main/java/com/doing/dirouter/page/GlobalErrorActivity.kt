package com.doing.dirouter.page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.alibaba.android.arouter.facade.annotation.Route
import com.doing.dirouter.R
import com.doing.dirouter.RouterConstant

@Route(path = RouterConstant.ACTIVITY_ROUTER_ERROR)
class GlobalErrorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_global_error)

        findViewById<Button>(R.id.GlobalErrorActivity_btn_refresh).setOnClickListener {

        }
    }
}