package com.example.restaurantapp.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.restaurantapp.Activity.HomeAdminActivity
import com.example.restaurantapp.R
import kotlinx.android.synthetic.main.fragment_login_admin.*
import kotlinx.android.synthetic.main.fragment_login_admin.view.*
import kotlinx.android.synthetic.main.fragment_login_admin.view.name

/**
 * A simple [Fragment] subclass.
 */
class LoginAdminFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_login_admin, container, false)
        root.btn_login.setOnClickListener {
            if (name.text.toString()=="admin"&&pass.text.toString()=="123456") {
                val i = Intent(activity!!, HomeAdminActivity::class.java)
                startActivity(i)
            }
        }

        return root
    }

}
