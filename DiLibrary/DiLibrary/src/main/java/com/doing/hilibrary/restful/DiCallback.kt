package com.doing.hilibrary.restful

interface DiCallback<T> {

    fun onSuccess(response: DiResponse<T>)

    fun onFailure(exception: DiHttpException) {

    }
}