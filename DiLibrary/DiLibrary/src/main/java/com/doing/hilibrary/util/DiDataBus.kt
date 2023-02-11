package com.doing.hilibrary.util

import androidx.lifecycle.*
import java.util.concurrent.ConcurrentHashMap

object DiDataBus {

    private val eventMap = ConcurrentHashMap<String, StickyLiveData<*>>()

    fun <T> with(eventName: String): StickyLiveData<T> {
        val liveData = eventMap[eventName]
            ?: return StickyLiveData<T>(eventName).apply {
                eventMap[eventName] = this
            }
        return liveData as StickyLiveData<T>
    }

    class StickyLiveData<T>(private val eventName: String) : LiveData<T>() {
        internal var mData: T? = null
        internal var mVersion = 0

        fun setStickyData(data: T) {
            this.mData = data
            setValue(data)
        }

        fun postStickData(data: T) {
            this.mData = data
            postValue(data)
        }

        override fun setValue(value: T) {
            mVersion++
            super.setValue(value)
        }

        override fun postValue(value: T) {
            super.postValue(value)
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
            observeSticky(owner, false, observer)
        }

        fun observeSticky(owner: LifecycleOwner, sticky: Boolean, observer: Observer<in T>) {
            owner.lifecycle.addObserver(LifecycleEventObserver { source, event ->
                if (event == Lifecycle.Event.ON_DESTROY) {
                    eventMap.remove(eventName)
                }
            })
            super.observe(owner, StickyObserver<T>(this, sticky, observer))

        }
    }

    class StickyObserver<T>(
        private val stickyLiveData: DiDataBus.StickyLiveData<T>,
        private val isSticky: Boolean,
        private val observer: Observer<in T>) : Observer<T> {

        private var lastVersion = stickyLiveData.mVersion

        override fun onChanged(t: T) {
            if (lastVersion >= stickyLiveData.mVersion) {
                if (isSticky && stickyLiveData.mData != null) {
                    observer.onChanged(stickyLiveData.mData)
                }
                return
            }

            lastVersion = stickyLiveData.mVersion
            observer.onChanged(t)
        }
    }
}