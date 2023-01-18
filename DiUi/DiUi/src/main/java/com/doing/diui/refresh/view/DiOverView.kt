package com.doing.diui.refresh.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.doing.hilibrary.util.DiDisplayUtil

abstract class DiOverView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
    : FrameLayout(context, attrs) {

    enum class DiRefreshState {
        /**
         * 默认状态（未初始化时）
         */
        STATE_INIT,

        /**
         * 头部展示出来
         */
        STATE_VISIBLE,

        /**
         * 超出可刷新距离
         */
        STATE_OVER,

        /**
         * 刷新中
         */
        STATE_REFRESH,

        /**
         * 超出刷新距离后松手时
         */
        STATE_OVER_RELEASE
    }

    protected var mRefreshState = DiRefreshState.STATE_INIT

    var pullRefreshHeight = DiDisplayUtil.dp2px(66.0f)
    var minDamp = 1.6f
    var maxDamp = 2.2f

    abstract fun init()
    abstract fun onVisible()
    abstract fun onOver()
    abstract fun onRefresh()
    abstract fun onFinish()
    abstract fun onScroll(scrollY: Int, pullRefreshHeight: Int)

    fun setState(state: DiRefreshState) {
        this.mRefreshState = state
    }

    fun getState(): DiRefreshState {
        return mRefreshState
    }
}