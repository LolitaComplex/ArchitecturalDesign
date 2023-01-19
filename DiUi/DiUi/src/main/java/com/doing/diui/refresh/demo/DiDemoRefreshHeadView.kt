package com.doing.diui.refresh.demo

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.doing.diui.refresh.demo.IDiDemoRefreshHead.DiRefreshState

abstract class DiDemoRefreshHeadView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
    : FrameLayout(context, attrs), IDiDemoRefreshHead {

    private var mState = DiRefreshState.STATE_INIT

    override fun setState(state: DiRefreshState) {
        this.mState = state
    }

    override fun getState(): DiRefreshState {
        return mState
    }

    override fun getMaxDamp(): Float {
        return 2.2f
    }

    override fun getMinDamp(): Float {
        return 1.6f
    }
}