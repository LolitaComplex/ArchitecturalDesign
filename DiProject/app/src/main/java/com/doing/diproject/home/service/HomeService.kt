package com.doing.diproject.home.service

import com.doing.diproject.home.model.Category
import com.doing.diproject.home.model.HomeList
import com.doing.hilibrary.restful.DiCall
import com.doing.hilibrary.restful.schema.Field
import com.doing.hilibrary.restful.schema.Get
import com.doing.hilibrary.restful.schema.Path

interface HomeService {

    @Get("category/categories")
    fun getCategory(): DiCall<List<Category>>

    @Get("home/{categories}")
    fun getCategoryList(@Path("categories") categoryId: String): DiCall<HomeList>

}