package com.doing.navigationcompiler

@Target(AnnotationTarget.TYPE)
@Retention(AnnotationRetention.SOURCE)
annotation class Destination(val pageUrl: String, val asStarter: Boolean = false)