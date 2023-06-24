package com.nqt.vuive.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.makeramen.roundedimageview.RoundedImageView
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
    private lateinit var viewModel: AuthViewModel
    private lateinit var binding : FragmentProfileBinding
    private lateinit var etName: TextView
    private lateinit var etLocate: TextView
    private lateinit var imImage: RoundedImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        // Inflate the layout for this fragment
        pagerTest = view.findViewById(R.id.pagerTest)
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        tabDemo = view.findViewById(R.id.tabDemo)
        val adapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        etName = view.findViewById(R.id.idProfile)
        etLocate = view.findViewById(R.id.locateProfile)
        imImage = view.findViewById(R.id.imageProfile)
        pagerTest.adapter = adapter
        binding = FragmentProfileBinding.inflate(inflater)
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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadData()
        viewModel.loadStatus.observe(viewLifecycleOwner, Observer { data ->
            etName.text = data?.name
            context?.let { Glide.with(it).load(data?.avatar).error(R.drawable.avatar_default).into(imImage) };
            etLocate.text = " " + data?.avatar
        })
    }
}