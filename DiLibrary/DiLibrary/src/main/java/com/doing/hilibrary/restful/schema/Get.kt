package com.doing.hilibrary.restful.schema

/**
 * @GET("/cities/beijing")
 * fun getBeijing()
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Get(val path: String)
