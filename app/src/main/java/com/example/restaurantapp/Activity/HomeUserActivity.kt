package com.example.restaurantapp.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.restaurant.Fragment.ProfileUserFragment

import com.example.restaurantapp.Fragment.OrderFragment
import com.example.restaurantapp.Fragment.RestaurantsUserFragment
import com.example.restaurantapp.R
import kotlinx.android.synthetic.main.activity_home_user.*

class HomeUserActivity : AppCompatActivity() {


    companion object {
        private const val ID_HOME = 1
        private const val ID_PROFILE = 2
        private const val ID_ORDER = 3

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home_user)



        setSupportActionBar(toolbar)
        toolbar.title="Restaurants"




        nav_view.add(MeowBottomNavigation.Model(ID_HOME, R.drawable.ic_restaurant))
        nav_view.add(MeowBottomNavigation.Model(ID_ORDER, R.drawable.ic_order))
        nav_view.add(MeowBottomNavigation.Model(ID_PROFILE, R.drawable.profile))


        nav_view.show(ID_HOME)
        replaceFragment(RestaurantsUserFragment())

        nav_view.setOnClickMenuListener {

            when (it.id) {
                ID_HOME -> replaceFragment(RestaurantsUserFragment())
                ID_ORDER -> replaceFragment(OrderFragment())
                ID_PROFILE -> replaceFragment(ProfileUserFragment())

                else -> ""
            }
        }
               }


    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(
            R.id.Container_User,
            fragment
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


