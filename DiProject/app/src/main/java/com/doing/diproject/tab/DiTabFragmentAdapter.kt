package com.doing.diproject.tab

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.doing.diui.tab.bottom.DiTabBottomInfo

class DiTabFragmentAdapter(private val mFragmentManager: FragmentManager,
    private val mDataList: MutableList<DiTabBottomInfo>) {

    private var mCurrentFragment : Fragment? = null

    fun instantiateItem(container: View, position: Int) {
        val manager = mFragmentManager
        val currentFragment = mCurrentFragment

        val transaction = manager.beginTransaction()
        if (currentFragment != null) {
            transaction.hide(currentFragment)
        }

        val name = "${container.id} : $position"
        var fragment = manager.findFragmentByTag(name)
        if (fragment != null) {
            transaction.show(fragment)
        } else {
            fragment = getItem(position)
            if (fragment != null && !fragment.isAdded) {
                transaction.add(container.id, fragment, name)
            }
        }
        transaction.commitAllowingStateLoss()
        mCurrentFragment = fragment
    }

    private fun getItem(position: Int): Fragment? {
        return try {
             mDataList[position].fragment.newInstance()
        } catch (e: Exception) {
            null
        }
    }

    fun getCurrentFragment(): Fragment? {
        return mCurrentFragment
    }

    fun getCount(): Int {
        return mDataList.size
    }
}