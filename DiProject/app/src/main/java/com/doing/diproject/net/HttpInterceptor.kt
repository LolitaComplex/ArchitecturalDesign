package com.doing.diproject.net

import com.alibaba.android.arouter.launcher.ARouter
import com.doing.diproject.router.DegradeGlobalActivity
import com.doing.diproject.router.RouterConstant
import com.doing.hilibrary.restful.DiInterceptor

class HttpInterceptor : DiInterceptor {


    override fun intercept(chain: DiInterceptor.Chain): Boolean {
        val response = chain.response()
        if (response != null) {
            when (response.code) {
                NetConstant.RC_NEED_LOGIN -> {
                    ARouter.getInstance().build(RouterConstant.ROUTE_ACTIVITY_LOGIN).navigation()
                }
                NetConstant.RC_AUTH_TOKEN_EXPIRED, NetConstant.RC_AUTH_TOKEN_INVALID,
                    NetConstant.RC_USER_FORBID -> {
                    ARouter.getInstance().build(RouterConstant.ROUTE_ACTIVITY_ROUTER_ERROR)
                        .withString(DegradeGlobalActivity.TITLE, "非法访问")
                        .withString(DegradeGlobalActivity.DESC, response.msg)
                        .withString(DegradeGlobalActivity.ACTION, response.errorData?.get("httpUrl"))
                        .navigation()
                }
            }
        }


        return false
    }
}