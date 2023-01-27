package com.doing.dinavigator.entity

class BottomBarKt(var tabs: List<Tab>) {
    constructor() : this(mutableListOf())

    class Tab(
        var size: Int,
        var enable: Boolean,
        var index: Int,
        var pageUrl: String,
        var title: String
    ) {
      constructor(): this(0, false, 0, "", "")
    }
}