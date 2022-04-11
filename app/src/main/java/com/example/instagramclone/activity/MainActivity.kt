package com.example.instagramclone.activity

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.example.instagramclone.R
import com.example.instagramclone.adapter.ViewPagerAdapter
import com.example.instagramclone.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * It contains view pager with 5 fragments in MainActivity,
 * and pages can be controlled by BottomNavigationView
 */
class MainActivity : BaseActivity(), HomeFragment.HomeListener ,UploadFragment.UploadListener {
    val TAG = MainActivity::class.java.simpleName
    private var index = 0
    private lateinit var homeFragment: HomeFragment
    private lateinit var uploadFragment: UploadFragment
    private lateinit var viewPager: ViewPager
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setLightStatusBar()

        initViews()

    }

    private fun initViews() {
        viewPager = findViewById(R.id.viewPager)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        // for seem changed BottomNavigationView an ViewPager
        setChangedNavigationAndViewPager()

        // Home and Upload Fragments are global for communication purpose
        homeFragment = HomeFragment()
        uploadFragment = UploadFragment()
        setupViewPager(viewPager)
    }

    private fun setChangedNavigationAndViewPager(){
        bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.navigation_home -> viewPager.currentItem = 0
                R.id.navigation_search -> viewPager.currentItem = 1
                R.id.navigation_upload -> viewPager.currentItem = 2
                R.id.navigation_favorite -> viewPager.currentItem = 3
                R.id.navigation_profile -> viewPager.currentItem = 4
            }
            true
        }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                index = position
                bottomNavigationView.menu.getItem(index).isChecked = true
            }

            override fun onPageScrollStateChanged(state: Int) {}

        })
    }

    private fun setupViewPager(viewPager: ViewPager){
        val adapter = ViewPagerAdapter(supportFragmentManager)
       adapter.addFragment(homeFragment)
       adapter.addFragment(SearchFragment())
       adapter.addFragment(uploadFragment)
       adapter.addFragment(FavoriteFragment())
       adapter.addFragment(ProfileFragment())
       viewPager.adapter = adapter
    }

    override fun scrollToUpload() {
        index = 2
        scrollByIndex(index)
    }

    override fun scrollToHome() {
        index = 0
        scrollByIndex(index)
    }

    private fun scrollByIndex(index: Int){
        viewPager.currentItem = index
        bottomNavigationView.menu.getItem(index).isChecked = true
    }


    private fun setLightStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var flags: Int = this.window.decorView.systemUiVisibility
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR // add LIGHT_STATUS_BAR to flag
            this.window.decorView.systemUiVisibility = flags
            this.window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        }
    }

}

// portreat: https://images.unsplash.com/photo-1649452814966-87450893154a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxlZGl0b3JpYWwtZmVlZHwxN3x8fGVufDB8fHx8&auto=format&fit=crop&w=500&q=60
// landscape: https://images.unsplash.com/photo-1524758631624-e2822e304c36?ixlib=rb-1.2.1&ixid=MnwxMjA3fDF8MHxlZGl0b3JpYWwtZmVlZHw2OXx8fGVufDB8fHx8&auto=format&fit=crop&w=500&q=60
// square: https://images.unsplash.com/photo-1606054534744-a3b13e35c574?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8dHJpcHxlbnwwfDJ8MHx8&auto=format&fit=crop&w=500&q=60