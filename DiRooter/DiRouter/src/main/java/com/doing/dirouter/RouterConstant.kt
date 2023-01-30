package com.doing.dirouter

object RouterConstant {

    const val ACTIVITY_ROUTER_MAIN = "/activity/router/main"
    const val ACTIVITY_ROUTER_PROFILE_DETAIL = "/activity/profile/detail"
    const val ACTIVITY_ROUTER_CHARGE = "/activity/charge"
    const val ACTIVITY_ROUTER_MEMBER_BENEFITS = "/activity/member/benefits"
    const val ACTIVITY_ROUTER_ERROR = "/activity/global/error"
    const val ACTIVITY_ROUTER_SERVICE = "/activity/global/service"

    const val ROUTER_FLAG_LOGIN = 0x01
    const val ROUTER_FLAG_AUTHENTICATION = ROUTER_FLAG_LOGIN shl 1
    const val ROUTER_FLAG_VIP = ROUTER_FLAG_AUTHENTICATION shl 1

    const val INTERCEPTOR_GLOBAL_PERMISSION = "/interceptor/global/permission"

}