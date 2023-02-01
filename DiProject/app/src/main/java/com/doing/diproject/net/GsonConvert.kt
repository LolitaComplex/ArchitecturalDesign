package com.doing.diproject.net

import com.doing.hilibrary.restful.DiConvert
import com.doing.hilibrary.restful.DiResponse
import com.google.gson.Gson
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
            response.data = gson.fromJson(data.toString(), dataType)
        }
        response.rawData = json

        return response
    }
}