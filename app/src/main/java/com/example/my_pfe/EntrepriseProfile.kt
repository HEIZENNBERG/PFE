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
import com.google.android.material.textfield.TextInputLayout
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


        val editButton =  view?.findViewById<Button>(R.id.editdButton)
        editButton!!.setOnClickListener {
            val db = Firebase.firestore
            val entrepriseId = FirebaseAuth.getInstance().currentUser?.uid
            val entrepriseRef = db.collection("entreprises").document(entrepriseId!!)


            val adressTextView = view.findViewById<TextInputLayout>(R.id.entAdressProfileEdit)
            val numeroTextView = view.findViewById<TextInputLayout>(R.id.entNTProfileEdit)
            val descriptionTV = view.findViewById<TextInputLayout>(R.id.entDescriptionProfileEdit)

            val adress = adressTextView.editText?.text.toString()
            val numero = numeroTextView.editText?.text.toString()
            val description = descriptionTV.editText?.text.toString()
            entrepriseRef.update(
                mapOf(
                    "categorie" to categorie_holder,
                    "adress" to adress,
                    "numero" to numero,
                    "description" to description
                )
            ).addOnSuccessListener {
                Toast.makeText(this, "Votre data est bien modifier", Toast.LENGTH_LONG).show()
            }.addOnFailureListener { e ->
                Toast.makeText(this, "Un error est occurer !", Toast.LENGTH_LONG).show()
            }
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


                    val upEmailTextView = entrepriseProfileBinding.profileEmail
                    val adressTextView = entrepriseProfileBinding.entAdressProfileEdit
                    val nomTextView = entrepriseProfileBinding.entrepriseProfileName
                    val descriptionTextView = entrepriseProfileBinding.entDescriptionProfileEdit
                    val numeroTextView =  entrepriseProfileBinding.entNTProfileEdit

                    // set text values of TextViews
                    val autoCompleteTextView = findViewById<TextInputLayout>(R.id.categorieEditText)
                    autoCompleteTextView.hint = categorie
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