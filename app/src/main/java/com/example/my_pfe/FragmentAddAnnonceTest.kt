package com.example.my_pfe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class FragmentAddAnnonceTest : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_annonce_test, container, false)
        val returnToHome = view.findViewById<Button>(R.id.goBackToHome)
        val cancelBtn = view.findViewById<Button>(R.id.cancelButton)
        
        cancelBtn.setOnClickListener {
            // Get the FragmentManager
            val fragmentManager = requireActivity().supportFragmentManager

            // Pop the current fragment and return to the previous one
            fragmentManager.popBackStack()
        }
        returnToHome.setOnClickListener {

            val nomAnnonce = view.findViewById<TextInputLayout>(R.id.annonceNameEditText).editText?.text.toString()
            val departement = view.findViewById<TextInputLayout>(R.id.annonceDepartementEditText).editText?.text.toString()
            val descriptionAnnonce = view.findViewById<TextInputLayout>(R.id.annonceDescriptionEditText).editText?.text.toString()


            if (nomAnnonce.isBlank() || descriptionAnnonce.isBlank() || departement.isBlank()){

                Toast.makeText(requireContext(), "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show()
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
                    "descriptionAnnonce" to descriptionAnnonce
                )
                entrepriseRef.collection("annonces").add(annonceData)
                    .addOnSuccessListener {
                        Toast.makeText(requireContext(), "Annonce ajoutée avec succès", Toast.LENGTH_LONG).show()
                        // Get the FragmentManager
                        val fragmentManager = requireActivity().supportFragmentManager
                        // Pop the current fragment and return to the previous one
                        fragmentManager.popBackStack()
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireContext(), "Erreur lors de l'ajout de l'annonce", Toast.LENGTH_LONG).show()
                    }
                        }
                    }
            }
        }

        return view
    }

      override fun onDetach() {
        super.onDetach()
        (activity as MainActivityEntreprise).enableListeners()
    }

}