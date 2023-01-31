package com.doing.hilibrary.restful.schema

/**
 * @Get("/city/{province}")
 * fun getCity(@Path("province") path: String)
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Path(val path: String)
