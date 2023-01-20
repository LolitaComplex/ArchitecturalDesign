package com.doing.diui.banner

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes

open class DiBannerModel(val type: Type,
     @LayoutRes val layoutId : Int) {

    sealed class Type {
        class Url(url: String) : Type()
        class File(dir: String): Type()
        class Resource(@IdRes id: Int) : Type()
    }
}