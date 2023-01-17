package com.doing.diui.tab.bottom

import android.graphics.Bitmap
import android.text.TextUtils
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment

class DiTabBottomInfo {

    sealed class TabType {

        class Image(val defaultBitmap: Bitmap, val selectedBitmap: Bitmap): TabType()
        class TextIcon(val iconFont: String, val defaultIconName: String, val selectedIconName: String,
                       @ColorInt val defaultColor: Int, @ColorInt val tintColor: Int): TabType()
    }

    val name : String
    val tabType: TabType
    val fragment: Class<out Fragment>

    constructor(fragment: Class<out Fragment>, name: String, defaultBitmap: Bitmap, selectedBitmap: Bitmap) {
        this.name = name
        this.fragment = fragment
        this.tabType = TabType.Image(defaultBitmap, selectedBitmap)
    }

    constructor(fragment: Class<out Fragment>, name: String, iconFont: String, defaultIconName: String, selectedIconName: String?,
                @ColorInt defaultColor: Int, @ColorInt tintColor: Int) {
        this.name = name
        this.fragment = fragment
        val selectedIconNameNew = if (TextUtils.isEmpty(selectedIconName)) {
            defaultIconName
        } else {
            selectedIconName!!
        }
        this.tabType = TabType.TextIcon(iconFont, defaultIconName,
            selectedIconNameNew, defaultColor, tintColor)
    }


}