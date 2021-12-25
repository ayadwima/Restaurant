package com.example.restaurantapp.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.restaurantapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_updat_restaurante.view.*

/**
 * A simple [Fragment] subclass.
 */
class updateRestaurantFragment : Fragment() {


    lateinit var auth: FirebaseAuth
    var db: FirebaseFirestore? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        auth = Firebase.auth
        db = Firebase.firestore

        val root = inflater.inflate(R.layout.fragment_updat_restaurante, container, false)


        root.btn_update_restaurant.setOnClickListener {
          //  updatRestaurant()
        }
        return root
    }

    private fun updatRestaurant(
        id: String,
        image: String,
        name: String,
        address: Double,
        lat: String,
        lng: String) {

        val product = HashMap<String, Any>()
        product["image"] = image
        product["name"] = name
        product["Address"] = address
        product["lat"] = lat
        product["lng"] = lng
        db!!.collection("Restaurants").document(id)
            .update(product)
            .addOnSuccessListener { querySnapshot ->
                Log.e("update", "update done")
            }.addOnFailureListener { exception ->
                Log.e("update", "update failed")
            }


    }
}