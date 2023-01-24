package com.doing.diui.banner.core

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import com.doing.hilibrary.log.DiLog
import java.lang.reflect.Field

class DiViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
    : ViewPager(context, attrs) {

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
    private var mIsLayout = false

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
        } else {
            start()
        }
    }

    fun setIntervalTime(intervalTime: Long) {
        this.mIntervalTime = intervalTime
    }

    fun setScrollDuration(duration: Int) {
        try {
            val field = ViewPager::class.java
                .getDeclaredField("mScroller")
            field.isAccessible = true
            field.set(this, DiBannerScroller(context, duration))
        } catch (e: Exception) {
            DiLog.e("DiViewPager", e, "Di Scroller Set Failed")
        }
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
