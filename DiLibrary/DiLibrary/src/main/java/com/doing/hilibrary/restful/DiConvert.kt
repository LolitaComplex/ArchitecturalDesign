package com.doing.hilibrary.restful

import java.lang.reflect.Type

interface DiConvert {

    fun <T> convert(json: String, dataType: Type): DiResponse<T>
}