package com.doing.diui.banner

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

private class DiDemoViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    ViewPager(context, attrs) {

    private var mIntervalTime = 1000L
    private var mIsAutoPlay = false
    private val mHandler = Handler(Looper.getMainLooper())
    private val mRunnable by lazy {
        object : Runnable {
            override fun run() {
                next()
                mHandler.postDelayed(this, mIntervalTime)
            }
        }
    }

    fun start() {
        mHandler.removeCallbacksAndMessages(null)

        if (mIsAutoPlay) {
            mHandler.postDelayed(mRunnable, mIntervalTime)
        }
    }

    fun stop() {
        mHandler.removeCallbacksAndMessages(null)
    }

    fun setAutoPlay(isAutoPlay: Boolean) {
        this.mIsAutoPlay = isAutoPlay

        if (!isAutoPlay) {
            stop()
        }
    }

    fun setIntervalTime(intervalTime: Long) {
        this.mIntervalTime = intervalTime
    }

    private fun next() {
        val adapter = this.adapter ?: return stop()

        if (adapter.count <= 1) {
            return stop()
        }

        val nextItem = currentItem + 1
        if (currentItem >= adapter.count) {
            // TODO
        }

        setCurrentItem(nextItem, true)
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> start()
            MotionEvent.ACTION_DOWN -> stop()
        }

        return super.onTouchEvent(ev)
    }
}