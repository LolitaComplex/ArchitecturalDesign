package com.doing.diproject.detail.service

import com.doing.diproject.detail.model.Detail
import com.doing.hilibrary.restful.DiCall
import com.doing.hilibrary.restful.schema.Get
import com.doing.hilibrary.restful.schema.Path

interface DetailService {

    @Get("goods/detail/{id}")
    fun getDetailContent(@Path("id") id: String): DiCall<Detail>
}