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
import kotlin.math.abs

class DiRefreshView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
    : FrameLayout(context, attrs), IDiRefresh {

    private var mRefreshListener: IDiRefresh.OnRefreshListener? = null
    private var mOverView: DiOverView? = null
    private var mIsDisableRefreshScroll = false
    private var mState: DiRefreshState = DiRefreshState.STATE_INIT
    private var mLastY = 0.0f

    private val mGestureDetector by lazy { GestureDetector(context, RefreshGestureDetector()) }
    private val mAutoScroller by lazy { AutoScroller() }

    override fun refreshFinished() {
        val overView = mOverView ?: return
        val head = getChildAt(0)

        overView.onFinish()
        overView.setState(DiRefreshState.STATE_INIT)
        if (head.bottom > 0) {
            recover(head.bottom)
        }
        mState = DiRefreshState.STATE_INIT
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
        val head = this.getChildAt(0)
        if (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_CANCEL
            || ev.action == MotionEvent.ACTION_POINTER_INDEX_MASK) {
            if (head.bottom > 0 && mState != DiRefreshState.STATE_REFRESH) {
                recover(head.bottom)
                return false
            }
        }

        val consumed = mGestureDetector.onTouchEvent(ev)
        val state = mState
        if (consumed || (state != DiRefreshState.STATE_INIT && state != DiRefreshState.STATE_REFRESH)
            && head.bottom != 0) {
            ev.action = MotionEvent.ACTION_CANCEL
            return super.dispatchTouchEvent(ev)
        }

        return if (consumed) {
            true
        } else {
            super.dispatchTouchEvent(ev)
        }
    }

    private fun recover(distance: Int) {
        val overView = mOverView ?: return
        val autoScroller = mAutoScroller

        if (distance >= overView.pullRefreshHeight) {
            autoScroller.recover(distance - overView.pullRefreshHeight)
            mState = DiRefreshState.STATE_OVER_RELEASE
        } else {
            autoScroller.recover(distance)
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val overView = mOverView ?: return super.onLayout(changed, left, top, right, bottom)

        val head = getChildAt(0)
        val child = getChildAt(1)

        if (head != null && child != null) {
            val childTop = child.top
            if (mState == DiRefreshState.STATE_REFRESH) {
                head.layout(left, overView.pullRefreshHeight - head.measuredHeight,
                    right, overView.pullRefreshHeight)
                child.layout(left, overView.pullRefreshHeight, right, overView.pullRefreshHeight + child.measuredHeight)
            } else {
                head.layout(left, childTop - head.measuredHeight, right, childTop)
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
            val isDisableRefreshScroll = mIsDisableRefreshScroll
            val listener = mRefreshListener ?: return false
            val state = mState
            val overView = mOverView ?: return false
            val lastY = mLastY


            if (abs(distanceX) > abs(distanceY) || !listener.enableRefresh()) {
                return false
            }

            if (isDisableRefreshScroll && state == DiRefreshState.STATE_REFRESH) {
                return true
            }

            val head = getChildAt(0)
            val child = DiRefreshUtil.findScrollChildView(getChildAt(1))
            if (DiRefreshUtil.childScrolled(child)) {
                return false
            }

            if (state != DiRefreshState.STATE_REFRESH || head.bottom <= overView.pullRefreshHeight
                && (head.bottom > 0 || distanceY <= 0.0f)) {
                if (state != DiRefreshState.STATE_OVER_RELEASE) {
                    val speed = if (child.top < overView.pullRefreshHeight) {
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
        val head = getChildAt(0)
        val child = getChildAt(1)
        val childTop = child.top + offsetY
        val overView = mOverView ?: return false

        if (childTop <= 0) {
            val resetY = -child.top
            head.offsetTopAndBottom(resetY)
            child.offsetTopAndBottom(resetY)

            if (mState != DiRefreshState.STATE_REFRESH) {
                mState = DiRefreshState.STATE_INIT
            }
        } else if (mState == DiRefreshState.STATE_REFRESH && childTop > overView.pullRefreshHeight) {
            return false
        } else if (childTop <= overView.pullRefreshHeight) {
            if (overView.getState() != DiRefreshState.STATE_VISIBLE) {
                overView.onVisible()
                overView.setState(DiRefreshState.STATE_VISIBLE)
                mState = DiRefreshState.STATE_VISIBLE
            }
            head.offsetTopAndBottom(offsetY)
            child.offsetTopAndBottom(offsetY)
            if (childTop == overView.pullRefreshHeight && mState == DiRefreshState.STATE_OVER_RELEASE) {
                refresh()
            }
        } else {
            if (overView.getState() != DiRefreshState.STATE_OVER && isNotAuto) {
                overView.onOver()
                overView.setState(DiRefreshState.STATE_OVER)
            }
            head.offsetTopAndBottom(offsetY)
            child.offsetTopAndBottom(offsetY)
        }

        overView.onScroll(head.bottom, overView.pullRefreshHeight)

        return true
    }

    private fun refresh() {
        val refreshListener = mRefreshListener ?: return
        val overView = mOverView ?: return

        mState = DiRefreshState.STATE_REFRESH
        overView.onRefresh()
        overView.setState(DiRefreshState.STATE_REFRESH)
        refreshListener.onRefresh()
    }
}