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

    constructor(defaultBitmap: Bitmap, selectedBitmap: Bitmap) {
        this.tabType = TabType.Image(defaultBitmap, selectedBitmap)
    }

    constructor(name: String, @ColorInt defaultColor: Int, @ColorInt tintColor: Int) {
        this.tabType = TabType.Text(name, defaultColor, tintColor)
    }
}