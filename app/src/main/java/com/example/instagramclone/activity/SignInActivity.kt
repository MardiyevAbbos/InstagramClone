package com.example.instagramclone.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.instagramclone.R

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
        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)
        val b_sign_in: Button = findViewById(R.id.b_sign_in)
        b_sign_in.setOnClickListener { callMainActivity() }
        val tv_sign_up: TextView = findViewById(R.id.tv_sign_up)
        tv_sign_up.setOnClickListener { callSignUpActivity() }
    }

    private fun callMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun callSignUpActivity(){
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

}