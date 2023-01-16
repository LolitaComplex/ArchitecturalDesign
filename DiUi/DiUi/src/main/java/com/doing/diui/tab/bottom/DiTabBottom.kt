package com.doing.diui.tab.bottom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.doing.diui.R
import com.doing.diui.tab.common.IDiTab
import com.doing.diui.tab.common.IDiTabLayout

class DiTabBottom @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs), IDiTab<DiTabBottomInfo>,
    IDiTabLayout.OnTabSelectedListener<DiTabBottomInfo> {

    private val ivTab: ImageView
    private val tvIcon: TextView
    private val tvName: TextView

    lateinit var tabInfo: DiTabBottomInfo
        private set
    private var index: Int = -1
    private var isTabSelected = false

    init {
        LayoutInflater.from(getContext()).inflate(R.layout.di_bottom_layout, this)
        ivTab = findViewById(R.id.DiTabBottomLayout_iv_image)
        tvIcon = findViewById(R.id.DiTabBottomLayout_tv_icon)
        tvName = findViewById(R.id.DiTabBottomLayout_tv_name)
    }

    override fun setTabInfo(index: Int, data: DiTabBottomInfo) {
        this.tabInfo = data
        this.index = index

        inflateView(data, false)
    }

    private fun inflateView(data: DiTabBottomInfo, isSelected: Boolean) {
        when (val type = data.tabType) {
            is DiTabBottomInfo.TabType.BITMAP -> {
                ivTab.visibility = View.VISIBLE
                tvIcon.visibility = View.GONE
                val bitmap = if (isSelected) type.selectedBitmap else type.defaultBitmap
                ivTab.setImageBitmap(bitmap)
            }
            is DiTabBottomInfo.TabType.ICON -> {
                ivTab.visibility = View.GONE
                tvIcon.visibility = View.VISIBLE
                val typeface = Typeface.createFromAsset(context.assets, type.iconFont)
                tvIcon.typeface = typeface


                val iconName = if (isSelected) type.selectedIconName else type.defaultIconName
                tvIcon.text = iconName

                tvIcon.setTextColor(if (isSelected) type.tintColor else type.defaultColor)
                tvName.setTextColor(if (isSelected) type.tintColor else type.defaultColor)
            }
        }

        tvName.text = data.name
    }

    override fun onTabSelectedChange(index: Int, currentInfo: DiTabBottomInfo) {
        val isSelected = index == this.index
        if (isSelected != this.isTabSelected) {
            inflateView(tabInfo, isSelected)
            this.isTabSelected = isSelected
        }
    }

    override fun resetTab(height: Int) {
        val params = this.layoutParams
        params.height = height
        this.layoutParams = params
        tvName.visibility = View.GONE
    }

}