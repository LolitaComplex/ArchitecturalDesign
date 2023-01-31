package com.doing.hilibrary.restful.schema

/**
 * @Headers({"name=doing", "sex=1"})
 * fun getBeijing()
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Headers(vararg val headers: String)
