package com.doing.poet

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.navigation.findNavController
import com.doing.navigationbase.utils.NavUtil
import com.doing.navigatorannotation.Destination

@Destination(pageUrl = PoetConstant.ACTIVITY_POET)
class PoetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poet)

        val navController = findNavController(R.id.nav_host_fragment)

        findViewById<Button>(R.id.PoetActivity_btn_java).setOnClickListener {
            navController.navigate(Uri.parse(PoetConstant.ACTIVITY_JAVA_POET))
        }

        findViewById<Button>(R.id.PoetActivity_btn_kotlin).setOnClickListener {
            navController.navigate(Uri.parse(PoetConstant.ACTIVITY_KOTLIN_POET))
        }

        val hostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        NavUtil.buildNavGraph(this, hostFragment!!.childFragmentManager,
            navController, R.id.nav_host_fragment)
    }
}