package com.doing.diui.tab.top

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import com.doing.diui.R
import com.doing.diui.tab.common.IDiTabLayout
import com.doing.hilibrary.log.DiLog
import com.doing.hilibrary.util.DiDisplayUtil

class DiTabTopView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
    : HorizontalScrollView(context, attrs), IDiTabLayout<DiTabTopItemView, DiTabTopItemInfo> {

    companion object {
        const val BOTTOM_TAG = "RootContainer"
    }

    private var itemViews: MutableList<DiTabTopItemView> = mutableListOf()

    private val onTabSelectedListeners = mutableListOf<IDiTabLayout.OnTabSelectedListener<DiTabTopItemInfo>>()

    override fun findTab(data: DiTabTopItemInfo): DiTabTopItemView? {
        itemViews.forEach { view ->
            if (view.tabInfo == data) {
                return view
            }
        }

        return null
    }

    override fun addOnTabSelectedListener(listener: IDiTabLayout.OnTabSelectedListener<DiTabTopItemInfo>) {
        onTabSelectedListeners.add(listener)
    }

    override fun select(data: DiTabTopItemInfo) {
        val selectedIndex = itemViews.indexOfFirst { view ->
            view.tabInfo == data
        }

        if (selectedIndex != -1) {
            onSelected(selectedIndex, data)
        } else {
            DiLog.wt("DiDoing", "Data: $data 并不存在")
        }
    }

    override fun inflateInfo(dataList: List<DiTabTopItemInfo>) {
        if (dataList.isEmpty()) {
            return
        }

        clearView()

        val llContainer = LinearLayout(context)
        llContainer.setTag(R.id.DiTabBottomLayout_rl_root, BOTTOM_TAG)

        dataList.forEachIndexed { index, data  ->
            val param = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            val itemView = DiTabTopItemView(context)
            param.gravity = Gravity.CENTER
            itemView.setTabInfo(index, data)
            llContainer.addView(itemView, param)
            itemView.setOnClickListener {
                onSelected(index, data)
            }
            itemViews.add(itemView)
        }

        val param = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        param.gravity = Gravity.CENTER_VERTICAL
        addView(llContainer, param)
    }

    private fun clearView() {
        var targetView : View? = null
        for (i in 0 until this.childCount) {
            val view = getChildAt(i)
            val tag = view.getTag(R.id.DiTabBottomLayout_rl_root)
            if (tag is String && tag == BOTTOM_TAG) {
                targetView = view
            }
        }

        if (targetView != null) {
            removeView(targetView)
        }

        itemViews.clear()
    }

    private fun onSelected(selectedIndex: Int, data: DiTabTopItemInfo) {
        itemViews.forEach { view ->
            view.onTabSelectedChange(selectedIndex, data)
        }

        onTabSelectedListeners.forEach { listener ->
            listener.onTabSelectedChange(selectedIndex, data)
        }
    }
}