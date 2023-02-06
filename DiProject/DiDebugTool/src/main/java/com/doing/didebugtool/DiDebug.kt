package com.doing.didebugtool

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class DiDebug(val name: String, val desc: String = "")
