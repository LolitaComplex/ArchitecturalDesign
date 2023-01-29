package com.doing.poet.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.doing.navigatorannotation.AddKotlinCode
import com.doing.navigatorannotation.Destination
import com.doing.poet.PoetConstant
import com.doing.poet.R

@AddKotlinCode
@Destination(pageUrl = PoetConstant.ACTIVITY_KOTLIN_POET)
class KotlinPoetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_poet)
    }
}