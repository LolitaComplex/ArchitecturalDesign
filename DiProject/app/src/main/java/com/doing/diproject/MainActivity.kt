package com.doing.diproject

import android.os.Bundle
import com.doing.diproject.common.ActivityProvider
import com.doing.diproject.common.DiBaseActivity
import com.doing.diproject.logic.MainLogic

class MainActivity : DiBaseActivity(), ActivityProvider {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainLogic = MainLogic(this)
        mainLogic.init()
    }
}