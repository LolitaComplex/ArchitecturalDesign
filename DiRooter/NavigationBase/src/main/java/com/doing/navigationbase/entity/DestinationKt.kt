package com.doing.navigationbase.entity

class DestinationKt(
    var pageUrl: String,
    var id: Int,
    var isStarter: Boolean,
    var destType: String,
    var className: String
) {

    constructor() : this("", 0, false, "", "")
}
