package com.example.instagramclone.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.instagramclone.R

/**
 * In SignUpActivity, user can signUp using fullName, email, password
 */
class SignUpActivity : BaseActivity() {
    val TAG = SignUpActivity::class.java.simpleName
    private lateinit var et_fullname: EditText
    private lateinit var et_password: EditText
    private lateinit var et_email: EditText
    private lateinit var et_cpassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        initViews()

    }

    private fun initViews() {
        et_fullname = findViewById(R.id.et_fullname)
        et_password = findViewById(R.id.et_password)
        et_email = findViewById(R.id.et_email)
        et_cpassword = findViewById(R.id.et_cpassword)

        val b_sign_up: Button = findViewById(R.id.b_sign_up)
        b_sign_up.setOnClickListener { finish() }
        val tv_sign_in: TextView = findViewById(R.id.tv_sign_in)
        tv_sign_in.setOnClickListener { finish() }
    }

}