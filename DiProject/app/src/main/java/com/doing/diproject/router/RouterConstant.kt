package com.doing.diproject.router

object RouterConstant {

    const val INTERCEPTOR_GLOBAL_PERMISSION = "/interceptor/global"
    const val ACTIVITY_ROUTER_SERVICE = "/activity/default"
    const val ACTIVITY_ROUTER_ERROR = "/activity/error"

    const val ROUTER_FLAG_LOGIN = 1
    const val ROUTER_FLAG_AUTHENTICATION = ROUTER_FLAG_LOGIN ushr 1
    const val ROUTER_FLAG_VIP = ROUTER_FLAG_AUTHENTICATION ushr 1
}