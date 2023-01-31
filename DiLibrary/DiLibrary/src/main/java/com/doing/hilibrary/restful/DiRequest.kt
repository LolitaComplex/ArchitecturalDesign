package com.doing.hilibrary.restful

import java.lang.reflect.Type

open class DiRequest {

    var requestType: Method = Default
    var path: String = ""
    var url = ""
    val headers = mutableMapOf<String, String>()
    lateinit var returnType: Type
    val parameters = mutableMapOf<String, String>()


    companion object {

        @JvmInline
        value class Method(val value: Int)

        val Get = Method(0)
        val PostJson = Method(1)
        val PostForm = Method(2)
        val Default = Method(-1)

        const val HEADER_SPLIT = "="
    }
}