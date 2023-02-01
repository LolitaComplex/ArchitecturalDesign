package com.doing.diproject.home.tab

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.fragment.app.Fragment

class DiTabFragmentView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
    : FrameLayout(context, attrs) {

    private lateinit var mAdapter: DiTabFragmentAdapter
    private var mCurrentPosition= -1

    fun setAdapter(adapter: DiTabFragmentAdapter) {
        if (this::mAdapter.isInitialized) {
            throw IllegalAccessException("don't call setAdapter repeat")
        }

        this.mAdapter = adapter
    }

    fun setCurrentItem(position: Int) {
        val adapter = mAdapter
        val currentPosition = mCurrentPosition
        if (position < 0 || position >= adapter.getCount()) {
            return
        }

        if (currentPosition != position) {
            adapter.instantiateItem(this, position)
        }

        mCurrentPosition = position
    }

    fun getCurrentPosition(): Int {
        return mCurrentPosition
    }

    fun getCurrentFragment(): Fragment? {
        if (!this::mAdapter.isInitialized) {
            throw IllegalAccessException("please call setAdapter first.")
        }

        return mAdapter.getCurrentFragment()
    }
}