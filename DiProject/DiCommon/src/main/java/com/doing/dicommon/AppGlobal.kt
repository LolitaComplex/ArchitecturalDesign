package com.doing.dicommon

import android.content.Context

class AppGlobal private constructor(){

    private lateinit var context: Context

    companion object {
        @JvmStatic
        val instance = AppGlobal()
    }

    internal fun init(context: Context) {
        this.context = context
    }

    fun globalContext(): Context {
        return context
    }
}