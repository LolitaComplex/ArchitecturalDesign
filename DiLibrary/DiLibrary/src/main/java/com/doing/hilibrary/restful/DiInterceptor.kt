package com.doing.hilibrary.restful

interface DiInterceptor {

    fun intercept(chain: Chain): Boolean

    interface Chain {

        fun request(): DiRequest

        fun response(): DiResponse<*>?
    }
}