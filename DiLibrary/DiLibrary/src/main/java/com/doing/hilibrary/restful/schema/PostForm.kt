package com.doing.hilibrary.restful.schema

/**
 * @PostForm("/users/info")
 * fun uploadInfo(@Field("name") val name: String, @Field("sex") val sex: Int)
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class PostForm(val path: String)