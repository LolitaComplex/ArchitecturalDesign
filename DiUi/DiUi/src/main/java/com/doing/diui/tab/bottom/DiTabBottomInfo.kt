package com.doing.diui.tab.bottom

import android.graphics.Bitmap
import android.graphics.Color
import android.text.TextUtils
import androidx.annotation.ColorInt

class DiTabBottomInfo {

    sealed class TabType {

        class BITMAP(val defaultBitmap: Bitmap, val selectedBitmap: Bitmap): TabType()
        class ICON(val iconFont: String, val defaultIconName: String, val selectedIconName: String,
                   @ColorInt val defaultColor: Int, @ColorInt val tintColor: Int): TabType()
    }

    val name : String
    val tabType: TabType

    constructor(name: String, defaultBitmap: Bitmap, selectedBitmap: Bitmap) {
        this.name = name
        this.tabType = TabType.BITMAP(defaultBitmap, selectedBitmap)
    }

    constructor(name: String, iconFont: String, defaultIconName: String, selectedIconName: String?,
                @ColorInt defaultColor: Int, @ColorInt tintColor: Int) {
        this.name = name
        val selectedIconNameNew = if (TextUtils.isEmpty(selectedIconName)) {
            defaultIconName
        } else {
            selectedIconName!!
        }
        this.tabType = TabType.ICON(iconFont, defaultIconName,
            selectedIconNameNew, defaultColor, tintColor)
    }


}