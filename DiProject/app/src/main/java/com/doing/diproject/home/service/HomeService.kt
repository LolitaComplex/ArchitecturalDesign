package com.doing.diproject.home.service

import com.doing.diproject.home.model.Category
import com.doing.hilibrary.restful.DiCall
import com.doing.hilibrary.restful.schema.Get

interface HomeService {

    @Get("category/categories")
    fun getCategory(): DiCall<List<Category>>

}