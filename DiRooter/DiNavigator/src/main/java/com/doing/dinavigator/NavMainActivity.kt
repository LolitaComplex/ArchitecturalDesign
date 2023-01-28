package com.doing.dinavigator

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.navigation.findNavController
import com.doing.navigationbase.utils.NavUtil
import com.doing.navigatorannotation.Destination

@Destination(pageUrl = NavigatorConstant.ACTIVITY_NAVIGATION_MAIN)
class NavMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_main)

        val navController = findNavController(R.id.nav_host_fragment)
        findViewById<Button>(R.id.NavMainActivity_btn_bottom_nav).setOnClickListener {
            navController.navigate(Uri.parse(NavigatorConstant.ACTIVITY_NAVIGATION_BOTTOM))
        }

        val hostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        NavUtil.buildNavGraph(this, hostFragment!!.childFragmentManager,
            navController, R.id.nav_host_fragment)
    }
}