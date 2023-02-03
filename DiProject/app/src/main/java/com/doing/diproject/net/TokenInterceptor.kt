package com.doing.diproject.net

import com.doing.diproject.account.AccountConstant
import com.doing.hilibrary.restful.DiInterceptor
import com.doing.hilibrary.util.SPUtil

class TokenInterceptor : DiInterceptor {

    override fun intercept(chain: DiInterceptor.Chain): Boolean {

        if (chain.response() == null) {
            val request = chain.request()
            val token = SPUtil.getString(AccountConstant.KEY_LOGIN_SUCCESS_TOKEN)
                ?: "MTU5Mjg1MDg3NDcwNw11.26=="

            request.headers["auth-token"] = token
        }
        return false
    }
}