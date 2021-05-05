package com.example.youthcare.ui.sportsmanPage

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.youthcare.R
import com.example.youthcare.ui.sportsmanPage.fragments.AllAnalyzFragment
import com.example.youthcare.ui.sportsmanPage.fragments.DeliveryAnalyzFragment
import com.example.youthcare.ui.sportsmanPage.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainPageSportsman : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_page_sportsman)
        var bottom_bar: BottomNavigationView = findViewById(R.id.bottom_navigation)
        val profileFragment = ProfileFragment();
        val deliveryAnalyzFragment = DeliveryAnalyzFragment();
        val allAnalyzFragment = AllAnalyzFragment();
        makeCurrentFragment(profileFragment)

       bottom_bar.setOnNavigationItemSelectedListener {
           when (it.itemId){
               R.id.person -> makeCurrentFragment(profileFragment)
               R.id.analists -> makeCurrentFragment(deliveryAnalyzFragment)
               R.id.all_analisc -> makeCurrentFragment(allAnalyzFragment)
           }
           true
       }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.info_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var bottom_bar: BottomNavigationView = findViewById(R.id.bottom_navigation)
        val id = item.itemId
        if (id == R.id.action_one && bottom_bar.selectedItemId == R.id.person) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Information for you")
            builder.setMessage("It's your profile information. Please look through it.\n" +
                    "If you need to update, take your chance to do it!\n" +
                    "\n" +
                    "Thanks for using YouthCare!")

            builder.setPositiveButton(
                    "OK, Go back") { dialog, id ->
            }
            builder.show()
            return true
        } else if (id == R.id.action_one && bottom_bar.selectedItemId == R.id.analists) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Information for you")
            builder.setMessage("On this page, you may pass the analysis to check your health.\n" +
                    "\n" +
                    "For that:\n" +
                    "1. Choose a doctor, who will receive the result.\n" +
                    "2. Choose the type.\n" +
                    "3. If the type of analysis equals to Temperature, HeartBeat, Breathing Rate or SweetRate, please, pass the result to the Measure field\n" +
                    "4. If the type of analysis equals to BMR or BMI, please, enter the value for Weight and Height in an appropriate way.\n" +
                    "5. Click on the button.\n" +
                    "\n" +
                    "Thanks for using YouthCare!")

            builder.setPositiveButton(
                "OK, Go back") { dialog, id ->
            }
            builder.show()
            return true
        } else if (id == R.id.action_one && bottom_bar.selectedItemId == R.id.all_analisc) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Information for you")
            builder.setMessage("On this page, you may see the analysis result \n" +
                    "that you have been passed. \n" +
                    "\n" +
                    "Thanks for using YouthCare!")

            builder.setPositiveButton(
                    "OK, Go back") { dialog, id ->
            }
            builder.show()
            return true
        }

        return super.onOptionsItemSelected(item)

    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout, fragment)
            commit()
        }
}