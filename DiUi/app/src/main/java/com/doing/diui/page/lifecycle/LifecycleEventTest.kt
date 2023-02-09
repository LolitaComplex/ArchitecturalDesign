package com.doing.diui.page.lifecycle

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

class LifecycleEventTest : LifecycleEventObserver {

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        Log.i(LifecycleActivity.TAG, "LifecycleEventObserver ${event.name}")
    }
}