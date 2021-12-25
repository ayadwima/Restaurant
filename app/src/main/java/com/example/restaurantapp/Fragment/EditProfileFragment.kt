package com.example.restaurantapp.Fragment

import android.app.Activity
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
import com.example.restaurant.Fragment.ProfileUserFragment

import com.example.restaurantapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home_user.*
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import kotlinx.android.synthetic.main.fragment_edit_profile.user_name
import kotlinx.android.synthetic.main.fragment_edit_profile.view.*
import kotlinx.android.synthetic.main.fragment_profile_user.*
import kotlinx.android.synthetic.main.fragment_profile_user.view.*
import kotlinx.android.synthetic.main.fragment_profile_user.view.img_profile
import java.io.ByteArrayOutputStream



/**
 * A simple [Fragment] subclass.
 */
class EditProfileFragment : Fragment() {
    lateinit var auth: FirebaseAuth
    var db: FirebaseFirestore? = null
    private var mstore: FirebaseStorage? = null
    private var reference: StorageReference? = null
    private var fileUri: Uri?=null
    lateinit var imageURI:Uri
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        auth = Firebase.auth
        val user=auth.currentUser
        db = Firebase.firestore

        mstore = Firebase.storage
        reference = mstore!!.reference
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        // Inflate the layout for this fragment
        val root =inflater.inflate(R.layout.fragment_edit_profile, container, false)

        activity!!.toolbar.title = "Edit Profile"
        getProfileData()
        activity!!.toolbar.setNavigationOnClickListener {
            activity!!.onBackPressed()
        }
        root.img_user.setOnClickListener {
            val photoPickerIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent,IMAGE_PICK_CODE)
        }

        root.btn_save.setOnClickListener {
//            updateUserById(user!!.uid,user_name.text.toString(),user_phone.text.toString(),user_email.text.toString())
            updateData(user_name.text.toString(),user_phone.text.toString())
            activity!!.supportFragmentManager.beginTransaction()
                .replace(
                    R.id.Container_User,
                    ProfileUserFragment()
                ).addToBackStack(null).commit()
        }
        return root
    }

    fun getProfileData() {
        db!!.collection("users").whereEqualTo("email", auth.currentUser!!.email).get()
            .addOnSuccessListener { querySnapshot ->
             Picasso.get().load(querySnapshot.documents.get(0).get("img").toString()).into(img_user)
                user_name.setText(querySnapshot.documents.get(0).get("name").toString())
                user_email.setText(auth.currentUser!!.email)
                user_phone.setText(querySnapshot.documents.get(0).get("phone").toString())
            }.addOnFailureListener { exception ->

            }
    }
    private fun uploadImage(uri: Uri?){
        val bitmap = (img_user.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val data = baos.toByteArray()
        val childRef=reference!!.child("Profile/"+System.currentTimeMillis().toString()+"_image.png")
        var uploadTask = childRef.putBytes(data)
        uploadTask.addOnFailureListener {exe->
            Log.e("msg",exe.message)

        }.addOnSuccessListener {
            childRef.downloadUrl.addOnSuccessListener {uri ->
                Log.e("msg",uri.toString())
                fileUri=uri
                updateImage(fileUri.toString())
//            Picasso.get().load(fileUri.toString()).into(img_user)


            }
            Log.e("msg","ProfileImage Uploaded Successfully")
            Toast.makeText(activity!!,"ProfileImage Uploaded Successfully", Toast.LENGTH_LONG).show()


        }
    }

    private fun updateImage(path: String) {
        db!!.collection("users").whereEqualTo("email",auth.currentUser!!.email).get()
            .addOnSuccessListener { querySnapshot ->
                db!!.collection("users").document(querySnapshot.documents.get(0).id)
                    .update("img",path)

            }
    }

    private fun updateData(name:String,phone: String) {
        db!!.collection("users").whereEqualTo("email",auth.currentUser!!.email).get()
            .addOnSuccessListener { querySnapshot ->
                db!!.collection("users").document(querySnapshot.documents.get(0).id)
                    .update("name",name,"phone",phone,"email",auth.currentUser!!.email)

            }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            imageURI = data!!.data!!
            img_user.setImageURI(imageURI)
            uploadImage(imageURI)
            Log.e("e",imageURI.toString())
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun updateUserById(id:String,name:String,phone:String,email:String) {
        val  user =HashMap<String,Any>()
        user["name"]=name
        user["email"]=email
        user["phone"]=phone

        db!!.collection("users").document(email)
            .update(user)
            .addOnSuccessListener {
                Log.e("msg", "user updated Successfully")
            }
            .addOnFailureListener { exception ->
                Log.e("msg", exception.message.toString())

            }

    }

    companion object {
        private val IMAGE_PICK_CODE = 2000

    }

}
