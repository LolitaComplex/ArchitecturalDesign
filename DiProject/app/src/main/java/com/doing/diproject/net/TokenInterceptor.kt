package com.doing.diproject.net

import com.doing.hilibrary.restful.DiInterceptor

class TokenInterceptor : DiInterceptor {

    override fun intercept(chain: DiInterceptor.Chain): Boolean {

        if (chain.response() == null) {
            val request = chain.request()
            request.headers["auth-token"] = "MTU5Mjg1MDg3NDcwNw11.26=="
        }
        return false
    }
}