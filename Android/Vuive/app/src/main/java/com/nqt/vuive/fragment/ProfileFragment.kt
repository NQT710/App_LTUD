package com.nqt.vuive.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
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
import com.nqt.vuive.model.UserSignUp
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

        binding.tvNameProfile.setOnClickListener{
            binding.tvNameProfile.requestFocus()
            showEditTextDialog(binding.tvNameProfile)
        }

        binding.tvNameProfile.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do nothing
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        binding.tvLocation.setOnClickListener{
            binding.tvLocation.requestFocus()
            showEditTextDialog(binding.tvLocation)
        }

        binding.tvLocation.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do nothing
            }

            override fun afterTextChanged(s: Editable?) {
                upDateUi()
            }
        })
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

    private fun showEditTextDialog(textView : TextView) {
        val editText = EditText(requireContext())

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Enter new")
            .setView(editText)
            .setPositiveButton("OK") { _, _ ->
                val content = editText.text.toString()
                textView.text = content

                // Hide the keyboard
                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(editText.windowToken, 0)
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.show()

        // Show the keyboard
        editText.requestFocus()
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }
    private fun upDateUi() {
        var data = UserSignUp()
        data.name = binding.tvNameProfile.text.toString()
        data.location = binding.tvLocation.text.toString()
        viewModel.saveData(data)
    }
}