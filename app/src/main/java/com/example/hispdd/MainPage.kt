package com.example.hispdd

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


@Suppress("DEPRECATION")
class MainPage : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page)

        val firstFragment = FirstFragment()
        val secondFragment = SecondFragment()
        val thirdFragment = ThirdFragment()
        val forthFragment = ForthFragment()
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        setCurrentFragment(firstFragment)

        bottomNavigation.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.navigation_home -> setCurrentFragment(firstFragment)
                R.id.navigation_rtc -> setCurrentFragment(secondFragment)
                R.id.navigation_upload -> setCurrentFragment(thirdFragment)
                R.id.navigation_lux -> setCurrentFragment(forthFragment)
            }
            true
        }

    }

        private fun setCurrentFragment(Fragment: Fragment) =
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.flfragments, Fragment)
                commit()
            }

}
