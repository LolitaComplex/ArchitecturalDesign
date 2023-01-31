package com.doing.hilibrary.restful.schema

/**
 * @BaseUrl("https://com.doing.di/project")
 * fun test(...);
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class BaseUrl(val url: String)