package com.doing.dirouter.page.interceptor

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.doing.dirouter.RouterConstant
import java.lang.RuntimeException

@Interceptor(name = RouterConstant.INTERCEPTOR_GLOBAL_PERMISSION, priority = 0)
class PageInterceptor : IInterceptor {

    private lateinit var context: Context

    override fun init(context: Context) {
        this.context = context
    }

    override fun process(postcard: Postcard, callback: InterceptorCallback) {
        val flag = postcard.extra

        if ((flag and RouterConstant.ROUTER_FLAG_LOGIN) != 0) {
            callback.onInterrupt(RuntimeException("need login"))
            showToast("请先登录")
        } else if ((flag and RouterConstant.ROUTER_FLAG_AUTHENTICATION) != 0) {
            callback.onInterrupt(RuntimeException("need auth"))
            showToast("请先认证")
        } else if ((flag and RouterConstant.ROUTER_FLAG_VIP) != 0) {
            callback.onInterrupt(RuntimeException("need become vip"))
            showToast("请先成为会员")
        } else {
            callback.onContinue(postcard)
        }
    }

    private fun showToast(content: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(this.context, content, Toast.LENGTH_SHORT).show()
        }
    }
}