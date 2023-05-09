package com.nqt.vuive.onboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.nqt.vuive.R
import com.nqt.vuive.activity.LoginActivity
import com.nqt.vuive.activity.MainActivity
import com.nqt.vuive.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityOnboardingBinding
    private val list : List<OnboardItems> = OnboardItems.getData()

    private val SIZE = list.size
    private var previousState =  ViewPager2.SCROLL_STATE_IDLE
    private var currentPage : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.viewPager.adapter = OnboardAdapter(list, supportFragmentManager, lifecycle)
        binding.circleOnboard.setViewPager(binding.viewPager)

        binding.viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                currentPage = position
                binding.btnOnboard.text = if (position == 2) "Login" else "Next"
            }
            @Suppress("KotlinConstantConditions")
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)

                if ((currentPage >= SIZE - 1)// end of list. these checks can be
                    // used individualy to detect end or start of pages
                    && previousState == ViewPager2.SCROLL_STATE_DRAGGING // from    DRAGGING
                    && state == ViewPager2.SCROLL_STATE_IDLE) {          // to      IDLE
                    //overscroll performed. work here
                    onSigIn()
                }
                previousState = state
            }
        })
        binding.btnOnboard.setOnClickListener(this@OnboardingActivity)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_onboard -> checkIntroPosition()
        }
    }
    private fun checkIntroPosition() {
        if (currentPage == 2) onSigIn() else onNextIntro()
    }

    private fun onNextIntro() {
        binding.viewPager.setCurrentItem(currentPage + 1, true)
    }

    private fun onSigIn() {
        val intent = Intent(this@OnboardingActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
