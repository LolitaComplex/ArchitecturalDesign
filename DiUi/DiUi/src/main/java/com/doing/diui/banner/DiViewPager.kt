package com.doing.diui.banner

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import java.lang.reflect.Field

class DiViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
    : ViewPager(context, attrs) {

    private val mHandler = Handler(Looper.getMainLooper())
    private var mIntervalTime : Long = 2000L
    private val mRunnable = object : Runnable {
        override fun run() {
            next()
            mHandler.postDelayed(this, mIntervalTime)
        }
    }
    private var mIsAutoPlay = false
    private var mIsLayout = false

    fun start() {
        val handler = mHandler
        val isAutoPlay = mIsAutoPlay
        val runnable = mRunnable
        val intervalTime = mIntervalTime

        handler.removeCallbacksAndMessages(null)
        if (isAutoPlay) {
            handler.postDelayed(runnable, intervalTime)
        }
    }

    fun stop() {
        mHandler.removeCallbacksAndMessages(null)
    }

    fun setIntervalTime(time: Long) {
        this.mIntervalTime = time
    }

    fun setAutoPlay(isAutoPlay: Boolean) {
        this.mIsAutoPlay = isAutoPlay
        if (!isAutoPlay) {
            stop()
        }
    }

    private fun next(): Int {
        val adapter = this.adapter ?: return stopError()
        if (adapter.count <= 1) {
            return stopError()
        }

        var nextItem = currentItem
        if (++nextItem >= adapter.count) {
//            nextItem = adapter.
        }

        setCurrentItem(nextItem, true)
        return nextItem
    }

    private fun stopError(): Int {
        stop()
        return currentItem
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        val event = ev ?: return super.onTouchEvent(ev)
        when (ev.action) {
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                start()
            }
            MotionEvent.ACTION_DOWN -> stop()
        }

        return super.onTouchEvent(ev)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        mIsLayout = true
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (mIsLayout && adapter != null && adapter!!.count > 0) {
            try {
                //fix 使用RecyclerView + ViewPager bug https://blog.csdn.net/u011002668/article/details/72884893
                val mScroller: Field = ViewPager::class.java.getDeclaredField("mFirstLayout")
                mScroller.isAccessible = true
                mScroller.set(this, false)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        start()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (context is Activity && (context as Activity).isFinishing) {
            super.onDetachedFromWindow()
        }
        stop()
    }

}
