package com.example.restaurantapp.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantapp.Adapter.MealUserAdapter
import com.example.restaurantapp.Model.Meal

import com.example.restaurantapp.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home_user.*
import kotlinx.android.synthetic.main.fragment_my_orders.*

/**
 * A simple [Fragment] subclass.
 */
class OrderFragment : Fragment()  {
    var db: FirebaseFirestore? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        val root =inflater.inflate(R.layout.fragment_my_orders, container, false)
        db = Firebase.firestore
        activity!!.toolbar.title="My Order"
        activity!!.nav_view!!.visibility = View.VISIBLE
        getAllOrder()
        return root
    }
    private fun getAllOrder(): MutableList<Meal> {
        var list = mutableListOf<Meal>()
        db!!.collection("Meals").whereEqualTo("isOrder",1)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        val id = document.id
                        val price = document.data["price"].toString()
                        val desc = document.data["Desc"].toString()
                        val imgMeal = document.data["img"].toString()
                        val name = document.data["name"].toString()
                        val resName = document.data["ResName"].toString()
                        list.add(Meal(id, imgMeal, resName, name, price))

                    }

                    meals_order_rv.layoutManager = LinearLayoutManager(activity!!)
                    meals_order_rv.setHasFixedSize(true)
                    val mealAdapter = MealUserAdapter(activity!!, list)
                    meals_order_rv.adapter = mealAdapter

                }
            }.addOnFailureListener { exception ->
                Log.e("msg", exception.message.toString())

            }

        return list
    }



}
