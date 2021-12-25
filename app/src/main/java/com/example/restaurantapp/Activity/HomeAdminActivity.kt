package com.example.restaurantapp.Activity


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.restaurantapp.Fragment.HomeAdminFragment

import com.example.restaurantapp.R
import kotlinx.android.synthetic.main.activity_home_admin.*


class HomeAdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_admin)


        setSupportActionBar(toolbar2)


        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container,
                HomeAdminFragment()
            ).commit()



    }


    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}
