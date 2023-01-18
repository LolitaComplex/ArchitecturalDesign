package com.doing.diui.refresh.view

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.Scroller
import com.doing.diui.refresh.common.DiGestureDetector
import com.doing.diui.refresh.common.DiRefreshUtil
import com.doing.diui.refresh.common.IDiRefresh
import com.doing.diui.refresh.view.DiOverView.DiRefreshState
import com.doing.hilibrary.log.DiLog
import kotlin.math.abs

class DiRefreshView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
    : FrameLayout(context, attrs), IDiRefresh {

    private var mRefreshListener: IDiRefresh.OnRefreshListener? = null
    private var mOverView: DiOverView? = null
    private var mIsDisableRefreshScroll = false
    private var mLastY = 0.0f

    private val mGestureDetector by lazy { GestureDetector(context, RefreshGestureDetector()) }
    private val mAutoScroller by lazy { AutoScroller() }

    override fun refreshFinished() {
        val overView = mOverView ?: return

        overView.onFinish()
        if (overView.bottom > 0) {
            recover(overView.bottom)
        }
        overView.setState(DiRefreshState.STATE_INIT)
    }

    override fun setDisableRefreshScroll(isScroll: Boolean) {
        this.mIsDisableRefreshScroll = isScroll
    }

    override fun setOnRefreshListener(listener: IDiRefresh.OnRefreshListener) {
        this.mRefreshListener = listener
    }

    override fun setRefreshOverView(overView: DiOverView) {
        this.mOverView = overView

        val params = ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT)
        addView(overView, 0, params)
        overView.init()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val overView =  mOverView ?: return super.dispatchTouchEvent(ev)

        val state = overView.getState()
        if (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_CANCEL
            || ev.action == MotionEvent.ACTION_POINTER_INDEX_MASK) {
            if (overView.bottom > 0 && state != DiRefreshState.STATE_REFRESH) {
                recover(overView.bottom)
                return false
            }
        }

        val consumed = mGestureDetector.onTouchEvent(ev)
        DiLog.vt("Doing", "dispatchTouchEvent: 1 \t: $consumed")
        if (consumed || (state != DiRefreshState.STATE_INIT && state != DiRefreshState.STATE_REFRESH)
            && overView.bottom != 0) {
            ev.action = MotionEvent.ACTION_CANCEL
            return super.dispatchTouchEvent(ev)
        }
        DiLog.vt("Doing", "dispatchTouchEvent: 2 \t: $consumed")

        return if (consumed) {
            true
        } else {
            super.dispatchTouchEvent(ev)
        }
    }

    private fun recover(distance: Int) {
        val overView = mOverView ?: return
        val autoScroller = mAutoScroller

        if (distance > overView.height) {
            autoScroller.recover(distance - overView.height)
            overView.setState(DiRefreshState.STATE_OVER_RELEASE)
        } else {
            autoScroller.recover(distance)
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val overView = mOverView ?: return super.onLayout(changed, left, top, right, bottom)

        val child = getChildAt(1)

        if (child != null) {
            DiLog.it("Doing", "Left: $left \t Top: $top \t Right:" +
                    " $right \t Bottom: $bottom")

            if (overView.getState() == DiRefreshState.STATE_REFRESH) {
                overView.layout(left, top, right, top + overView.measuredHeight)
                child.layout(left, top + overView.measuredHeight, right,
                    top + overView.measuredHeight + child.measuredHeight)
            } else {
                val childTop = child.top
                overView.layout(left, childTop - overView.measuredHeight, right, childTop)
                child.layout(left, childTop, right, childTop + child.measuredHeight)
            }

            for (i in 2 until childCount) {
                val other = getChildAt(i)
                other.layout(left, top, right, bottom)
            }
        }
    }


    private inner class AutoScroller : Runnable {

        private val mScroller by lazy { Scroller(context, LinearInterpolator()) }
        private var mLastY = 0
        private var mIsFinished = true

        override fun run() {
            val scroller = mScroller

            if (scroller.computeScrollOffset()) { // 滚动中
                moveDown(mLastY - mScroller.currY, false)
                mLastY = mScroller.currY
                post(this)
            } else {
                removeCallbacks(this)
                mIsFinished = true
            }
        }

        fun recover(distance: Int) {
            if (distance <= 0) {
                return
            }

            removeCallbacks(this)
            mLastY = 0
            mIsFinished = false
            mScroller.startScroll(0, 0, 0, distance, 300)
            post(this)
        }

        fun isFinish(): Boolean {
            return mIsFinished
        }
    }

    private inner class RefreshGestureDetector : DiGestureDetector() {
        override fun onScroll(e1: MotionEvent, e2: MotionEvent,
                              distanceX: Float, distanceY: Float): Boolean {
            DiLog.dt("Doing", "DistanceX: $distanceX \t DistanceY: $distanceY")

            val isDisableRefreshScroll = mIsDisableRefreshScroll
            val listener = mRefreshListener ?: return false
            val overView = mOverView ?: return false
            val state = overView.getState()
            val lastY = mLastY


            if (abs(distanceX) > abs(distanceY) || !listener.enableRefresh()) {
                return false
            }

            if (isDisableRefreshScroll && state == DiRefreshState.STATE_REFRESH) {
                return true
            }

            val child = DiRefreshUtil.findScrollChildView(getChildAt(1))
            if (DiRefreshUtil.childScrolled(child)) {
                return false
            }

            DiLog.wt("Doing", "DistanceX: $distanceX \t DistanceY: $distanceY" +
                    " \t OverViewBottom: ${overView.bottom} \t OverViewHeight: ${overView.height}" +
                    "")

            if (distanceY <= 0.0f && state != DiRefreshState.STATE_REFRESH ||
                (overView.bottom < overView.height && overView.bottom > 0)) {
                if (state != DiRefreshState.STATE_OVER_RELEASE) {
                    val speed = if (child.top < overView.height) {
                        lastY / overView.maxDamp
                    } else {
                        lastY / overView.minDamp
                    }
                    val isMove = moveDown(speed.toInt(), true)
                    mLastY = -distanceY
                    return isMove
                }
            }

            return false
        }
    }

    private fun moveDown(offsetY: Int, isNotAuto: Boolean): Boolean {
        val child = getChildAt(1)
        val childTop = child.top + offsetY
        val overView = mOverView ?: return false
        val state = overView.getState()

        if (childTop <= 0) {
            val resetY = -child.top
            overView.offsetTopAndBottom(resetY)
            child.offsetTopAndBottom(resetY)

            if (state != DiRefreshState.STATE_REFRESH) {
                overView.setState(DiRefreshState.STATE_INIT)
            }
        } else if (state == DiRefreshState.STATE_REFRESH && childTop > overView.height) {
            return false
        } else if (childTop <= overView.height) {
            if (state != DiRefreshState.STATE_VISIBLE && isNotAuto) {
                overView.onVisible()
                overView.setState(DiRefreshState.STATE_VISIBLE)
            }
            overView.offsetTopAndBottom(offsetY)
            child.offsetTopAndBottom(offsetY)
            if (childTop == overView.height && state == DiRefreshState.STATE_OVER_RELEASE) {
                refresh()
            }
        } else {
            if (overView.getState() != DiRefreshState.STATE_OVER && isNotAuto) {
                overView.onOver()
                overView.setState(DiRefreshState.STATE_OVER)
            }
            overView.offsetTopAndBottom(offsetY)
            child.offsetTopAndBottom(offsetY)
        }

        overView.onScroll(overView.bottom, overView.height)

        return true
    }

    private fun refresh() {
        val refreshListener = mRefreshListener ?: return
        val overView = mOverView ?: return

        overView.onRefresh()
        overView.setState(DiRefreshState.STATE_REFRESH)
        refreshListener.onRefresh()
    }
}