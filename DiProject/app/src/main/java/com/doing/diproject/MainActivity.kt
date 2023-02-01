package com.doing.diproject

import android.os.Bundle
import android.util.Log
import com.doing.diproject.common.ActivityProvider
import com.doing.diproject.common.DiBaseActivity
import com.doing.diproject.logic.MainLogic
import com.doing.diproject.net.ApiFactory
import com.doing.hilibrary.log.DiLog
import com.doing.hilibrary.restful.DiCallback
import com.doing.hilibrary.restful.DiResponse
import org.json.JSONObject

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
}