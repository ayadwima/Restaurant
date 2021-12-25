package com.example.restaurantapp.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantapp.Adapter.MealAdminAdapter
import com.example.restaurantapp.Adapter.MealUserAdapter
import com.example.restaurantapp.Model.Meal

import com.example.restaurantapp.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home_admin.*
import kotlinx.android.synthetic.main.fragment_meals_admin.*
import kotlinx.android.synthetic.main.fragment_meals_admin.view.*

/**
 * A simple [Fragment] subclass.
 */
class MealsAdminFragment : Fragment()  {
    var root: View? = null
    var db: FirebaseFirestore? = null
    var listsearch: MutableList<Meal> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_meals_admin, container, false)
        db = Firebase.firestore

        activity!!.toolbar2.title = "Meals"

        getAllMeals()

        activity!!.toolbar2.setNavigationOnClickListener {
            activity!!.onBackPressed()
        }

        root.btn_add.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    AddMealsFragment()
                ).addToBackStack(null).commit()
        }
        root!!.txtSearch!!.setOnClickListener {
            getMealsSearch(txtSearch.text.toString())
        }

        return root!!
    }

    private fun getMealsSearch(search: String) {
        db!!.collection("Meals")
            .whereEqualTo("name", search)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        Log.e(
                            "msg",
                            "${document.id} -> ${document.get("name")} -> ${document.get("img")}"
                        )
                        val id = document.id
                        val name = document.data["name"].toString()
                        val imgMeal = document.data["img"].toString()
                        val price = document.data["price"].toString()
                        val resName = document.data["ResName"].toString()
                        listsearch.add(Meal(id, imgMeal, resName, name, price))
                    }
                    Log.e("haneen",listsearch.toString())
                    meals_rv.layoutManager = LinearLayoutManager(activity!!)
                    meals_rv.setHasFixedSize(true)
                    val mealAdapter = MealUserAdapter(activity!!, listsearch)
                    meals_rv.adapter = mealAdapter
                }

            }
    }
    private fun getAllMeals():MutableList<Meal> {
        var list = mutableListOf<Meal>()
        db!!.collection("Meals")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        val id = document.id
                        val price=document.data["price"].toString()
                        val desc =document.data["Desc"].toString()
                        val imgMeal = document.data["img"].toString()
                        val name = document.data["name"].toString()
                        val resName = document.data["ResName"].toString()
                        list.add(Meal(id, imgMeal,resName,name,price))
                    }

               meals_rv.layoutManager = LinearLayoutManager(activity!!)
                 meals_rv.setHasFixedSize(true)
                   val mealAdapter = MealAdminAdapter(activity!!,list)
                    meals_rv.adapter = mealAdapter


                }
            }.addOnFailureListener { exception ->
                Log.e("msg", exception.message.toString())

            }

        return list
    }


}


