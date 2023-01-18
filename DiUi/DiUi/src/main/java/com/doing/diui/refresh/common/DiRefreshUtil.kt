package com.doing.diui.refresh.common

import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView


internal object DiRefreshUtil {

    fun findScrollChildView(view: View): View {
        if (view is RecyclerView || view is AdapterView<*>) {
            return view
        }

        if (view is ViewGroup) {
            val child = view.getChildAt(0)
            return findScrollChildView(child)
        }

        return view
    }

    fun childScrolled(child: View): Boolean {
        if (child is AdapterView<*>) {
            if (child.firstVisiblePosition != 0
                || child.firstVisiblePosition == 0 && child.getChildAt(0) != null && child.getChildAt(
                    0
                ).top < 0
            ) {
                return true
            }
        } else if (child.scrollY > 0) {
            return true
        }
        if (child is RecyclerView) {
            val view = child.getChildAt(0)
            val firstPosition = child.getChildAdapterPosition(view)
            return firstPosition != 0 || view.top != 0
        }
        return false
    }
}