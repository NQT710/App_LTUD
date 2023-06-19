package com.nqt.vuive.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.nqt.vuive.R
import com.nqt.vuive.activity.LoginActivity
import com.nqt.vuive.databinding.FragmentHomeBinding
import com.nqt.vuive.databinding.FragmentOnboardBinding
import com.nqt.vuive.databinding.FragmentProfileBinding
import com.nqt.vuive.viewmodel.AuthViewModel
import kotlinx.android.synthetic.main.fragment_profile.pagerTest
import kotlinx.android.synthetic.main.fragment_profile.tabDemo

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class ProfileFragment  : Fragment() {


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
        TabLayoutMediator(tabDemo,pagerTest){tab,position ->
            when(position){
                0->{
                    tab.text="ARTICLES"
                }
                1->{
                    tab.text="SPECIES"
                }


            }
        }.attach()

        return view

    }


}