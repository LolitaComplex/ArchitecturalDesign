package com.doing.hilibrary.restful

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.concurrent.ConcurrentHashMap

class DiRestful(private val baseUrl: String, callFactory: DiCall.Factory) {

    private val interceptors = mutableListOf<DiInterceptor>()
    private val scheduler = Scheduler(callFactory, interceptors)

    fun addInterceptor(interceptor: DiInterceptor) {
        if (interceptors.contains(interceptor)) {
            return
        }
        interceptors.add(interceptor)
    }

    fun <T> create(service: Class<T>): T {
        return Proxy.newProxyInstance(service.classLoader, arrayOf<Class<*>>(service),
            object : InvocationHandler {

                private val methodService = ConcurrentHashMap<Method, MethodParser>()

                override fun invoke(proxy: Any, method: Method, args: Array<out Any>?): Any {
                    var parser = methodService[method]
                    if (parser == null) {
                        parser = MethodParser(baseUrl, method)
                        methodService[method] = parser
                    }

                    val request: DiRequest = parser.newRequest(method, args)
                    return scheduler.newCall(request)
                }
            }) as T
    }
}