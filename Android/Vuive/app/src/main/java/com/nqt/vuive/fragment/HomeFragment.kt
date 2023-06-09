package com.nqt.vuive.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.nqt.vuive.R
import com.nqt.vuive.activity.ArticlesActivity
import com.nqt.vuive.databinding.FragmentHomeBinding
import com.nqt.vuive.databinding.FragmentProfileBinding
import com.nqt.vuive.viewmodel.AuthViewModel

class HomeFragment : Fragment(), View.OnClickListener {


    private lateinit var binding : FragmentHomeBinding

    private lateinit var viewModel: AuthViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        return binding.root

    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadData()
        viewModel.loadStatus.observe(viewLifecycleOwner, Observer { data ->
            binding.tvName.text = data?.name + ","
            context?.let { Glide.with(it).load(data?.avatar).error(R.drawable.avatar_default).into(binding.imgAvatarHome) };

        })

        binding.layoutIdentify.setOnClickListener(this@HomeFragment)
        binding.layoutSpecial.setOnClickListener(this@HomeFragment)
        binding.layoutArticle.setOnClickListener(this@HomeFragment)
    }

    override fun onClick(v: View?) {
        when(v?.id){
//            R.id.layout_identify -> {
//                startActivity(Intent(context, IdentifyActivity::class.java))
//                activity?.finish()
//            }
//            R.id.layout_special -> {
//                startActivity(Intent(context, SpecialActivity::class.java))
//                activity?.finish()
//            }
            R.id.layout_article -> {
                startActivity(Intent(context, ArticlesActivity::class.java))
                activity?.finish()
            }
        }
    }
}

