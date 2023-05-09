package com.nqt.vuive.viewmodel

import android.app.Application
import android.widget.Toast
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.nqt.vuive.model.UserSignUp
import kotlin.math.log

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val context = application.applicationContext
    private val database: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _signUpStatus = MutableLiveData<Status>()
    val signUpStatus: LiveData<Status> get() = _signUpStatus
    //val signUpStatus = MutableLiveData<Status>()

    private val _logInStatus = MutableLiveData<Status>()
    val logInStatus: LiveData<Status> get() = _logInStatus

    private val _forgotStatus = MutableLiveData<Status>()
    val forgotStatus: LiveData<Status> get() = _forgotStatus

    private val _loadStatus = MutableLiveData<UserSignUp?>()
    val loadStatus: LiveData<UserSignUp?> get() = _loadStatus

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)

    fun signUp(user: UserSignUp, password: String) {
        _signUpStatus.value = Status.Loading
        mAuth.createUserWithEmailAndPassword(user.email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
//                    val newUser = hashMapOf(
//                        "name" to user.name,
//                        "email" to user.email,
//                        "avatar" to user.avatar
//                    )
                    task.result.user?.let {
                        database.collection("user").document(it.uid).set(user)
                        //Tạo thành công thì sẽ gửi email xác nhận
                        mAuth.currentUser?.sendEmailVerification()
                            ?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {

                                    // Nếu gửi email xác nhận thành công, gửi trạng thái đăng ký thành công
                                    _signUpStatus.value = Status.Success
                                }
                            }
                    }
                } else {
                    // Tạo không thành công
                    _signUpStatus.value = Status.Error(task.exception?.message)
                }
            }
    }

    fun logIn(email: String, password: String, rememberMe: Boolean) {
        _logInStatus.value = Status.Loading

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user: FirebaseUser? = mAuth.currentUser
                    if (user != null) {
                        if (user.isEmailVerified) {
                            if (rememberMe) {
                                // Lưu thông tin đăng nhập vào SharedPreferences
                                sharedPreferences.edit()
                                    .putString("email", email)
                                    .putString("password", password)
                                    .apply()
                            } else {
                                clearSavedLoginInfor()
                            }
                            _logInStatus.value = Status.Success
                        } else {
//                            Toast.makeText(context,"Unconfirmed e-mail!\n" +
//                                    "Please check your email!", Toast.LENGTH_LONG).show()
                            _logInStatus.value = Status.Error(
                                "Unconfirmed e-mail! Please check your email!"
                            )
                        }
                    }
                } else {
                    _logInStatus.value = Status.Error("Email or Password incorrect!")
                }
            }
    }

    fun loadSavedLoginInfo() {
        val email = sharedPreferences.getString("email", null)
        val password = sharedPreferences.getString("password", null)
        if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
            _logInStatus.value = Status.Success
        }
    }

    fun clearSavedLoginInfor() {
        sharedPreferences.edit()
            .remove("email")
            .remove("password")
            .apply()
    }

    fun logout() {
        mAuth.signOut()
        clearSavedLoginInfor()
    }

    fun forgotPassword(email: String) {
        _forgotStatus.value = Status.Loading
        mAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _forgotStatus.value = Status.Success
                } else {
                    _forgotStatus.value = Status.Error(task.exception?.message)
                }
            }
    }

    fun loadData() {
//        val id = mAuth.currentUser?.uid
//        Log.i("TAG_I", "loadData: id = $id")
//
//        id?.let {
//            database.collection("user")
//                .document(it).collection("User").document().get()
//                .addOnSuccessListener { document ->
//                    val avatar = document.get("avatar", String::class.java)
//                    val name = document.get("name", String::class.java)
//                    Log.i("TAG_I", "name: + $name")
//                }
//                .addOnFailureListener { exception ->
//                    // Handle any errors here
//                }
//        }
        val id = mAuth.currentUser?.uid
        Log.i("TAG_I", "loadData: id = $id")
        id?.let {
            database.collection("user").document(it).get()
                .addOnSuccessListener { document ->
                    val avatar = document.get("avatar", String::class.java)
                    val name = document.get("name", String::class.java)
                    val data = document.toObject(UserSignUp::class.java)


                    Log.i("TAG_I", "name: + $name")
                    _loadStatus.value = data
                }
                .addOnFailureListener { exception ->
                    // Handle any errors here
                }
        }
    }

}

sealed class Status {
    object Loading : Status()
    object Success : Status()
    data class Error(val errorMessage: String?) : Status()
}

