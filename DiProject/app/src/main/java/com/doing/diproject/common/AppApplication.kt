package com.doing.diproject.common

import android.app.Application
import android.content.Context
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

    companion object {
        lateinit var application: Application
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        application = this
    }

    override fun onCreate() {
        super.onCreate()
        ARouter.init(this)

        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
    }



}