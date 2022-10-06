package hu.bme.aut.android.launcher.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import hu.bme.aut.android.launcher.fragment.ApplicationsFragment
import hu.bme.aut.android.launcher.fragment.DialerFragment

class LauncherPagerAdapter(manager: FragmentManager) : FragmentStatePagerAdapter(manager) {

    companion object {
        private const val NUM_PAGES = 2
    }

    override fun getCount(): Int = NUM_PAGES

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> DialerFragment()
            1 -> ApplicationsFragment()
            else -> throw IllegalArgumentException("No such page!")
        }
    }
}