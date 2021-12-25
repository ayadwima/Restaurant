package com.example.restaurantapp.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.restaurantapp.Activity.HomeUserActivity

import com.example.restaurantapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_profile_user.*
import kotlinx.android.synthetic.main.fragment_register_user.*
import kotlinx.android.synthetic.main.fragment_register_user.phone
import kotlinx.android.synthetic.main.fragment_register_user.view.*


/**
 * A simple [Fragment] subclass.
 */
class RegisterUserFragment : Fragment() {
    val TAG = "3la"
    lateinit var auth: FirebaseAuth
    var db: FirebaseFirestore? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        auth = Firebase.auth
        db = Firebase.firestore

        val root= inflater.inflate(R.layout.fragment_register_user, container, false)

        root.btn_register.setOnClickListener {

            if (name.text.toString().isEmpty()){
                Toast.makeText(activity!!,"please fill username filed",Toast.LENGTH_LONG).show()
            }else if (email1.text.toString().isEmpty()){
                Toast.makeText(activity!!,"please fill email filed",Toast.LENGTH_LONG).show()
            }else if (password.text.toString().isEmpty()){
                Toast.makeText(activity!!,"please fill password filed",Toast.LENGTH_LONG).show()
            }else{
                createNewAccount(email1.text.toString(), password.text.toString())
            }


        }


        root.txt_login.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(
                    R.id.mainContainer,
                    LoginUserFragment()
                ).commit()
        }
        return root
    }


    private fun createNewAccount(email: String, pass: String) {
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(activity!!) { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "تم التسجيل بنجاح", Toast.LENGTH_SHORT).show()
                val user = auth.currentUser
                Log.e("TestAuthn", "user ${user!!.uid} + ${user.email}")
                addUser(
                    user.uid,
                    name.text.toString(),
                    user.email!!,
                    password.text.toString(),
                    phone.text.toString()


                )
                var i =Intent(activity!!,HomeUserActivity::class.java)
                startActivity(i)
            } else {
                Toast.makeText(context, "فشل في التسجيل", Toast.LENGTH_SHORT).show()

            }
        }
    }
    private fun addUser( id: String, username: String, email: String, password: String , phone :String) {

        val user = hashMapOf("id" to id,"name" to username, "email" to email, "password" to password ,"phone" to phone)

        db!!.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.e(TAG, "User added Successfully with user id ${documentReference.id}")
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, exception.message)
            }
    }


}
