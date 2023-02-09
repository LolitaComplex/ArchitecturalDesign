package com.doing.diui.page.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.doing.hilibrary.log.DiLog

class LifecycleTest : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        DiLog.d(LifecycleActivity.TAG, "LifecycleObserver onCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        DiLog.d(LifecycleActivity.TAG, "LifecycleObserver onStart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        DiLog.d(LifecycleActivity.TAG, "LifecycleObserver onResume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        DiLog.d(LifecycleActivity.TAG, "LifecycleObserver onPause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        DiLog.d(LifecycleActivity.TAG, "LifecycleObserver onStop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        DiLog.d(LifecycleActivity.TAG, "LifecycleObserver onDestroy")
    }
}