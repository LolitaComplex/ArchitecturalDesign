package com.doing.diproject.login

import com.doing.hilibrary.restful.DiCall
import com.doing.hilibrary.restful.schema.Field
import com.doing.hilibrary.restful.schema.PostForm
import com.doing.hilibrary.restful.schema.PostJson

interface AccountService {

    @PostForm("user/login")
    fun login(@Field("userName") userName: String,
        @Field("password") password: String): DiCall<String>
}