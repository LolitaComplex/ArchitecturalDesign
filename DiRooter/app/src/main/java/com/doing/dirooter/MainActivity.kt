package com.doing.dirooter

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.navigation.findNavController
import com.doing.dinavigator.NavMainActivity
import com.doing.dinavigator.utils.NavUtil
import com.doing.navigatorannotation.Destination

@Destination(pageUrl = "dirooter://activity/main")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)
        findViewById<Button>(R.id.MainActivity_btn_navigation).setOnClickListener {
            navController.navigate(Uri.parse("dirooter://activity/navmain"))
        }

        val hostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        NavUtil.buildNavGraph(this, hostFragment!!.childFragmentManager,
            navController, R.id.nav_host_fragment)
    }
}