package com.doing.dinavigator.utils

import android.content.ComponentName
import android.content.Context
import android.graphics.Color
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphNavigator
import androidx.navigation.fragment.DialogFragmentNavigator
import androidx.navigation.fragment.FragmentNavigator
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.TypeReference
import com.doing.dinavigator.R
import com.doing.dinavigator.entity.BottomBar
import com.doing.dinavigator.entity.BottomBarKt
import com.doing.dinavigator.entity.Destination
import com.doing.dinavigator.entity.DestinationKt
import com.google.android.material.bottomnavigation.BottomNavigationView

object NavUtil {

    fun parseFile(context: Context, fileName: String): String {
        val assets = context.assets
        assets.open(fileName).bufferedReader().use { reader->
            return reader.readText()
        }
    }

    fun buildNavGraph(activity: FragmentActivity, manager: FragmentManager,
        controller: NavController, containerId: Int) {

        val json = parseFile(activity, "destination.json")
        val destinations = JSON.parseObject(json,
            object : TypeReference<HashMap<String, Destination>>() {})

        val provider = controller.navigatorProvider

        val graphNavigator = provider.getNavigator(NavGraphNavigator::class.java)
        val navGraph = NavGraph(graphNavigator)

        destinations.values.forEach { destination ->
            when (destination.pageType) {
                "Activity" -> {
                    val navigator = provider.getNavigator(ActivityNavigator::class.java)
                    val node = navigator.createDestination()
                    node.id = destination.id
                    node.addDeepLink(destination.pageUrl)
                    node.setComponentName(ComponentName(activity.packageName, destination.className))
                    navGraph.addDestination(node)
                }
                "Fragment" -> {
                    val navigator = provider.getNavigator(FragmentNavigator::class.java)
                    val node = navigator.createDestination()
                    node.id = destination.id
                    node.addDeepLink(destination.pageUrl)
                    node.className = destination.className
                    navGraph.addDestination(node)
                }
                "Dialog" -> {
                    val navigator = provider.getNavigator(DialogFragmentNavigator::class.java)
                    val node = navigator.createDestination()
                    node.id = destination.id
                    node.addDeepLink(destination.pageUrl)
                    node.className = destination.className
                    navGraph.addDestination(node)
                }
            }

            if (destination.isStarter) {
                navGraph.startDestination = destination.id
            }
        }

        controller.graph = navGraph
    }

    fun buildBottomBar(bottomNavView: BottomNavigationView) {
        val json = parseFile(bottomNavView.context, "main_tabs_config.json")
        val bottomBarData = JSON.parseObject(json, BottomBar::class.java)

        val destinationJson = parseFile(bottomNavView.context, "destination.json")
        val destinations = JSON.parseObject(destinationJson,
            object : TypeReference<HashMap<String, Destination>>() {})

        bottomBarData.tabs.forEach { tab ->
//            if (!tab.enable) {
//                return@forEach
//            }

            val destination = destinations[tab.pageUrl]
            if (destination != null) {
                val menuItem = bottomNavView.menu.add(0, destination.id, tab.index, tab.title)
                menuItem.setIcon(R.drawable.ic_home_black_24dp)
                menuItem.actionView?.setBackgroundColor(Color.GRAY)
            }
        }
    }
}