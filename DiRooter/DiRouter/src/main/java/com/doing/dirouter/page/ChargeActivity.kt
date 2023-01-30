package com.doing.dirouter.page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.doing.dirouter.R
import com.doing.dirouter.RouterConstant

@Route(path = RouterConstant.ACTIVITY_ROUTER_CHARGE, extras = RouterConstant.ROUTER_FLAG_LOGIN)
class ChargeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charge)
    }
}