package com.doing.hilibrary.restful

class FactoryDelegate(
    private val callFactory: DiCall.Factory,
) {
    fun newCall(request: DiRequest): DiCall<*> {
        val call = callFactory.newCall(request)
        return ProxyCall(call, request)
    }

    class ProxyCall<T>(private val call: DiCall<T>, private val request: DiRequest) : DiCall<T> {

        override fun execute(): DiResponse<T> {
            TODO("Not yet implemented")
        }

        override fun enqueue(callback: DiCallback<T>) {
            TODO("Not yet implemented")
        }
    }
}