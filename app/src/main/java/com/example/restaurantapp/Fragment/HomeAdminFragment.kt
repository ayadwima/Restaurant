package com.example.restaurantapp.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.restaurantapp.R
import kotlinx.android.synthetic.main.activity_home_admin.*
import kotlinx.android.synthetic.main.fragment_home_admin.view.*

/**
 * A simple [Fragment] subclass.
 */
class HomeAdminFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        // Inflate the layout for this fragment

        val root=inflater.inflate(R.layout.fragment_home_admin, container, false)

        activity!!.toolbar2.title="Restaurants"

       root. card_personal_data.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    PersonalDataAdminFragment()
                ).addToBackStack(null).commit()



        }

        root. card_dashboard.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    StatiisticsFragment()
                ).addToBackStack(null).commit()



        }

        root. card_add_Meals.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    AddMealsFragment()
                ).addToBackStack(null).commit()


        }

        root. card_Meals.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    MealsAdminFragment()
                ).addToBackStack(null).commit()



        }

       root. card_Restaurants.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                   RestaurantsAdminFragment()
                ).addToBackStack(null).commit()



        }


        root. card_add_Restaurants.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                   AddRestaurantFragment()
                ).addToBackStack(null).commit()


        }
        return root
    }

}
