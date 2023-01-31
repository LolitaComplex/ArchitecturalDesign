package com.doing.hilibrary.restful

interface DiCall<T> {

    fun execute(): DiResponse<T>

    fun enqueue(callback: DiCallback<T>)

    interface Factory {

        fun newCall(request: DiRequest) : DiCall<*>
    }
}