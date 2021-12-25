package com.example.restaurantapp.Fragment

import android.R.attr.name
import android.R.attr.password
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.restaurantapp.Activity.HomeUserActivity
import com.example.restaurantapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_login_user.*
import kotlinx.android.synthetic.main.fragment_login_user.view.*
import kotlinx.android.synthetic.main.fragment_profile_user.*


/**
 * A simple [Fragment] subclass.
 */
class LoginUserFragment : Fragment() {
    lateinit var auth: FirebaseAuth
    var db: FirebaseFirestore? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        auth = Firebase.auth
        db = Firebase.firestore

        val root = inflater.inflate(R.layout.fragment_login_user, container, false)
        root.txt_Register.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(
                    R.id.mainContainer,
                    RegisterUserFragment()
                ).commit()
        }

        root.btn_login.setOnClickListener {
            LogInAccount(ed_email.text.toString(), ed_pass.text.toString())

        }
        return root
    }


    private fun LogInAccount(email: String, pass: String) {
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(activity!!) { task ->
            if (task.isSuccessful) {
                Toast.makeText(activity!!, "log in  successful", Toast.LENGTH_SHORT).show()
                val user = auth.currentUser
                Log.e("log in", "user ${user!!.uid} + ${user.email}")
                val i = Intent(activity!!, HomeUserActivity::class.java)
                startActivity(i)

            } else {
                Toast.makeText(activity!!, "Login failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}





