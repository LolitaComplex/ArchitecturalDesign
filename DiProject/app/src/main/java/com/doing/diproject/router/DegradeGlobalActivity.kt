package com.doing.diproject.router

import android.os.Bundle
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.doing.diproject.R
import com.doing.diproject.common.DiBaseActivity
import com.doing.diui.view.error.EmptyView


@Route(path = RouterConstant.ACTIVITY_ROUTER_ERROR)
class DegradeGlobalActivity : DiBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_global_degrage)

        findViewById<TextView>(R.id.DegradeGlobalActivity_tv_back).setOnClickListener {
            onBackPressed()
        }

        val emptyView = findViewById<EmptyView>(R.id.DegradeGlobalActivity_empty_view)
        emptyView.setIcon(R.string.if_empty)
    }
}