package com.doing.diui.refresh.common

import com.doing.diui.refresh.view.DiOverView

interface IDiRefresh {

    fun refreshFinished()
    fun setDisableRefreshScroll(isScroll: Boolean)
    fun setOnRefreshListener(listener: OnRefreshListener)
    fun setRefreshOverView(overView: DiOverView)

    interface OnRefreshListener {
        fun onRefresh()
        fun enableRefresh(): Boolean
    }
}