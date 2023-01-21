package com.doing.diui.banner.core

import androidx.annotation.DrawableRes

open class DiBannerModel(val type: Type) {

    sealed class Type {
        open class Url(val url: String) : Type()
        class File(val dir: String): Type()
        class Resource(@DrawableRes val id: Int) : Type()
    }
}