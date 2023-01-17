package com.doing.diui.tab.top

import android.graphics.Bitmap
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment

class DiTabTopItemInfo {

    sealed class TabType {
        class Image(val defaultBitmap: Bitmap, val selectedBitmap: Bitmap) : TabType()
        class Text(val text: String, @ColorInt val defaultColor: Int, @ColorInt val tintColor: Int) : TabType()
    }

    val tabType: TabType
    val fragment: Class<out Fragment>

    constructor(fragment: Class<out Fragment>, defaultBitmap: Bitmap, selectedBitmap: Bitmap) {
        this.fragment = fragment
        this.tabType = TabType.Image(defaultBitmap, selectedBitmap)
    }

    constructor(fragment: Class<out Fragment>, name: String,
                @ColorInt defaultColor: Int, @ColorInt tintColor: Int) {
        this.fragment = fragment
        this.tabType = TabType.Text(name, defaultColor, tintColor)
    }
}