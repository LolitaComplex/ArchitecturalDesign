package com.doing.diproject.home.service

import com.doing.diproject.home.model.CourseNotice
import com.doing.diproject.home.model.Profile
import com.doing.hilibrary.restful.DiCall
import com.doing.hilibrary.restful.schema.Get

interface ProfileService {

    @Get("user/profile")
    fun profile(): DiCall<Profile>

    @Get("notice")
    fun notice(): DiCall<CourseNotice>
}