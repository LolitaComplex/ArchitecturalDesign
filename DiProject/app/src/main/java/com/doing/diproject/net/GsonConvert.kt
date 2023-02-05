package com.doing.diproject.net

import com.doing.hilibrary.restful.DiConvert
import com.doing.hilibrary.restful.DiResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Type

class GsonConvert : DiConvert {

    private val gson = Gson()

    override fun <T> convert(json: String, dataType: Type): DiResponse<T> {
        val response = DiResponse<T>()
        val jsonObj = JSONObject(json)
        response.msg = jsonObj.optString("msg")
        response.code = jsonObj.optInt("code")
        val data = jsonObj.opt("data")
        if (data is JSONObject || data is JSONArray) {
            if (response.isSuccess()) {
                response.data = gson.fromJson(data.toString(), dataType)
            } else {
                // 协议如果是错误，会返回一个JsonObject
                val type = object : TypeToken<MutableMap<String, String>>() {}.type
                response.errorData = gson.fromJson(data.toString(), type)
            }
        }
        response.rawData = json

        return response
    }
}