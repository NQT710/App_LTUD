package com.nqt.vuive.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.nqt.vuive.R

class addNewPlant  : Fragment() {


    private lateinit var pagerTest: ViewPager2
    private lateinit var tabDemo: TabLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        // Inflate the layout for this fragment
        pagerTest = view.findViewById(R.id.pagerTest)
        tabDemo = view.findViewById(R.id.tabDemo)
        val adapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        pagerTest.adapter = adapter
        TabLayoutMediator(tabDemo, pagerTest) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "ARTICLES"
                }

                1 -> {
                    tab.text = "SPECIES"
                }


            }
        }.attach()

        return view

    }


}