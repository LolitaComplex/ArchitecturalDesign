package com.doing.hilibrary.restful

import java.io.InputStream

class DiResponse<T> {

    companion object {
        const val SUCCESS = 200
    }

    var code: Int = -1
    var data: T? = null
    var errorData: Map<String, String>? = null
    var msg: String = ""
    var rawData: String = ""
    val stream: InputStream? = null

    fun successful(): Boolean {
        return code == SUCCESS
    }


}