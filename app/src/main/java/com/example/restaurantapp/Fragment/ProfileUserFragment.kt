package com.example.restaurant.Fragment

import android.app.Activity
import android.app.AlertDialog
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
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.example.restaurantapp.Activity.LoginActivity

import com.example.restaurantapp.Fragment.ChangePasswordFragment
import com.example.restaurantapp.Fragment.EditProfileFragment
import com.example.restaurantapp.Fragment.SettingFragment
import com.example.restaurantapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_home_user.*
import kotlinx.android.synthetic.main.fragment_add_restaurant.*
import kotlinx.android.synthetic.main.fragment_personal_data.view.*
import kotlinx.android.synthetic.main.fragment_profile_user.*
import kotlinx.android.synthetic.main.fragment_profile_user.view.*
import java.io.ByteArrayOutputStream
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class ProfileUserFragment : Fragment(){

    var db : FirebaseFirestore?=null
    private var auth: FirebaseAuth? = null
    private var mstore: FirebaseStorage? = null
    private var reference: StorageReference? = null
    private var pd: ProgressDialog? = null
    var path:String?=null





    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        val root = inflater.inflate(R.layout.fragment_profile_user, container, false)
        db = Firebase.firestore
        auth = Firebase.auth
        mstore = Firebase.storage
        reference = mstore!!.reference
        pd = ProgressDialog(activity!!)
        pd!!.setMessage("Wait .. ")
        pd!!.setCancelable(false)


        activity!!.toolbar.title = "Profile"
        activity!!.nav_view.visibility = View.VISIBLE
        getProfileData()

        root.edit_profile.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(
                    R.id.Container_User,
                    EditProfileFragment()
                ).addToBackStack(null).commit()

            activity!!.nav_view.visibility = View.GONE
        }

        root.change_password.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(
                    R.id.Container_User,
                    ChangePasswordFragment()
                ).addToBackStack(null).commit()

            activity!!.nav_view.visibility = View.GONE
        }

        root.setting.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(
                    R.id.Container_User,
                    SettingFragment()
                ).addToBackStack(null).commit()

            activity!!.nav_view.visibility = View.GONE
        }

        root.logout.setOnClickListener {
            val alertdialog = AlertDialog.Builder(activity)
            alertdialog.setTitle("Log out")
            alertdialog.setMessage("Are you sure?")
            alertdialog.setCancelable(false)
            alertdialog.setNegativeButton("OK") { dialogTnterface, i ->

                val i = Intent(activity!!, LoginActivity::class.java)
                startActivity(i)

            }
            alertdialog.setPositiveButton("CANCEL") { dialogTnterface, i ->
            }


            alertdialog.create().show()
        }


        return root
    }









    fun getProfileData() {
        db!!.collection("users").whereEqualTo("email", auth!!.currentUser!!.email).get()
            .addOnSuccessListener { querySnapshot ->
                Picasso.get().load(querySnapshot.documents.get(0).get("img").toString()).into(img_profile)
                user_name.text = querySnapshot.documents.get(0).get("name").toString()
                email.text = auth!!.currentUser!!.email
                phone.text = querySnapshot.documents.get(0).get("phone").toString()
            }.addOnFailureListener { exception ->

            }
    }
}
