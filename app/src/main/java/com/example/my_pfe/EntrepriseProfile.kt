package com.example.my_pfe

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.example.my_pfe.databinding.ActivityEntrepriseProfileBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EntrepriseProfile : AppCompatActivity() {

    lateinit var entrepriseProfileBinding: ActivityEntrepriseProfileBinding
    var categorie_holder : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        entrepriseProfileBinding = ActivityEntrepriseProfileBinding.inflate(layoutInflater)
        val view = entrepriseProfileBinding.root
        setContentView(view)

        // Create an ArrayAdapter to populate the dropdown menu
        val categories = resources.getStringArray(R.array.entreprise_categories)
        val adapter = ArrayAdapter(this, R.layout.list_categorie, categories)
        adapter.setDropDownViewResource(R.layout.list_categorie)


        // Get the dropdown menu view and set the adapter
        entrepriseProfileBinding.autoCompleteTextView.setAdapter(adapter)


        // Set an OnItemClickedListener to get the Clicked item
        entrepriseProfileBinding.autoCompleteTextView.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position) as String
                // Use the selected item as needed
                categorie_holder = selectedItem
            }
        }

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
                    //val categorie = document.getString("categorie")
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
                    //val categoryTextView = view?.findViewById<TextInputEditText>(R.id.entCategorieProfileEdit)
                   // val test = entrepriseProfileBinding.entCategorieProfileEdit
                    val upEmailTextView = entrepriseProfileBinding.profileEmail
                    val adressTextView = entrepriseProfileBinding.entAdressProfileEdit
                    val nomTextView = entrepriseProfileBinding.entrepriseProfileName
                    val descriptionTextView = entrepriseProfileBinding.entDescriptionProfileEdit
                    //val emailTextView = entrepriseProfileBinding.emailProfileEdit
                    val numeroTextView =  entrepriseProfileBinding.entNTProfileEdit

                    // set text values of TextViews
                    //test.editText?.setText(categorie)
                    upEmailTextView?.text = email
                    descriptionTextView.editText?.setText(description)
                    nomTextView?.text = nom
                    adressTextView.editText?.setText(adress)
                    //emailTextView?.setText("email")
                    numeroTextView.editText?.setText(numero)
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