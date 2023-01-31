package com.doing.hilibrary.restful.schema

/**
 * @PostJson("/users/info")
 * fun uploadInfo()
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class PostJson(val path: String)