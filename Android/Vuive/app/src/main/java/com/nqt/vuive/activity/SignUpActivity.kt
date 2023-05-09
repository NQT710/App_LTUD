package com.nqt.vuive.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.nqt.vuive.R
import com.nqt.vuive.databinding.ActivitySignUpBinding
import com.nqt.vuive.model.UserSignUp
import com.nqt.vuive.viewmodel.AuthViewModel
import com.nqt.vuive.viewmodel.Status

class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : ActivitySignUpBinding
    private lateinit var viewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]


        binding.tvLogin.setOnClickListener(this@SignUpActivity)
        binding.btnSignup.setOnClickListener(this@SignUpActivity)
        binding.edtFullName.editText?.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus){
                binding.edtFullName.error = null
            }else if(binding.edtFullName.editText?.text.isNullOrEmpty())
                binding.edtFullName.error = "Full name is required!"
        }
        binding.edtEmailSignup.editText?.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus){
                binding.edtEmailSignup.error = null
            }else if(binding.edtEmailSignup.editText?.text.isNullOrEmpty())
                binding.edtEmailSignup.error = "Email is required!"
        }
        binding.edtPasswordSignup.editText?.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus){
                binding.edtPasswordSignup.error = null
            }else if(binding.edtPasswordSignup.editText?.text.isNullOrEmpty())
                binding.edtPasswordSignup.error = "Password is required!"
        }
        notification()
    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_login -> {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }

            R.id.btn_signup -> {
                val fullName = binding.edtFullName.editText?.text.toString().trim()
                val email = binding.edtEmailSignup.editText?.text.toString().trim()
                val password = binding.edtPasswordSignup.editText?.text.toString().trim()
                val avatar = ""
                val user = UserSignUp(fullName, email, avatar)
                if(fullName.isEmpty()){
                    binding.edtFullName.error = "Full name is required!"
                    //binding.edtFullName.editText?.requestFocus()
                    return
                }
                if (email.isEmpty()){
                    binding.edtEmailSignup.error = "Email is required!"
                    //binding.edtEmailSignup.requestFocus()
                    return
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    binding.edtEmailSignup.error = "Pleas provide valid email!"
                    //binding.edtEmailSignup.requestFocus()
                    return
                }
                if (password.isEmpty()){
                    binding.edtPasswordSignup.error = "Password is required!"
                    //binding.edtPasswordSignup.requestFocus()
                    return
                }
                if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%!\\-_?&./])(?=\\S+\$).{11,}".toRegex())){
                    binding.edtPasswordSignup.error = "Invalid password"
                    //binding.edtPasswordSignup.requestFocus()
                    return
                }
                viewModel.signUp(user,password)


            }
        }
    }
    private fun notification(){
        viewModel.signUpStatus.observe(this) { status ->
            when (status) {
                is Status.Loading -> {
                    binding.progressSignup.visibility = View.VISIBLE
                }
                is Status.Success -> {
                    binding.progressSignup.visibility = View.GONE
                    Toast.makeText(this, "Please check your email to complete the registration!", Toast.LENGTH_LONG).show()
                }
                is Status.Error -> {
                    binding.progressSignup.visibility = View.GONE
                    Toast.makeText(this, "Registration failed!", Toast.LENGTH_LONG).show()

                }
            }
        }
    }
}

