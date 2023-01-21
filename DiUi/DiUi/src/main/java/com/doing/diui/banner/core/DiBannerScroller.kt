package com.doing.diui.banner.core

import android.content.Context
import android.widget.Scroller

class DiBannerScroller(context: Context, private val time: Int)
    : Scroller(context) {

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        super.startScroll(startX, startY, dx, dy, this.time)
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
        super.startScroll(startX, startY, dx, dy, this.time)
    }
}