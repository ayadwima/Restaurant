package com.example.restaurantapp.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.restaurantapp.R
import kotlinx.android.synthetic.main.activity_home_admin.*

/**
 * A simple [Fragment] subclass.
 */
class StatiisticsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
           val root =inflater.inflate(R.layout.fragment_statiistics, container, false)

        activity!!.toolbar2.title="Statistics"

        activity!!.toolbar2.setNavigationOnClickListener {
            activity!!.onBackPressed()
        }
        return  root
    }

}
