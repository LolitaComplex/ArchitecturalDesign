package com.doing.diui.page.lifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.doing.diui.R
import com.doing.hilibrary.log.DiLog
import com.doing.hilibrary.util.DiDataBus

class LifecycleActivity : AppCompatActivity() {

    companion object {
        const val TAG = "LifecycleActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lifecycle)

        DiLog.w(TAG, "LifecycleActivity onCreate")

        val liveData = MutableLiveData<String>()
        liveData.postValue("")

        liveData.observe(this, object : Observer<String> {
            override fun onChanged(t: String) {
                DiLog.d(TAG, "onChange: $t")
            }
        })

        DiDataBus.with<String>("hello_live_bus").observe(this)
            {data ->
            Toast.makeText(this, "Lifecycle$data", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        DiLog.w(TAG, "LifecycleActivity onStart")
    }

    override fun onResume() {
        super.onResume()
        DiLog.w(TAG, "LifecycleActivity onResume")
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()

        lifecycle.addObserver(LifecycleTest())
        lifecycle.addObserver(LifecycleEventTest())
        Log.w(TAG, "LifecycleActivity register observer finish")
    }
}