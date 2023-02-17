package com.doing.diproject.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.DialogFragment
import com.doing.diproject.R
import com.doing.dicommon.component.ActivityProvider
import com.doing.dicommon.component.DiBaseActivity
import com.doing.diproject.BuildConfig
import com.doing.diproject.home.logic.MainLogic
import com.doing.diui.common.DiStatusBar
import com.doing.hilibrary.log.DiLog

class MainActivity : DiBaseActivity(), ActivityProvider {

    companion object {
        const val TAG = "MainActivityDraw"
        val start by lazy {
            System.currentTimeMillis()
        }
    }

    private lateinit var mMainLogic: MainLogic

    override fun onCreate(savedInstanceState: Bundle?) {
        DiLog.d(TAG, "onCreate: 0ms start: $start")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainLogic = MainLogic(this)
        mainLogic.initLogic(savedInstanceState)
        mMainLogic = mainLogic


        DiStatusBar.setStatusBar(this, true, Color.WHITE, false)
        val time = System.currentTimeMillis() - start
        DiLog.d(TAG, "onCreate end: ${time}ms")
    }

    override fun onResume() {
        super.onResume()
        val time = System.currentTimeMillis() - start
        DiLog.d(TAG, "onResume end: ${time}ms")
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        val time = System.currentTimeMillis() - start
        DiLog.d(TAG, "onWindowFocusChange: ${time}ms")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mMainLogic.onSaveInstanceState(outState)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            if (BuildConfig.DEBUG) {
                val clazz = Class.forName("com.doing.didebugtool.DebugToolDialog")
                val dialogFragment = clazz.getConstructor().newInstance() as DialogFragment
                dialogFragment.show(supportFragmentManager, "debug_tool")
            }
        }

        return super.onKeyDown(keyCode, event)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        supportFragmentManager.fragments.forEach { fragment ->
            fragment.onActivityResult(resultCode, resultCode, data)
        }
    }
}