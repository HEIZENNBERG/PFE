package com.example.my_pfe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.my_pfe.databinding.ActivityAddEntrepriseAnnounceBinding
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class AddEntrepriseAnnounce : AppCompatActivity() {

    lateinit var addEntrepriseAnnounceBinding: ActivityAddEntrepriseAnnounceBinding
    var deparetement_holder : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addEntrepriseAnnounceBinding = ActivityAddEntrepriseAnnounceBinding.inflate(layoutInflater)
        val view = addEntrepriseAnnounceBinding.root
        setContentView(view)

        // Create an ArrayAdapter to populate the dropdown menu
        val categories = resources.getStringArray(R.array.entreprise_departements)
        val adapter = ArrayAdapter(this, R.layout.list_categorie, categories)
        adapter.setDropDownViewResource(R.layout.list_categorie)

        // Get the dropdown menu view and set the adapter
        addEntrepriseAnnounceBinding.annonceDepartementEditText.setAdapter(adapter)

        // Set an OnItemClickedListener to get the Clicked item
        addEntrepriseAnnounceBinding.annonceDepartementEditText.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position) as String
                // Use the selected item as needed
                deparetement_holder = selectedItem
            }
        }

        addEntrepriseAnnounceBinding.cancelButton.setOnClickListener {
            val intent = Intent(this, MainActivityEntreprise::class.java)
            startActivity(intent)

        }

        addEntrepriseAnnounceBinding.addAnnounce.setOnClickListener {

            val nomAnnonce = view.findViewById<TextInputLayout>(R.id.annonceNameEditText).editText?.text.toString()
            val departement = deparetement_holder
            val descriptionAnnonce = view.findViewById<TextInputLayout>(R.id.annonceDescriptionEditText).editText?.text.toString()


            if (nomAnnonce.isBlank() || descriptionAnnonce.isBlank() || departement.isBlank()){

                Toast.makeText(this , "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show()
            }
            else{
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



                            // Create a new document in the sub-collection "annonces" with the provided data
                            val annonceData = hashMapOf(
                                "nom" to nom,
                                "categorie" to categorie,
                                "email" to email,
                                "description" to description,
                                "numero" to numero,
                                "url" to url,
                                "adress" to adress,
                                "nomAnnonce" to nomAnnonce,
                                "departement" to departement,
                                "descriptionAnnonce" to descriptionAnnonce,
                                "entrepriseId" to entrepriseId,
                                "date" to Date()
                            )
                            entrepriseRef.collection("annonces").add(annonceData)
                                .addOnSuccessListener { documentReference ->
                                    // Store the ID of the created document inside the same document
                                    entrepriseRef.collection("annonces").document(documentReference.id).update("id", documentReference.id)
                                        .addOnSuccessListener {
                                            Toast.makeText(this, "Annonce ajoutée avec succès", Toast.LENGTH_LONG).show()
                                            // Get the FragmentManager
                                            val intent = Intent(this, MainActivityEntreprise::class.java)
                                            startActivity(intent)
                                        }
                                        .addOnFailureListener {
                                            Toast.makeText(this, "Erreur lors de l'ajout de l'annonce", Toast.LENGTH_LONG).show()
                                        }
                                }
                                .addOnFailureListener {
                                    Toast.makeText(this, "Erreur lors de l'ajout de l'annonce", Toast.LENGTH_LONG).show()
                                }
                        }
                    }
            }
        }

    }
}