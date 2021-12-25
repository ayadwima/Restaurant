package com.example.restaurantapp.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantapp.Adapter.RestaurantAdminAdapter
import com.example.restaurantapp.Model.Restaurant

import com.example.restaurantapp.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home_admin.*
import kotlinx.android.synthetic.main.activity_home_user.*
import kotlinx.android.synthetic.main.fragment_restaurants_admin.*
import kotlinx.android.synthetic.main.fragment_restaurants_admin.view.*
import kotlinx.android.synthetic.main.fragment_restaurants_admin.view.txtSearch

/**
 * A simple [Fragment] subclass.
 */
class RestaurantsAdminFragment : Fragment() {
    var root: View? = null
    var db: FirebaseFirestore? = null
    var listsearch: MutableList<Restaurant> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        db = Firebase.firestore

        // Inflate the layout for this fragment
       root = inflater.inflate(R.layout.fragment_restaurants_admin, container, false)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        activity!!.toolbar2.title = "Restaurants"

        activity!!.toolbar2.setNavigationOnClickListener {
            activity!!.onBackPressed()
        }
        getAllRestaurants()

        root!!.txtSearch!!.setOnClickListener {
            getMealsSearch(txtSearch.text.toString())
        }
        return  root!!
    }


    private fun getAllRestaurants():MutableList<Restaurant> {
        var list = mutableListOf<Restaurant>()
        db!!.collection("Restaurants")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        val id = document.id
                       val address = document.data["Address"].toString()
                        val img = document.data["img"].toString()
                        val name = document.data["name"].toString()
                        list.add(Restaurant(id, img, name, address))
                    }


                    root!!.restaurant_rv.layoutManager = LinearLayoutManager(activity)
                    root!!.restaurant_rv.setHasFixedSize(true)
                    val restaurantAdapter = RestaurantAdminAdapter(activity!!, list)
                    root!!.restaurant_rv.adapter = restaurantAdapter
                }
            }.addOnFailureListener { exception ->
                Log.e("msg", exception.message.toString())

            }

        return list
    }

    private fun getMealsSearch(search: String) {
        db!!.collection("Restaurants")
            .whereEqualTo("name", search)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        val id = document.id
                        val address = document.data["Address"].toString()
                        val img = document.data["img"].toString()
                        val name = document.data["name"].toString()
                        listsearch.add(Restaurant(id, img, name, address))
                    }


                    root!!.restaurant_rv.layoutManager = LinearLayoutManager(activity)
                    root!!.restaurant_rv.setHasFixedSize(true)
                    val restaurantAdapter = RestaurantAdminAdapter(activity!!, listsearch)
                    root!!.restaurant_rv.adapter = restaurantAdapter
                }
            }.addOnFailureListener { exception ->
                Log.e("msg", exception.message.toString())

            }
    }

}
