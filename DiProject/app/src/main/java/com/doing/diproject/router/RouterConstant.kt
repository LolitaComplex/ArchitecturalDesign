package com.doing.diproject.router

object RouterConstant {

    const val ROUTE_INTERCEPTOR_GLOBAL_PERMISSION = "/interceptor/global"
    const val ROUTE_ACTIVITY_ROUTER_SERVICE = "/activity/default"
    const val ROUTE_ACTIVITY_ROUTER_ERROR = "/activity/error"

    const val ROUTE_ACTIVITY_LOGIN = "/activity/login"
    const val ROUTE_ACTIVITY_REGISTER = "/activity/register"

    const val ROUTER_FLAG_LOGIN = 1
    const val ROUTER_FLAG_AUTHENTICATION = ROUTER_FLAG_LOGIN ushr 1
    const val ROUTER_FLAG_VIP = ROUTER_FLAG_AUTHENTICATION ushr 1
}