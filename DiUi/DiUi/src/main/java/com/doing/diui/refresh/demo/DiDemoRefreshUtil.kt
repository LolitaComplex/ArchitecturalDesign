package com.doing.diui.refresh.demo

import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.view.ScrollingView
import androidx.recyclerview.widget.RecyclerView

object DiDemoRefreshUtil {

    fun getScrollView(view: View?): View? {
        if (view is ScrollingView || view is AdapterView<*>) {
            return view
        }

        if (view !is ViewGroup) {
            return null
        }

        for (index in 0 until view.childCount) {
            val child = getScrollView(view.getChildAt(index))
            if (child != null) {
                return child
            }
        }

        return null
    }

    fun isScrollViewTop(view: View): Boolean {
        if (view is RecyclerView) {
            val child = view.getChildAt(0)
            val position = view.getChildAdapterPosition(child)
            return position == 0
        }

        return false
    }
}