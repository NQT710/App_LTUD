package com.nqt.vuive.activity

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.nqt.vuive.R
import com.nqt.vuive.databinding.ActivityLoginBinding
import com.nqt.vuive.databinding.ActivityMainBinding
import com.nqt.vuive.databinding.FragmentHomeBinding
import com.nqt.vuive.fragment.HomeFragment
import com.nqt.vuive.fragment.ProfileFragment
import com.nqt.vuive.fragment.addNewPlantDetail
import com.nqt.vuive.viewmodel.AuthViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var HomeFragment: HomeFragment
    private lateinit var addNewPlantDetail: addNewPlantDetail
    //private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

         HomeFragment = HomeFragment()
         addNewPlantDetail=addNewPlantDetail()
        replaceFragment(HomeFragment())
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, HomeFragment)
            .commit()
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.action_home -> replaceFragment(HomeFragment())
                R.id.action_profile -> replaceFragment(ProfileFragment())

                else ->{

                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
    fun switchToFragmentTwo(  imageBitmap: Bitmap) {
        val bundle = Bundle()
        bundle.putParcelable("data", imageBitmap)
        addNewPlantDetail.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, addNewPlantDetail)
            .addToBackStack(null) // Thêm vào Back Stack
            .commit()
    }
}
