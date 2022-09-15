package com.example.instagramclone.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.example.instagramclone.R
import com.example.instagramclone.managers.AuthManager
import com.example.instagramclone.managers.FirebaseConfig
import com.example.instagramclone.managers.handler.AuthHandler
import com.example.instagramclone.utils.DeepLink
import com.example.instagramclone.utils.Extensions.toast
import com.example.instagramclone.utils.Utils
import java.lang.Exception

/**
 * In SignUpActivity, user can login using email, password
 */
class SignInActivity : BaseActivity() {

    val TAG = SignInActivity::class.java.simpleName
    private lateinit var et_email: EditText
    private lateinit var et_password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        initViews()

    }

    private fun initViews() {
        val ll_background = findViewById<LinearLayout>(R.id.ll_background)
        val tv_welcome = findViewById<TextView>(R.id.tv_welcome)

        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)
        val b_sign_in: Button = findViewById(R.id.b_sign_in)
        b_sign_in.setOnClickListener {
//            FirebaseConfig(ll_background, tv_welcome).updateConfig()
            val email = et_email.text.toString().trim()
            val password = et_password.text.toString().trim()
            if (email.isNotEmpty() && password.isNotEmpty()){
                firebaseSignIn(email, password)
            }
//            val str1 = reverse(email)
//            val str2 = reverse(password)
        }
        val tv_sign_up: TextView = findViewById(R.id.tv_sign_up)
        tv_sign_up.setOnClickListener { callSignUpActivity() }

        val tv_link = findViewById<TextView>(R.id.tv_link)
        DeepLink.retrieveLink(intent, tv_link)

//        FirebaseConfig(ll_background, tv_welcome).applyConfig()
    }

    private fun firebaseSignIn(email: String, password: String){
        showLoading(this)
        AuthManager.signIn(email, password, object : AuthHandler{
            override fun onSuccess(uid: String) {
                dismissLoading()
                toast(getString(R.string.str_signin_success))
                callMainActivity(this@SignInActivity)
            }

            override fun onError(exception: Exception?) {
                dismissLoading()
                toast(getString(R.string.str_signin_failed))
            }

        })
    }

    private fun callSignUpActivity(){
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    fun reverse(str: String): String{
        val chars: CharArray = str.toCharArray()

        var l = 0
        var h = str.length - 1
        while (l < h){
            val c = chars[l]
            chars[l] = chars[h]
            chars[h] = c
            l++
            h--
        }
        return String(chars)
    }

}