package com.doing.diui.tab.common

import android.view.ViewGroup

interface IDiTabLayout<Tab : ViewGroup, Data> {

    fun findTab(data: Data): Tab?

    fun addOnTabSelectedListener(listener: OnTabSelectedListener<Data>)

    fun select(data: Data)

    fun inflateInfo(dataList: List<Data>)

    interface OnTabSelectedListener<Data> {
        fun onTabSelectedChange(index: Int, currentData: Data)
    }
}