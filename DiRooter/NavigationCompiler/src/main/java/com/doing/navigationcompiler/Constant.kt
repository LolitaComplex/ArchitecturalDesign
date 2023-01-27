package com.doing.navigationcompiler

object Constant {

    const val TAG = "NavigatorProcessor"
    private const val PAGE_TYPE_ACTIVITY = "Activity"
    private const val PAGE_TYPE_FRAGMENT = "Fragment"
    private const val PAGE_TYPE_DIALOG = "Dialog"

    @JvmInline
    value class PageType(val type: String)

    val TypeActivity = PageType(PAGE_TYPE_ACTIVITY)
    val TypeFragment = PageType(PAGE_TYPE_FRAGMENT)
    val TypeDialog = PageType(PAGE_TYPE_DIALOG)

    val OUTPUT_FILE_NAME = "destination.json"
}