package com.doing.hilibrary.restful

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.concurrent.ConcurrentHashMap

class DiRestful(val baseUrl: String, val callFactory: DiCall.Factory) {

    fun <T> create(service: Class<T>): T {
        return Proxy.newProxyInstance(service.classLoader, arrayOf<Class<*>>(service),
            object : InvocationHandler {

                private val methodService = ConcurrentHashMap<Method, MethodParser>()

                override fun invoke(proxy: Any, method: Method, args: Array<out Any>): Any {
                    var parser = methodService[method]
                    if (parser == null) {
                        parser = MethodParser(baseUrl, method)
                        methodService[method] = parser
                    }

                    val request: DiRequest = parser.newRequest(method, args)
                    return callFactory.newCall(request)
                }
            }) as T
    }
}