package com.doing.poet.java

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.doing.navigatorannotation.Destination
import com.doing.poet.PoetConstant
import com.doing.poet.R

@Destination(pageUrl = PoetConstant.ACTIVITY_JAVA_POET)
class JavaPoetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_java_poet)
    }
}