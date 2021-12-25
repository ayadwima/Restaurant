package com.example.restaurantapp.Adapter

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.Activity.MapsActivity
import com.example.restaurantapp.Fragment.AddRestaurantFragment
import com.example.restaurantapp.Fragment.updateRestaurantFragment
import com.example.restaurantapp.Model.Restaurant
import com.example.restaurantapp.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_restaurant_admin.view.*

class RestaurantAdminAdapter (var activity: FragmentActivity, var data: MutableList<Restaurant>) :
    RecyclerView.Adapter<RestaurantAdminAdapter.MyViewHolder>() {
    val db = Firebase.firestore
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var id: String? = null
        val restaurantName= itemView.restaurant_name!!
        val address = itemView.restaurant_address
        val image= itemView.restaurant_img!!
        var restaurantLocation = itemView.restaurant_location_ic
        var resDelete=itemView.ic_delete
        var resEdit=itemView.ic_edit




    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(activity).inflate(R.layout.item_restaurant_admin, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val id = data[position].id
        holder.id=id
        holder.restaurantName.text=data[position].name
        holder.address.text=data[position].address
        Picasso.get().load(data[position].img).into(holder.image)
//        db.collection("Restaurants").get()
//            .addOnSuccessListener { querySnapshot ->
//                Picasso.get().load(querySnapshot.documents.get(0).get("img").toString()).into(holder.image)
//
//            }.addOnFailureListener { exception ->
//
//            }


        holder.restaurantLocation.setOnClickListener {
            val i = Intent(activity, MapsActivity::class.java)
            activity.startActivity(i)
        }

        holder.resDelete.setOnClickListener {
            var alert = AlertDialog.Builder(activity!!)

            alert.setTitle("Delete")
            alert.setIcon(R.drawable.ic_delete_black_24dp)
            alert.setMessage("Are you sure?")
            alert.setCancelable(false)

            alert.setPositiveButton("YES") { _, _ ->
                deleteRestaurantById(id)
                data.remove(data[position])
                notifyDataSetChanged()
            }
            alert.setNegativeButton("NO") { dialogInterface, i ->
            }
            alert.create().show()
            true
        }


        holder.resEdit.setOnClickListener {

            var b = Bundle()
            b.putString("id", id)
            b.putString("lat", "22.2")
            b.putString("lng", "33.3")
            b.putString("name", holder.restaurantName.text.toString())
            b.putString("address", holder.address.text.toString())


            //b.putString("img", holder.image.toString())
            var editFragment = AddRestaurantFragment()
            editFragment.arguments = b
            activity!!.supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    editFragment
                ).addToBackStack(null).commit()
        }




    }

    private fun deleteRestaurantById(id: String) {

        db.collection("Restaurants").document(id)
            .delete()
            .addOnSuccessListener {
                Log.e("msg", "Restaurant deleted Successfully")
            }
            .addOnFailureListener { exception ->
                Log.e("msg", exception.message.toString())

            }
    }


}