package com.example.restaurantapp.Fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.restaurantapp.Activity.LoginActivity
import com.example.restaurantapp.R
import kotlinx.android.synthetic.main.activity_home_admin.*
import kotlinx.android.synthetic.main.fragment_personal_data.*
import kotlinx.android.synthetic.main.fragment_personal_data.view.*

/**
 * A simple [Fragment] subclass.
 */
class PersonalDataAdminFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val root=inflater.inflate(R.layout.fragment_personal_data, container, false)


        activity!!.toolbar2.title="Personal Data"

        activity!!.toolbar2.setNavigationOnClickListener {
            activity!!.onBackPressed()
        }


        root.btn_logout_admin.setOnClickListener {
            val alertdialog = AlertDialog.Builder(activity)
            alertdialog.setTitle("Log out")
            alertdialog.setMessage("Are you sure?")
            alertdialog.setCancelable(false)
            alertdialog.setNegativeButton("OK") { dialogTnterface, i ->

                val i = Intent(activity!!, LoginActivity::class.java)
                startActivity(i)

            }
            alertdialog.setPositiveButton("CANCEL") { dialogTnterface, i ->
            }


            alertdialog.create().show()
        }
          return  root

    }

}
