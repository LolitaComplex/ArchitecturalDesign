package com.doing.dirouter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter

@Route(path = RouterConstant.ACTIVITY_ROUTER_MAIN)
class RouterMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rooter_main)

        findViewById<Button>(R.id.RouterMainActivity_btn_profile).setOnClickListener {
            ARouter.getInstance().build(RouterConstant.ACTIVITY_ROUTER_PROFILE_DETAIL)
                .navigation()
        }

        findViewById<Button>(R.id.RouterMainActivity_btn_vip).setOnClickListener {
            ARouter.getInstance().build(RouterConstant.ACTIVITY_ROUTER_MEMBER_BENEFITS)
                .navigation()
        }

        findViewById<Button>(R.id.RouterMainActivity_btn_charge).setOnClickListener {
            ARouter.getInstance().build(RouterConstant.ACTIVITY_ROUTER_CHARGE)
                .navigation()
        }

        findViewById<Button>(R.id.RouterMainActivity_btn_error).setOnClickListener {
            ARouter.getInstance().build("/activity/error/other")
                .navigation()
        }
    }
}