package com.doing.diproject.common

import android.content.res.Resources
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentManager

interface ActivityProvider {

    fun <T : View> findViewById(@IdRes id: Int): T

    fun getSupportFragmentManager(): FragmentManager

    fun getResources(): Resources

    fun getString(@StringRes id: Int): String
}