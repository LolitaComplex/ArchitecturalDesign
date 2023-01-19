package com.doing.diui.refresh.demo

interface IDiDemoRefresh {

    fun finishRefresh()

    fun setHeadView(head: DiDemoRefreshHeadView)

    fun setOnRefreshListener(listener: OnDiDemoRefreshListener)

    interface OnDiDemoRefreshListener {
        fun onRefresh(diRefresh: IDiDemoRefresh)

        fun enableRefresh(): Boolean
    }
}