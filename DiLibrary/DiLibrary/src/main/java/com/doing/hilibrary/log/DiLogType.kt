package com.doing.hilibrary.log

import android.util.Log

object DiLogType {

    @JvmInline
    value class Type(val type: Int)

    val V = Type(Log.VERBOSE)
    val D = Type(Log.DEBUG)
    val I = Type(Log.INFO)
    val W = Type(Log.WARN)
    val E = Type(Log.ERROR)
    val A = Type(Log.ASSERT)
}