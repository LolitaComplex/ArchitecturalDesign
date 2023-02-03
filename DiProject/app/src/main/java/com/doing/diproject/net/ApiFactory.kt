package com.doing.diproject.net

import com.doing.hilibrary.restful.DiRestful

object ApiFactory {

    private const val HTTP_BASE_URL = "https://api.devio.org/as/"

    private val restful = DiRestful(HTTP_BASE_URL, RetrofitFactory(HTTP_BASE_URL))

    init {
        restful.addInterceptor(TokenInterceptor())
        restful.addInterceptor(HttpInterceptor())
    }


    fun <T> create(service: Class<T>): T {
        return restful.create(service)
    }
}