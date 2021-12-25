package com.example.restaurantapp.Adapter

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.Fragment.AddMealsFragment
import com.example.restaurantapp.Model.Meal
import com.example.restaurantapp.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_add_meals.view.*
import kotlinx.android.synthetic.main.item_meal_admin.view.*
import kotlinx.android.synthetic.main.item_meal_admin.view.ic_delete
import kotlinx.android.synthetic.main.item_meal_admin.view.ic_edit
import kotlinx.android.synthetic.main.item_restaurant_admin.view.*

class MealAdminAdapter(var activity: FragmentActivity, var data: MutableList<Meal>) :
    RecyclerView.Adapter<MealAdminAdapter.MyViewHolder>() {
    val db = Firebase.firestore
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img=itemView.meal_img
        var resDelete=itemView.ic_delete
        var resEdit=itemView.ic_edit
        val nameRest = itemView.name_rest
        var mealName=itemView.name_meal
        var cost=itemView.cost_meals
        var desc=itemView.tv_desc
        var id: String? = null

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(activity).inflate(R.layout.item_meal_admin, parent, false)

        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val id = data[position].id
        holder.id=id
        holder.mealName.text=data[position].name
//            img.setImageResource(data.img)
        holder.nameRest.text=data[position].restName
        holder.cost.text=data[position].cost

       Picasso.get().load(data[position].img).into(holder.img)
//        db.collection("Meals").get()
//            .addOnSuccessListener { querySnapshot ->
//                Picasso.get().load(querySnapshot.documents.get(0).get("img").toString()).into(holder.img)
//
//            }.addOnFailureListener { exception ->
//
//            }

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
            b.putString("cost",  holder.cost.text.toString())
            b.putString("nameRest", holder.nameRest.text.toString())
            b.putString("nameMeal", holder.mealName.text.toString())
            b.putString("desc","Meal is Great")


            //b.putString("img", holder.image.toString())
            var editFragment = AddMealsFragment()
            editFragment.arguments = b
            activity.supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    editFragment
                ).addToBackStack(null).commit()
        }
    }
    private fun deleteRestaurantById(id: String) {

        db.collection("Meals").document(id)
            .delete()
            .addOnSuccessListener {
                Log.e("msg", "Meal deleted Successfully")
            }
            .addOnFailureListener { exception ->
                Log.e("msg", exception.message.toString())

            }
    }

}