package com.example.restaurantapp.Adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantapp.Fragment.MealviewFragment
import com.example.restaurantapp.Fragment.OrderFragment
import com.example.restaurantapp.Model.Meal
import com.example.restaurantapp.MyNotificationManager
import com.example.restaurantapp.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home_user.*
import kotlinx.android.synthetic.main.item_meals_user.view.*


class MealUserAdapter (var activity: FragmentActivity, var data: MutableList<Meal>) :
    RecyclerView.Adapter<MealUserAdapter.MyViewHolder>() {
    val db = Firebase.firestore
  //  val myNotificationManager = MyNotificationManager(activity!!)

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img=itemView.meal_img
        var card = itemView.card_meal
        val nameRest = itemView.name_rest
        var mealName=itemView.name_meal
        var no=itemView.no_meals
        var cost=itemView.cost_meals
        var id: String? = null

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(activity).inflate(R.layout.item_meals_user, parent, false)

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
        holder.card.setOnLongClickListener {
            var alert = AlertDialog.Builder(activity!!)
            alert.setTitle("Add Order")
            alert.setIcon(R.drawable.ic_shopping_cart_black_24dp)
            alert.setMessage("Are you sure?")
            alert.setCancelable(false)

            alert.setPositiveButton("YES") { _, _ ->
                addOrder(id)
                activity!!.supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.Container_User,
                        OrderFragment()
                    ).addToBackStack(null).commit()
               // myNotificationManager.showNotification(11,"Restaurant App","Order added successfully",
               //     Intent(activity!!, activity!!::class.java))
                activity!!.nav_view.visibility = View.VISIBLE
            }
            alert.setNegativeButton("NO") { dialogInterface, i ->
            }
            alert.create().show()
            true
        }
holder.card.setOnClickListener {
    activity!!.supportFragmentManager.beginTransaction()
        .replace(
            R.id.Container_User,
            MealviewFragment()
        ).addToBackStack(null).commit()
    activity!!.nav_view!!.visibility = View.GONE
}
    }




    private fun addOrder(id: String) {
        val db = Firebase.firestore
        val meal = HashMap<String, Any>()
        meal["isOrder"] = 1
        db.collection("Meals").document(id)
            .update(meal)
            .addOnSuccessListener {
                Log.e("msg", "meal updated Successfully")
            }
            .addOnFailureListener { exception ->
                Log.e("msg", exception.message.toString())

            }
    }

}
