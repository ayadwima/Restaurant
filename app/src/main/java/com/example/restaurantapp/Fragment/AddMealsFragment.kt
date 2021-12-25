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
import com.example.restaurantapp.Activity.HomeAdminActivity
import com.example.restaurantapp.MyNotificationManager
import com.example.restaurantapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_home_admin.*
import kotlinx.android.synthetic.main.activity_home_user.*
import kotlinx.android.synthetic.main.fragment_add_meals.*
import kotlinx.android.synthetic.main.fragment_add_meals.view.*
import kotlinx.android.synthetic.main.fragment_add_meals.view.img_add_meal
import kotlinx.android.synthetic.main.fragment_add_meals.view.tvRes
import kotlinx.android.synthetic.main.fragment_add_meals.view.tv_meal
import kotlinx.android.synthetic.main.fragment_add_meals.view.tv_price
import kotlinx.android.synthetic.main.fragment_add_restaurant.view.*
import java.io.ByteArrayOutputStream

/**
 * A simple [Fragment] subclass.
 */
class AddMealsFragment : Fragment() {
    var addCounter=0
    var db : FirebaseFirestore?=null
    private var mAuth: FirebaseAuth? = null
    private var mstore: FirebaseStorage? = null
    private var reference: StorageReference? = null
    private var progressDialog: ProgressDialog? = null
    private var fileUri: Uri?=null
    var path:String?=null
    lateinit var imageURI:Uri
    var id: String? = null
   // val myNotificationManager = MyNotificationManager(activity!!)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val root = inflater.inflate(R.layout.fragment_add_meals, container, false)
        db = Firebase.firestore
        mstore = Firebase.storage
        reference = mstore!!.reference
        progressDialog = ProgressDialog(activity!!)
        progressDialog!!.setMessage("جاري التحميل")
        progressDialog!!.setCancelable(true)



        root.img_add_meal.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, IMAGE_PICK_CODE)
        }



        root.btn_save_meal.setOnClickListener {

            var mealName = root.tv_meal.text.toString()
            var price = root.tv_price.text.toString()

            if (mealName.isNotEmpty() && price.isNotEmpty()) {
                addMeals()
                uploadImage(imageURI)
                progressDialog!!.show()
                activity!!.supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.container,
                        MealsAdminFragment()
                    ).addToBackStack(null).commit()
            } else {
                Toast.makeText(activity!!, "Fill Fields", Toast.LENGTH_LONG).show()
            }
        }

        var b = arguments
        if (b != null) {
            id = b.getString("id", "")
            var restaurantName: String? = b.getString("nameRest", "")
            var cost= b.getString("cost", "")
          var name=  b.getString("nameMeal","")
            //var img = b.getString("img", "")
 var desc= b.getString("desc","")
            root!!.tvRes.setText("$restaurantName")
            root!!.tv_meal.setText(name)
            root!!.tv_price.setText(cost)
            root!!.tv_desc.setText(desc)


        }
        root!!.btn_edit_meal.setOnClickListener {
            updateMealById(id!!,root!!.tvRes.text.toString(),root!!.tv_meal.text.toString(),root.tv_price.text.toString(),root!!.tv_desc.text.toString())
            activity!!.supportFragmentManager.beginTransaction()
                .replace(
                    R.id.container,
                    MealsAdminFragment()
                ).addToBackStack(null).commit()
        }

        activity!!.toolbar2.title = "Add Meal"

        activity!!.toolbar2.setNavigationOnClickListener {
            activity!!.onBackPressed()
        }
        return root
    }

    companion object {
        private val IMAGE_PICK_CODE = 1000

    }
    private fun addMeals(){
        val db = Firebase.firestore
        val meal = hashMapOf("img" to imageURI.toString(),  "name" to tv_meal.text.toString(),"ResName" to tvRes.text.toString(),"price" to tv_price.text.toString(),"Desc" to tv_desc.text.toString()
        )
        db.collection("Meals").add(meal)
            .addOnSuccessListener { documentReference ->
                Log.e("msg", "Meal added successfully with id ${documentReference.id}")
                addCounter++
                Log.e("msg", " the no of Meals = $addCounter")
               // myNotificationManager.showNotification(11,"Restaurant App","Meal added successfully",Intent(activity!!, activity!!::class.java))
            }.addOnFailureListener { e ->
                Log.e("msg", "Meal added Failed with id ${e.message}")}
        uploadImage(imageURI)
    }
    private fun uploadImage(uri: Uri?){
        val bitmap = (img_add_meal.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val data = baos.toByteArray()
        val childRef=reference!!.child("Meals/"+System.currentTimeMillis().toString()+"_image.png")
        var uploadTask = childRef.putBytes(data)
        uploadTask.addOnFailureListener {exe->
            Log.e("msg",exe.message)

        }.addOnSuccessListener {
            childRef.downloadUrl.addOnSuccessListener {uri ->
                Log.e("msg",uri.toString())
                fileUri=uri


            }
            Log.e("msg","Image Uploaded Successfully")
//            Toast.makeText(activity!!,"Image Uploaded Successfully", Toast.LENGTH_LONG).show()


        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            imageURI = data!!.data!!
            img_add_meal.setImageURI(imageURI)
            uploadImage(imageURI)
            Log.e("e",imageURI.toString())
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun updateMealById(id: String,nameRes:String,nameMeal:String,cost:String,desc:String) {
        val db = Firebase.firestore
        val meal=HashMap<String,Any>()
        meal["ResName"]=nameRes
        meal["name"]=nameMeal
        //restaurants["img"]=img
        meal["price"]=cost
        meal["Desc"]=desc

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
