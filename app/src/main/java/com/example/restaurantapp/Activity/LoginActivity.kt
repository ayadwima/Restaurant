package com.example.restaurantapp.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.restaurantapp.Fragment.SelectUserFragment
import com.example.restaurantapp.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.mainContainer,
                SelectUserFragment()
            ).addToBackStack(null).commit()


    }
}
