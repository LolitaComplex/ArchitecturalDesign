package com.doing.diui.refresh.demo

interface IDiDemoRefreshHead {

    enum class DiRefreshState {
        STATE_INIT,
        STATE_VISIBLE,
        STATE_REFRESH,
        STATE_OVER,
        STATE_OVER_RELEASE
    }

    fun init()
    fun onVisible()
    fun onRefresh()
    fun onOver()

    fun onScroll(distance: Int)

    fun setState(state: DiRefreshState)
    fun getState(): DiRefreshState
    fun getMaxDamp(): Float
    fun getMinDamp(): Float

}