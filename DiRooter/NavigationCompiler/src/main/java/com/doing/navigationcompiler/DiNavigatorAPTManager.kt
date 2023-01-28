package com.doing.navigationcompiler

import com.alibaba.fastjson.JSONObject

class DiNavigatorAPTManager private constructor() {

    companion object {
        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { DiNavigatorAPTManager() }
    }

    val destinations : MutableMap<String, JSONObject> = mutableMapOf()
}