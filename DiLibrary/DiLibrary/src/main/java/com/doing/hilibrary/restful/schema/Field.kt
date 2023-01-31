package com.doing.hilibrary.restful.schema

/**
 * @Get("/users/info")
 * fun uploadInfo(@Field("name") val name: String, @Field("sex") val sex: Int)
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Field(val value: String)
