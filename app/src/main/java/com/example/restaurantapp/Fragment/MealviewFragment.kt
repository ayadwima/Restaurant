package com.example.restaurantapp.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.restaurantapp.R
import kotlinx.android.synthetic.main.activity_home_user.*

/**
 * A simple [Fragment] subclass.
 */
class MealviewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val root = inflater.inflate(R.layout.fragment_mealview, container, false)


        activity!!.toolbar.title="Meal Details"
        activity!!.toolbar.setNavigationOnClickListener {
            activity!!.onBackPressed()
        }

        return root
    }

}
