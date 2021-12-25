package com.example.restaurantapp.Fragment

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri

import com.example.restaurantapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home_admin.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.fragment_add_restaurant.*
import kotlinx.android.synthetic.main.fragment_add_restaurant.view.*
import kotlinx.android.synthetic.main.fragment_add_restaurant.view.img_add_restaurant
import kotlinx.android.synthetic.main.fragment_add_restaurant.view.restaurant_lat
import kotlinx.android.synthetic.main.fragment_add_restaurant.view.restaurant_lng
import kotlinx.android.synthetic.main.fragment_add_restaurant.view.tvRes
import kotlinx.android.synthetic.main.fragment_add_restaurant.view.tv_address
import java.io.ByteArrayOutputStream

/**
 * A simple [Fragment] subclass.
 */
class AddRestaurantFragment : Fragment() {
    var addCounter=0
    var db : FirebaseFirestore?=null
    private var mAuth: FirebaseAuth? = null
    private var mstore: FirebaseStorage? = null
    private var reference: StorageReference? = null
    private var progressDialog: ProgressDialog? = null
    private var fileUri: Uri?=null
    var path:String?=null
    lateinit var imageURI:Uri
    var root: View? = null
    var id: String? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        root = inflater.inflate(R.layout.fragment_add_restaurant, container, false)
        var restaurantLat = root!!.restaurant_lat.text.toString()
        var restaurantLng = root!!.restaurant_lng.text.toString()
        db = Firebase.firestore
        mstore = Firebase.storage

        reference = mstore!!.reference
        progressDialog = ProgressDialog(activity!!)
        progressDialog!!.setMessage("جاري التحميل")
        progressDialog!!.setCancelable(false)

        /* val i = Intent(activity!!, MapsActivity::class.java)
         i.putExtra("lat", root!!.restaurant_lat.text.toString())
         i.putExtra("lng", root!!.restaurant_lng.text.toString())
         startActivity(i)*/


        root!!.img_add_restaurant.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, IMAGE_PICK_CODE)
        }


        root!!.btn_save_restaurant.setOnClickListener {

            var restaurantName = root!!.tvRes.text.toString()
            var restaurantAddress = root!!.tv_address.text.toString()

            if (restaurantName.isNotEmpty() && restaurantAddress.isNotEmpty()) {
                addRestaurants()
                activity!!.supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.container,
                        RestaurantsAdminFragment()
                    ).addToBackStack(null).commit()

            } else {
                Toast.makeText(activity!!, "Fill Fields", Toast.LENGTH_LONG).show()
            }
        }
        var b = arguments
        if (b != null) {
            id = b.getString("id", "")
            var restaurantName: String? = b.getString("name", "")
            var lng: String? = b.getString("lat", "")
            var lat: String? = b.getString("lng", "")
            var restaurantAddress = b.getString("address", "")
            //var img = b.getString("img", "")

            root!!.tvRes.setText("$restaurantName")
            root!!.tv_address.setText("$restaurantAddress")
            root!!.restaurant_lat.setText(lat)
            root!!.restaurant_lng.setText("$lng")

        }
        root!!.btn_edit_restaurant.setOnClickListener {
            updateRestaurantById(id!!,root!!.tvRes.text.toString(),root!!.tv_address.text.toString(),root!!.restaurant_lat.text.toString().toDouble(),root!!.restaurant_lng.text.toString().toDouble())

        }

        activity!!.toolbar2.title = "Add Restaurant"

        activity!!.toolbar2.setNavigationOnClickListener {
            activity!!.onBackPressed()
        }
        return root!!
    }

    companion object {
        private val IMAGE_PICK_CODE = 1000

    }
    private fun addRestaurants(){
        val db = Firebase.firestore
        val restaurant = hashMapOf("img" to fileUri.toString(), "name" to tvRes.text.toString(),"Address" to tv_address.text.toString(),"lng" to restaurant_lng.text.toString(),  "lat" to restaurant_lat.text.toString().toDouble(),
            "lng" to restaurant_lng.text.toString().toDouble())
        db.collection("Restaurants").add(restaurant)
            .addOnSuccessListener { documentReference ->
                Log.e("msg", "Restaurant added successfully with id ${documentReference.id}")
                addCounter++
                Log.e("msg", " the no of Restaurants = $addCounter")
            }.addOnFailureListener { e ->
                Log.e("msg", "Restaurant added Failed with id ${e.message}")}
    }

    private fun uploadImage(uri:Uri){
        val bitmap = (img_add_restaurant.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val data = baos.toByteArray()
        val childRef=reference!!.child("Restaurants/"+System.currentTimeMillis().toString()+"_images.png")
        var uploadTask = childRef.putBytes(data)
        uploadTask.addOnFailureListener {exe->
            //  Log.e("msg",exe.message())

        }.addOnSuccessListener {
            childRef.downloadUrl.addOnSuccessListener {
                Log.e("msg",uri.toString())
                fileUri=uri

            }
            Log.e("msg","Image Uploaded Successfully")
            Toast.makeText(activity!!,"Image Uploaded Successfully", Toast.LENGTH_LONG).show()


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            imageURI = data!!.data!!
            img_add_restaurant.setImageURI(imageURI)
            uploadImage(imageURI)
            Log.e("e",imageURI.toString())
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun updateRestaurantById(id: String,name:String,address:String,lat:Double,lng:Double) {
        val db = Firebase.firestore
        val restaurants=HashMap<String,Any>()
        restaurants["name"]=name
        restaurants["Address"]=address
        //restaurants["img"]=img
        restaurants["lat"]=lat
        restaurants["lng"]=lng

        db.collection("Restaurants").document(id)
            .update(restaurants)
            .addOnSuccessListener {
                Log.e("msg", "Restaurant updated Successfully")
            }
            .addOnFailureListener { exception ->
                Log.e("msg", exception.message.toString())

            }
    }
}
