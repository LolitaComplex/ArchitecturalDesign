package com.doing.diui.tab.top

import android.content.Context
import android.graphics.Rect
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


class DiTabTopView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    HorizontalScrollView(context, attrs), IDiTabLayout<DiTabTopItemView, DiTabTopItemInfo> {

    companion object {
        const val BOTTOM_TAG = "RootContainer"
    }

    private var itemViews: MutableList<DiTabTopItemView> = mutableListOf()

    private val onTabSelectedListeners =
        mutableListOf<IDiTabLayout.OnTabSelectedListener<DiTabTopItemInfo>>()

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
            DiLog.w("DiDoing", "Data: $data 并不存在")
        }
    }

    override fun inflateInfo(dataList: List<DiTabTopItemInfo>) {
        if (dataList.isEmpty()) {
            return
        }

        clearView()

        val llContainer = LinearLayout(context)
        llContainer.setTag(R.id.DiTabBottomLayout_rl_root, BOTTOM_TAG)

        dataList.forEachIndexed { index, data ->
            val param = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
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
        var targetView: View? = null
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

        autoScroll(selectedIndex, data)
    }

    private var tabWith: Int = 0

    private fun autoScroll(index: Int, nextInfo: DiTabTopItemInfo) {
        val tabTop = findTab(nextInfo) ?: return
        val loc = IntArray(2)
        //获取点击的控件在屏幕的位置
        //获取点击的控件在屏幕的位置
        tabTop.getLocationInWindow(loc)
        val scrollWidth: Int
        if (tabWith == 0) {
            tabWith = tabTop.width
        }
        //判断点击了屏幕左侧还是右侧
        //判断点击了屏幕左侧还是右侧
        if (loc[0] + tabWith / 2 > DiDisplayUtil.getScreenWidthInPx(context) / 2) {
            scrollWidth = rangeScrollWidth(index, 2)
        } else {
            scrollWidth = rangeScrollWidth(index, -2)
        }
        scrollTo(scrollX + scrollWidth, 0)
    }

    /**
     * 获取可滚动的范围
     *
     * @param index 从第几个开始
     * @param range 向前向后的范围
     * @return 可滚动的范围
     */
    private fun rangeScrollWidth(index: Int, range: Int): Int {
        var scrollWidth = 0
        for (i in 0..Math.abs(range)) {
            val next = if (range < 0) {
                range + i + index
            } else {
                range - i + index
            }
            if (next >= 0 && next < itemViews.size) {
                if (range < 0) {
                    scrollWidth -= scrollWidth(next, false)
                } else {
                    scrollWidth += scrollWidth(next, true)
                }
            }
        }
        return scrollWidth
    }

    /**
     * 指定位置的控件可滚动的距离
     *
     * @param index   指定位置的控件
     * @param toRight 是否是点击了屏幕右侧
     * @return 可滚动的距离
     */
    private fun scrollWidth(index: Int, toRight: Boolean): Int {
        val target = findTab(itemViews.find { item -> item.index == index }!!.tabInfo) ?: return 0
        val rect = Rect()
        target.getLocalVisibleRect(rect)
        return if (toRight) { //点击屏幕右侧
            if (rect.right > tabWith) { //right坐标大于控件的宽度时，说明完全没有显示
                tabWith
            } else { //显示部分，减去已显示的宽度
                tabWith - rect.right
            }
        } else {
            if (rect.left <= -tabWith) { //left坐标小于等于-控件的宽度，说明完全没有显示
                return tabWith
            } else if (rect.left > 0) { //显示部分
                return rect.left
            }
            0
        }
    }
}