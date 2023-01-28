package com.doing.dinavigator

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.doing.dinavigator.databinding.ActivityBottomNavigationBinding
import com.doing.dinavigator.utils.NavUtil
import com.doing.navigatorannotation.Destination

@Destination(pageUrl = "dirooter://activity/bottom/navigation")
class BottomNavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomNavigationBinding
    private lateinit var mNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment)
        val hostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        NavUtil.buildNavGraph(this, hostFragment!!.childFragmentManager,
            navController, R.id.nav_host_fragment)

        NavUtil.buildBottomBar(navView)
        navView.setOnItemSelectedListener { item ->
            mNavController.popBackStack()
            navController.navigate(item.itemId)
            true
        }
        mNavController = navController

//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)


    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}