package com.doing.hilibrary.restful

class Scheduler(
    private val callFactory: DiCall.Factory,
    private val interceptors: List<DiInterceptor>
) {
    fun newCall(request: DiRequest): DiCall<*> {
        val call = callFactory.newCall(request)
        return ProxyCall(call, request)
    }

    private inner class  ProxyCall<T>(private val delegate: DiCall<T>,
              private val request: DiRequest) : DiCall<T> {

        override fun execute(): DiResponse<T> {
            dispatchInterceptor(request)

            val response = delegate.execute()

            dispatchInterceptor(request, response)

            return response
        }

        override fun enqueue(callback: DiCallback<T>) {
            dispatchInterceptor(request)

            delegate.enqueue(object : DiCallback<T> {
                override fun onSuccess(response: DiResponse<T>) {
                    dispatchInterceptor(request, response)

                    callback.onSuccess(response)
                }

                override fun onFailure(exception: DiHttpException) {
                    callback.onFailure(exception)
                }
            })
        }

        private fun dispatchInterceptor(request: DiRequest, response: DiResponse<T>? = null) {
            if (interceptors.isEmpty()) {
                return
            }
            InterceptorChain(request, response).dispatch()
        }
    }

    private inner class InterceptorChain<T>(
        private val request: DiRequest,
        private val response: DiResponse<T>?
    ) : DiInterceptor.Chain {

        var callIndex = 0

        override fun request(): DiRequest {
            return request
        }

        override fun response(): DiResponse<*>? {
            return response
        }

        fun dispatch() {
            val interceptor = interceptors[callIndex]

            val isIntercept = interceptor.intercept(this)
            callIndex++
            if (!isIntercept && callIndex <= interceptors.size - 1) {
                dispatch()
            }
        }

    }
}