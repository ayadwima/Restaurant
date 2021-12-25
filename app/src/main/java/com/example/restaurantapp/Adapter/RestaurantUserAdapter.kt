package com.example.restaurantapp.Adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.Fragment.MealsUserFragment
import com.example.restaurantapp.Model.Restaurant
import com.example.restaurantapp.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home_user.*

import kotlinx.android.synthetic.main.item_restaurant_user.view.*


class RestaurantUserAdapter(var activity: FragmentActivity, var data: MutableList<Restaurant>) :
    RecyclerView.Adapter<RestaurantUserAdapter.MyViewHolder>() {
    val db = Firebase.firestore

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var id: String? = null

        val restaurantName = itemView.restaurant_name!!
        val address = itemView.restaurant_address
        val image = itemView.restaurant_img!!
        var cardResturent = itemView.card_Restaurants
        // val rating = itemView.rating_bar!!
        //  val meals = itemView.meals!!


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(activity).inflate(R.layout.item_restaurant_user, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.restaurantName.text = data[position].name
        holder.address.text = data[position].address

    Picasso.get().load(data[position].img).into(holder.image)

        val id = data[position].id
        holder.id = id




        holder.cardResturent.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(
                    R.id.Container_User,
                    MealsUserFragment()
                ).addToBackStack(null).commit()

            activity!!.nav_view.visibility = View.GONE

        }

        holder.cardResturent.setOnClickListener {
            var b = Bundle()
            b.putString("restaurantName", holder.restaurantName.text.toString())
            var mealsUserFragment = MealsUserFragment()
            mealsUserFragment.arguments = b
            activity!!.supportFragmentManager.beginTransaction()
                .replace(
                    R.id.Container_User,
                    mealsUserFragment
                ).addToBackStack(null).commit()

            activity!!.nav_view.visibility = View.GONE

        }

    }



  }