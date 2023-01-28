package com.doing.dirooter

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.navigation.findNavController
import com.doing.dinavigator.NavigatorConstant
import com.doing.navigationbase.utils.NavUtil
import com.doing.navigatorannotation.Destination
import com.doing.poet.PoetConstant

@Destination(pageUrl = MainConstant.ACTIVITY_MAIN)
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)
        findViewById<Button>(R.id.MainActivity_btn_navigation).setOnClickListener {
            navController.navigate(Uri.parse(NavigatorConstant.ACTIVITY_NAVIGATION_MAIN))
        }

        findViewById<Button>(R.id.MainActivity_btn_poet).setOnClickListener {
            navController.navigate(Uri.parse(PoetConstant.ACTIVITY_POET))
        }

        val hostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        NavUtil.buildNavGraph(this, hostFragment!!.childFragmentManager,
            navController, R.id.nav_host_fragment)
    }
}