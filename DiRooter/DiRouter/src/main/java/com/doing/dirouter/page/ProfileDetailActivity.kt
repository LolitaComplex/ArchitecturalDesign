package com.doing.dirouter.page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.doing.dirouter.R
import com.doing.dirouter.RouterConstant

@Route(path = RouterConstant.ACTIVITY_ROUTER_PROFILE_DETAIL, extras = RouterConstant.ROUTER_FLAG_VIP
        or RouterConstant.ROUTER_FLAG_LOGIN)
class ProfileDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_detail)
    }
}