package com.doing.diproject.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.alibaba.android.arouter.launcher.ARouter
import com.doing.dicommon.BuildConfig
import com.doing.dicommon.component.DiBaseApplication

class AppApplication : DiBaseApplication(), ViewModelStoreOwner {

    private val mViewModelStore by lazy { ViewModelStore() }

    override fun getViewModelStore(): ViewModelStore {
        return mViewModelStore
    }


    class TestViewModel : ViewModel()

    fun test() {
        ViewModelProvider(this).get(TestViewModel::class.java)
    }

    override fun onCreate() {
        super.onCreate()
        ARouter.init(this)

        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        mViewModelStore.
    }



}