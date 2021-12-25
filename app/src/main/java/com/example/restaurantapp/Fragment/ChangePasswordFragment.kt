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
class ChangePasswordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_change_password, container, false)


        activity!!.toolbar.title = "Change password"

        activity!!.toolbar.setNavigationOnClickListener {
            activity!!.onBackPressed()
        }


        return root

    }

}
