package com.doing.diui.refresh.demo

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.Scroller
import com.doing.diui.refresh.demo.IDiDemoRefresh.OnDiDemoRefreshListener
import com.doing.diui.refresh.demo.IDiDemoRefreshHead.DiRefreshState
import com.doing.hilibrary.log.DiLog
import kotlin.math.abs

class DiDemoRefreshView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    FrameLayout(context, attrs), IDiDemoRefresh {

    private var mHeadView: DiDemoRefreshHeadView? = null
    private var mRefreshListener: OnDiDemoRefreshListener? = null
    private val mGestureDetector by lazy { GestureDetector(context, DiDemoGestureDetector()) }
    private val mAutoScroller by lazy { DiDemoAutoScroller() }
    private var mLastY = 0;

    override fun finishRefresh() {
        val head = mHeadView ?: return

        recover(head.bottom)
        head.setState(DiRefreshState.STATE_INIT)
    }

    override fun setHeadView(head: DiDemoRefreshHeadView) {
        this.mHeadView = head

        head.init()
        addView(head, 0, LayoutParams(LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT))
    }

    override fun setOnRefreshListener(listener: OnDiDemoRefreshListener) {
        this.mRefreshListener = listener
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val head = mHeadView ?: return super.dispatchTouchEvent(ev)
        val state = head.getState()

        val child = DiDemoRefreshUtil.getScrollView(getChildAt(1))
            ?: return super.dispatchTouchEvent(ev)

        if (!DiDemoRefreshUtil.isScrollViewTop(child)) {
            return super.dispatchTouchEvent(ev)
        }

        if (state != DiRefreshState.STATE_REFRESH && head.bottom != 0 && (ev.action == MotionEvent.ACTION_UP
                    || ev.action == MotionEvent.ACTION_CANCEL || ev.action == MotionEvent.ACTION_POINTER_INDEX_MASK)) {
            recover(head.bottom)
            return true
        }



        val gesture = mGestureDetector.onTouchEvent(ev)
        DiLog.it("Doing", "dispatchEvent: $gesture")

        if (gesture) {
            ev.action = MotionEvent.ACTION_CANCEL
        }


        return super.dispatchTouchEvent(ev)
    }

    private fun recover(distance: Int) {
        val headView = mHeadView ?: return
        val scroller = mAutoScroller

        if (distance > headView.height) {
            scroller.recover(distance - headView.height)
            headView.setState(DiRefreshState.STATE_OVER_RELEASE)
        } else {
            scroller.recover(distance)
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val head = mHeadView?: return super.onLayout(changed, left, top, right, bottom)
        val child = getChildAt(1) ?: return super.onLayout(changed, left, top, right, bottom)

        if (head.getState() == DiRefreshState.STATE_REFRESH) {
            head.layout(left, top, right, top + head.measuredHeight)
            child.layout(left, top + head.measuredHeight, right,
                top + head.measuredHeight + child.measuredHeight)
        } else {
            head.layout(left, top - head.measuredHeight, right, top)
            child.layout(left, top, right, top + child.measuredHeight)
        }

        for (index in 2 until childCount) {
            val view = getChildAt(index)
            view.layout(left, top, right, bottom)
        }
    }

    private inner class DiDemoGestureDetector : GestureDetector.OnGestureListener {
        override fun onDown(e: MotionEvent?): Boolean {
            return false
        }

        override fun onShowPress(e: MotionEvent?) {
        }

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            return false
        }

        override fun onScroll(e1: MotionEvent?, e2: MotionEvent?,
            distanceX: Float, distanceY: Float): Boolean {

            val head = mHeadView ?: return false
            val child = getChildAt(1) ?: return false
            val isEnableRefresh = mRefreshListener?.enableRefresh() ?: true
            val state = head.getState()
            val lastY = mLastY

            DiLog.vt("Doing", "DistanceX: $distanceX \t DistanceY: $distanceY")

            if (abs(distanceX) > abs(distanceY) || !isEnableRefresh) {
                return false
            }

            if (state == DiRefreshState.STATE_REFRESH) {
                return true
            }

            DiLog.wt("Doing", "---DistanceX: $distanceX \t DistanceY: $distanceY")

            if (distanceY <= 0  && state != DiRefreshState.STATE_OVER_RELEASE){
                val distance = if (head.bottom > head.height) {
                    distanceY / head.getMinDamp()
                } else {
                    distanceY / head.getMaxDamp()
                }
                mLastY = -distance.toInt()
                return moveDown(lastY)
            }


            return false
        }

        override fun onLongPress(e: MotionEvent?) {
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            return false
        }
    }

    private fun moveDown(distance: Int): Boolean {
        val head = mHeadView?: return false
        val state = head.getState()
        val child = getChildAt(1)
        val childTop = child.top

        if (childTop < 0) {
            val resetY = -childTop
            head.offsetTopAndBottom(resetY)
            child.offsetTopAndBottom(resetY)
            if (state != DiRefreshState.STATE_REFRESH) {
                head.setState(DiRefreshState.STATE_INIT)
            }
        } else if (state == DiRefreshState.STATE_REFRESH) {
            return false
        } else if (childTop < head.height) {
            if (state != DiRefreshState.STATE_VISIBLE) {
                head.setState(DiRefreshState.STATE_VISIBLE)
                head.onVisible()
            }
            head.offsetTopAndBottom(distance)
            child.offsetTopAndBottom(distance)
        } else if (childTop == head.height) {
            head.offsetTopAndBottom(distance)
            child.offsetTopAndBottom(distance)
            if (state == DiRefreshState.STATE_OVER_RELEASE) {
                refresh()
            }
        } else {
            if (state != DiRefreshState.STATE_OVER) {
                head.setState(DiRefreshState.STATE_OVER)
                head.onOver()
            }
            head.offsetTopAndBottom(distance)
            child.offsetTopAndBottom(distance)
        }
        head.onScroll(distance)

        return true
    }

    private fun refresh() {
        mRefreshListener?.onRefresh(this)

        val head = mHeadView ?: return
        head.setState(DiRefreshState.STATE_REFRESH)
        head.onRefresh()
    }

    private inner class DiDemoAutoScroller : Runnable {

        private val scroller: Scroller = Scroller(context, LinearInterpolator())
        private var lastY = 0

        fun recover(distance: Int) {
            if (distance <= 0) {
                return
            }

            removeCallbacks(this)
            lastY = 0
            scroller.startScroll(0, 0, 0, distance, 300)
            post(this)
        }

        override fun run() {
            val isScrolling = scroller.computeScrollOffset()
            if (isScrolling) {
                moveDown(lastY - scroller.currY)
                lastY = scroller.currY
                post(this)
            } else {
                removeCallbacks(this)
            }
        }
    }
}