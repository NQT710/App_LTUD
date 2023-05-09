package com.nqt.vuive.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nqt.vuive.R
import com.nqt.vuive.activity.LoginActivity
import com.nqt.vuive.databinding.FragmentHomeBinding
import com.nqt.vuive.databinding.FragmentOnboardBinding
import com.nqt.vuive.databinding.FragmentProfileBinding
import com.nqt.vuive.viewmodel.AuthViewModel

class ProfileFragment : Fragment(), View.OnClickListener {
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

        binding.btnOut.setOnClickListener(this@ProfileFragment)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_out -> {
                viewModel.logout()
                startActivity(Intent(context, LoginActivity::class.java))
                activity?.finish()
            }
        }
    }
}