package com.doing.diproject.home.model

import com.doing.hilibrary.restful.DiCall
import com.doing.hilibrary.restful.schema.Get

interface ProfileService {

    @Get("user/profile")
    fun profile(): DiCall<Profile>

}