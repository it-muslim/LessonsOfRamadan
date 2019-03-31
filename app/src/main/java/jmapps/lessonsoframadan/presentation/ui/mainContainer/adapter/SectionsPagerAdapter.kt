package jmapps.lessonsoframadan.presentation.ui.mainContainer.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import jmapps.lessonsoframadan.presentation.ui.mainContainer.PlaceholderFragment

class SectionsPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return PlaceholderFragment.newInstance(position + 1)
    }

    override fun getCount(): Int {
        return 31
    }
}