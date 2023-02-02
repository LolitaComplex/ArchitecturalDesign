package com.doing.diproject.account

import com.doing.hilibrary.restful.DiCall
import com.doing.hilibrary.restful.schema.Field
import com.doing.hilibrary.restful.schema.PostForm

interface AccountService {

    @PostForm("user/login")
    fun login(@Field("userName") userName: String,
        @Field("password") password: String): DiCall<String>

    @PostForm("user/registration")
    fun register(@Field("userName") username: String, @Field("password") password: String,
        @Field("imoocId") imoocId: String, @Field("orderId") orderId: String): DiCall<String>
}