package com.doing.diui.tab.top

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.doing.diui.R
import com.doing.diui.tab.common.IDiTab
import com.doing.diui.tab.common.IDiTabLayout

class DiTabTopItemView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)
    : FrameLayout(context, attrs), IDiTab<DiTabTopItemInfo>,
    IDiTabLayout.OnTabSelectedListener<DiTabTopItemInfo> {

    private val ivImage: ImageView
    private val tvText: TextView
    private val vIndicator: View

    var index = -1
        private set
    lateinit var tabInfo: DiTabTopItemInfo
        private set
    private var isTabSelected: Boolean = false

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_di_top_item,
            this, true)
        ivImage = findViewById(R.id.DiTabTopItemView_iv_image)
        tvText = findViewById(R.id.DiTabTopItemView_tv_name)
        vIndicator = findViewById(R.id.DiTabTopItemView_indicator)
    }

    override fun setTabInfo(index: Int, data: DiTabTopItemInfo) {
        this.index = index
        this.tabInfo = data

        inflateView(data, false)
    }

    private fun inflateView(data: DiTabTopItemInfo, isSelected: Boolean) {
        when (val type = data.tabType) {
            is DiTabTopItemInfo.TabType.Image -> {
                tvText.visibility = View.GONE
                ivImage.visibility = View.VISIBLE
                ivImage.setImageBitmap(
                    if (isSelected) type.selectedBitmap else type.defaultBitmap)
            }
            is DiTabTopItemInfo.TabType.Text -> {
                tvText.visibility = View.VISIBLE
                ivImage.visibility = View.GONE

                tvText.text = type.text
                tvText.setTextColor(if (isSelected) type.tintColor else type.defaultColor)
            }
        }

        vIndicator.visibility = if (isSelected) View.VISIBLE else View.INVISIBLE
    }

    override fun resetTab(height: Int) {
    }

    override fun onTabSelectedChange(index: Int, currentData: DiTabTopItemInfo) {
        val isSelected = index == this.index
        if (isSelected != this.isTabSelected) {
            inflateView(tabInfo, isSelected)
            this.isTabSelected = isSelected
        }
    }
}