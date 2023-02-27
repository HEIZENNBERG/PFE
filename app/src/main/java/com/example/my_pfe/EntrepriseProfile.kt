package com.example.my_pfe

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.my_pfe.databinding.ActivityEntrepriseProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EntrepriseProfile : AppCompatActivity() {

    lateinit var entrepriseProfileBinding: ActivityEntrepriseProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        entrepriseProfileBinding = ActivityEntrepriseProfileBinding.inflate(layoutInflater)
        val view = entrepriseProfileBinding.root
        setContentView(view)

        entrepriseProfileBinding.backHome.setOnClickListener {
            val intent = Intent(this, MainActivityEntreprise::class.java)
            startActivity(intent)
            finish()
        }
        displayEntrepriseInfo(view)
    }




    private fun displayEntrepriseInfo(view: View) {
        val db = Firebase.firestore
        val entrepriseId = FirebaseAuth.getInstance().currentUser?.uid
        val entrepriseRef = db.collection("entreprises").document(entrepriseId!!)

        entrepriseRef.get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {

                    val nom = document.getString("nom")
                    val categorie = document.getString("categorie")
                    val email = document.getString("email")
                    val description = document.getString("description")
                    val numero = document.getString("numero")
                    val url = document.getString("url")
                    val adress = document.getString("adress")

                    val imageUrl = url

                    val imageView = view.findViewById<ImageView>(R.id.entrepriseProfileimage)

                    Glide.with(this)
                        .load(imageUrl)
                        .into(imageView)

                    // get references to TextViews
                    val categoryTextView = view?.findViewById<TextView>(R.id.entCategorieProfileEdit)
                    val upEmailTextView = view?.findViewById<TextView>(R.id.profileEmail)
                    val adressTextView = view?.findViewById<TextView>(R.id.entAdressProfileEdit)
                    val nomTextView =  view?.findViewById<TextView>(R.id.entrepriseProfileName)
                    val descriptionTextView =  view?.findViewById<TextView>(R.id.entDescriptionProfileEdit)
                    val emailTextView =  view?.findViewById<TextView>(R.id.emailProfileEdit)
                    val numeroTextView =  view?.findViewById<TextView>(R.id.phoneNumProfileEdit)

                    // set text values of TextViews
                    categoryTextView?.text = categorie
                    upEmailTextView?.text = email
                    descriptionTextView?.text = description
                    nomTextView?.text = nom
                    adressTextView?.text = adress
                    emailTextView?.text = email
                    numeroTextView?.text = numero
                } else {
                    // document not found
                    Log.d(ContentValues.TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                // handle errors
                Log.d(ContentValues.TAG, "get failed with ", exception)
            }
    }
}