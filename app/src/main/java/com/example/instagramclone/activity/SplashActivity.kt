package com.example.instagramclone.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.example.instagramclone.R


/**
 * In SplashActivity, user can visit to SignInActivity or MainActivity
 */

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {
    val TAG = SplashActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        hideSystemUI()
        setContentView(R.layout.activity_splash)
        initViews()

    }

    private fun initViews() {
        countDownTimer()
    }

    private fun countDownTimer() {
        object : CountDownTimer(2000,1000){
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                callSignInActivity()
            }
        }.start()
    }

    private fun callSignInActivity(){
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
        finish()
    }


    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        // Hide the nav bar and status bar
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // Hide nav bar
                        or View.SYSTEM_UI_FLAG_FULLSCREEN // Hide status bar
                )
    }

    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION // layout Behind nav bar
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // layout Behind status bar
                )
    }


}