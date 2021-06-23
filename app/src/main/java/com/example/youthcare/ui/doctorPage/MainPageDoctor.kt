package com.example.youthcare.ui.doctorPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.youthcare.R
import com.example.youthcare.ui.doctorPage.fragments.AllSportsmansFragment
import com.example.youthcare.ui.doctorPage.fragments.ProfileDoctorFragment
import com.example.youthcare.ui.sportsmanPage.fragments.AllAnalyzFragment
import com.example.youthcare.ui.sportsmanPage.fragments.DeliveryAnalyzFragment
import com.example.youthcare.ui.sportsmanPage.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainPageDoctor : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page_doctor)
        var bottom_bar: BottomNavigationView = findViewById(R.id.bottom_navigation_doctor)
        val profileFragment = ProfileDoctorFragment();
        val allSportsmanFragment = AllSportsmansFragment();
        makeCurrentFragment(profileFragment)

        bottom_bar.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.personDoctor -> makeCurrentFragment(profileFragment)
                R.id.all_sportsmans-> makeCurrentFragment(allSportsmanFragment)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout, fragment)
            commit()
        }
}