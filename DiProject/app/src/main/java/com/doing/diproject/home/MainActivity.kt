package com.doing.diproject.home

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import com.doing.diproject.R
import com.doing.dicommon.component.ActivityProvider
import com.doing.dicommon.component.DiBaseActivity
import com.doing.diproject.BuildConfig
import com.doing.diproject.home.logic.MainLogic

class MainActivity : DiBaseActivity(), ActivityProvider {

    private lateinit var mMainLogic: MainLogic

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainLogic = MainLogic(this)
        mainLogic.initLogic(savedInstanceState)
        mMainLogic = mainLogic

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mMainLogic.onSaveInstanceState(outState)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            if (BuildConfig.DEBUG) {

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