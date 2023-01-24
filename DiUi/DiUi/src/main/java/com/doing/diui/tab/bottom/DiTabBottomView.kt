package com.doing.diui.tab.bottom

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.doing.diui.R
import com.doing.diui.tab.common.IDiTabLayout
import com.doing.hilibrary.log.DiLog
import com.doing.hilibrary.util.DiDisplayUtil

class DiTabBottomView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
    : FrameLayout(context, attrs), IDiTabLayout<DiTabBottomItemView, DiTabBottomInfo> {

    companion object {
        const val BOTTOM_TAG = "RootContainer"
    }

    private var itemViews: MutableList<DiTabBottomItemView> = mutableListOf()

    private val onTabSelectedListeners = mutableListOf<IDiTabLayout.OnTabSelectedListener<DiTabBottomInfo>>()

    override fun findTab(data: DiTabBottomInfo): DiTabBottomItemView? {
        itemViews.forEach { view ->
            if (view.tabInfo == data) {
                return view
            }
        }

        return null
    }

    override fun addOnTabSelectedListener(listener: IDiTabLayout.OnTabSelectedListener<DiTabBottomInfo>) {
        onTabSelectedListeners.add(listener)
    }

    override fun select(data: DiTabBottomInfo) {
        val selectedIndex = itemViews.indexOfFirst { view ->
            view.tabInfo == data
        }

        if (selectedIndex != -1) {
            onSelected(selectedIndex, data)
        } else {
            DiLog.w("DiDoing", "Data: $data 并不存在")
        }
    }

    override fun inflateInfo(dataList: List<DiTabBottomInfo>) {
        if (dataList.isEmpty()) {
            return
        }

        clearView()

        val topLine = View(context)
        topLine.setBackgroundColor(Color.parseColor("#CCCCCC"))
        topLine.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, 3).apply {
            this.gravity = Gravity.TOP
            this.topMargin = 50
        }

        addView(topLine)

        val llContainer = LinearLayout(context)
        llContainer.setTag(R.id.DiTabBottomLayout_rl_root, BOTTOM_TAG)

        val width: Int = DiDisplayUtil.getScreenWidthInPx(context) / dataList.size
        dataList.forEachIndexed { index, data  ->
            val param = LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT)
            val itemView = DiTabBottomItemView(context)
            param.gravity = Gravity.BOTTOM
            itemView.setTabInfo(index, data )
            llContainer.addView(itemView, param)
            itemView.setOnClickListener {
                onSelected(index, data)
            }
            itemViews.add(itemView)
        }

        val param = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        param.gravity = Gravity.BOTTOM
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

    private fun onSelected(selectedIndex: Int, data: DiTabBottomInfo) {
        itemViews.forEach { view ->
            view.onTabSelectedChange(selectedIndex, data)
        }

        onTabSelectedListeners.forEach { listener ->
            listener.onTabSelectedChange(selectedIndex, data)
        }
    }
}