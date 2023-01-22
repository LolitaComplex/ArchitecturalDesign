package com.doing.hilibrary.global

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.lang.ref.WeakReference

class DiActivityManager private constructor(){

    companion object {
        val instance by lazy(LazyThreadSafetyMode.PUBLICATION) { DiActivityManager() }
    }

    private val activitys = mutableListOf<WeakReference<Activity?>>()
    private val onBackgroundCallbacks = mutableListOf<(Boolean) -> Unit>()
    private var activityCount = 0
    private var isFront = false

    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(DiActivityLifecycleCallbacks())
    }

    private inner class DiActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            activitys.add(WeakReference(activity))
        }

        override fun onActivityStarted(activity: Activity) {
            activityCount++
            if (!isFront && activityCount > 0) {
                isFront = true
                onFrontBackground(true)
            }
        }

        override fun onActivityResumed(activity: Activity) {
        }

        override fun onActivityPaused(activity: Activity) {
        }

        override fun onActivityStopped(activity: Activity) {
            activityCount--
            if (isFront && activityCount <= 0) {
                isFront = false
                onFrontBackground(false)
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {
            var targetActivity: WeakReference<Activity?>? = null
            activitys.forEach { weakReference ->
                val item = weakReference.get()
                if (item != null && item == activity) {
                    targetActivity = weakReference
                    return@forEach
                }
            }

            if (targetActivity != null) {
                activitys.remove(targetActivity)
            }
        }
    }

    fun registerOnBackgroundChangeListener(listener: (Boolean) -> Unit) {
        onBackgroundCallbacks.add(listener)
    }

    fun unRegisterOnBackgroundChangeListener(listener: (Boolean) -> Unit) {
        onBackgroundCallbacks.remove(listener)
    }

    private fun onFrontBackground(isFront: Boolean) {
        onBackgroundCallbacks.forEach { callback ->
            callback.invoke(!isFront)
        }
    }
}