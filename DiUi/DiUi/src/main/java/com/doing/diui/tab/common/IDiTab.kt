package com.doing.diui.tab.common

interface IDiTab<Data>  {

    fun setTabInfo(index: Int, data: Data)

    fun resetTab(height: Int)
}