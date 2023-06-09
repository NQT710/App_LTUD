package com.nqt.vuive.activity

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.nqt.vuive.R
import com.nqt.vuive.databinding.ActivityDetailArticlesBinding
import com.nqt.vuive.fragment.HomeFragment
import com.nqt.vuive.fragment.ProfileFragment


class DetailArticlesActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var binding :ActivityDetailArticlesBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticlesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val imageArticles = intent.getStringExtra("image_articles")
        val title = intent.getStringExtra("title")
        val avatarAuthor = intent.getStringExtra("avatar_author")
        val name = intent.getStringExtra("name")
        val day = intent.getStringExtra("day")
        val tagArticles = intent.getStringExtra("tag_articles")
        val descriptionArticles = intent.getStringExtra("description_articles")

        Glide.with(this).load(imageArticles).centerCrop().into(binding.imgArticles)
        binding.titleDetail.text = title
        Glide.with(this).load(avatarAuthor).centerCrop().into(binding.avtDetail)
        binding.nameDetail.text = name
        binding.dayDetail.text = day
        binding.description.text = descriptionArticles
        //Xủ lý chuỗi data của tagArticles xuất ra mỗi textview
        setDataTagArticles(tagArticles)

        //Xử lý sự kiện khi nhấn back
        binding.btnBack.setOnClickListener(this@DetailArticlesActivity)

        // Xử lý sự kiện khi nhấn nút +
        binding.btnOpenCamera.setOnClickListener(this@DetailArticlesActivity)

        // Xử lý sự kiện khi nhấn nút trên BottomNavigationView
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

    private fun setDataTagArticles(tagArticles: String?) {
        val tagArticlesArray = tagArticles?.split(", ")

        if (tagArticles?.isNotEmpty() == true) {
            if (tagArticlesArray != null) {
                for (tag in tagArticlesArray) {
                    val textViewTag = TextView(this)
                    textViewTag.setBackgroundColor(Color.parseColor("#1A2F91EB"))
                    textViewTag.setTextColor(Color.parseColor("#2F91EB"))
                    textViewTag.text = tag
                    textViewTag.textSize = 14f
                    textViewTag.setTypeface(null, Typeface.BOLD)
                    textViewTag.gravity = 20.dpToPx(this)
                    val layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        25.dpToPx(this)
                    )
                    layoutParams.setMargins(0, 0, 20.dpToPx(this), 0)
                    textViewTag.layoutParams = layoutParams

                    binding.layoutTag.addView(textViewTag)
                }
            }
        }
    }


    // Hàm xử lý khi nhấn nút trên BottomNavigationView
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_back -> startActivity(Intent(this, ArticlesActivity::class.java))
        }
    }


    fun Int.dpToPx(context: Context): Int {
        val scale = context.resources.displayMetrics.density
        return (this * scale + 0.5f).toInt()
    }

}



