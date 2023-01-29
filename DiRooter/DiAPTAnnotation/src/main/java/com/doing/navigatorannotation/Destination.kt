package com.doing.navigatorannotation

@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class Destination(val pageUrl: String, val isStarter: Boolean = false)