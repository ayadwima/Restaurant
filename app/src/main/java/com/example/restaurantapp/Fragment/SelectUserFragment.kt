package com.example.restaurantapp.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.restaurantapp.Activity.HomeUserActivity
import com.example.restaurantapp.R
import kotlinx.android.synthetic.main.fragment_select_user.view.*

/**
 * A simple [Fragment] subclass.
 */
class SelectUserFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

      val root=  inflater.inflate(R.layout.fragment_select_user, container, false)

        root.img_chef.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(
                    R.id.mainContainer,
                    LoginAdminFragment()
                ).commit()
        }
        root.img_customer.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(
                    R.id.mainContainer,
                    LoginUserFragment()
                ).commit()
        }
        return  root
    }

}
