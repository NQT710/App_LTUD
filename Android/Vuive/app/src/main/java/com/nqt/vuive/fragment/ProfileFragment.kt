package com.nqt.vuive.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.nqt.vuive.R
import com.nqt.vuive.activity.LoginActivity
import com.nqt.vuive.adapter.ViewPagerAdapter
import com.nqt.vuive.databinding.FragmentHomeBinding
import com.nqt.vuive.databinding.FragmentOnboardBinding
import com.nqt.vuive.databinding.FragmentProfileBinding
import com.nqt.vuive.model.NetworkUtils
import com.nqt.vuive.viewmodel.AuthViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProfileFragment : Fragment(), View.OnClickListener {

    private lateinit var pagerTest: ViewPager2
    private lateinit var tabDemo: TabLayout

    private lateinit var binding : FragmentProfileBinding

    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadData()
        likeData()
        binding.btnSignout.setOnClickListener(this@ProfileFragment)
    }

    private fun likeData(){
        pagerTest = binding.pagerTest
        tabDemo = binding.tabDemo
        val adapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        pagerTest.adapter = adapter
        TabLayoutMediator(tabDemo,pagerTest){tab,position ->
            when(position){
                0->{
                    tab.text="SPECIES"
                }
                1->{
                    tab.text="ARTICLES"
                }
            }
        }.attach()
    }
    private fun loadData(){
        viewModel.loadData()
        viewModel.loadStatus.observe(viewLifecycleOwner, Observer { data ->
            binding.tvNameProfile.text = data?.name
            binding.tvLocation.text = data?.location
            context?.let { Glide.with(it).load(data?.avatar).error(R.drawable.avatar_default).into(binding.imageProfile) }

        })
    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_signout -> {
                if(NetworkUtils.isInternetConnected(requireContext())){
                    viewModel.logout()
                    startActivity(Intent(context, LoginActivity::class.java))
                    activity?.finish()
                }else{
                    Toast.makeText(requireContext(), "Internet is not connected", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}