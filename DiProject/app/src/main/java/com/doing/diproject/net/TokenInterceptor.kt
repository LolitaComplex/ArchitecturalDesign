package com.doing.diproject.net

import com.doing.diproject.account.AccountConstant
import com.doing.hilibrary.restful.DiInterceptor
import com.doing.hilibrary.util.SPUtil

class TokenInterceptor : DiInterceptor {

    override fun intercept(chain: DiInterceptor.Chain): Boolean {

        if (chain.response() == null) {
            val request = chain.request()
            val sp = SPUtil.getString(AccountConstant.KEY_LOGIN_SUCCESS_TOKEN)

            if (!sp.isNullOrEmpty()) {
                request.headers["boarding-pass"] = sp
            }
            request.headers["auth-token"] = "MTU5Mjg1MDg3NDcwNw11.26=="
        }
        return false
    }
}