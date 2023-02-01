package com.doing.diproject

import com.doing.hilibrary.restful.DiCall
import com.doing.hilibrary.restful.schema.Field
import com.doing.hilibrary.restful.schema.Get
import org.json.JSONObject

interface TestService {

    @Get("cities")
    fun getCities(@Field("name") name: String): DiCall<String>

}