package com.example.my_pfe.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.Navigation.findNavController
import com.bumptech.glide.Glide
import com.example.my_pfe.AnnonceItem
import com.example.my_pfe.Demande
import com.example.my_pfe.Entreprise
import com.example.my_pfe.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

import java.io.IOException
import java.util.*


class AnnonceInfosFragment : Fragment() {

    private lateinit var annonce: AnnonceItem
    lateinit var typeStage : TextInputLayout
    lateinit var importcv : Button
    lateinit var submit : Button
    private lateinit var storage : StorageReference
    lateinit var demande : Demande
    private lateinit var firestore: FirebaseFirestore

    private var uri: Uri? = null // Declare uri variable outside of lambda

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        annonce = arguments?.getParcelable("annonce") ?: AnnonceItem()
        // Get the URI of the image from the Entreprise object
        val imageUrl = annonce.url

        val imageView = view.findViewById<ImageView>(R.id.entrepriseLogo)



        Glide.with(this)
            .load(imageUrl)
            .into(imageView)

        // Now you can use the entreprise object to display the additional information in your UI

        view.findViewById<TextView>(R.id.annonceTitle).text = annonce.nomAnnonce
        view.findViewById<TextView>(R.id.entrepriseName2).text = annonce.nom
        view.findViewById<TextView>(R.id.annonceDescription).text = annonce.descriptionAnnonce
        view.findViewById<TextView>(R.id.entrepriseDescrition).text = annonce.email
        view.findViewById<TextView>(R.id.annonceDepartement).text = annonce.departement

    }

    @SuppressLint("Recycle")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_annonce_infos, container, false)
        view.setOnTouchListener { _, _ -> true }

        typeStage = view.findViewById(R.id.typeStageEditText)
        importcv = view.findViewById(R.id.importCV)
        submit = view.findViewById(R.id.submitDemende)



        val selectFileLauncher = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri ->
            if (uri != null) {
                try {

                    this.uri = uri
                    val inputStream = requireActivity().contentResolver.openInputStream(uri)
                    // Read the contents of the file using the input stream
                    inputStream?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        // importcv = view.findViewById<Button>(R.id.selectFileButton)
        importcv.setOnClickListener { selectFileLauncher.launch("application/pdf") } // you can set specific mime types like "application/pdf"

        submit.setOnClickListener {

            val userId = FirebaseAuth.getInstance().currentUser?.uid
            val db = FirebaseFirestore.getInstance()
            val studentRef = db.collection("students").document(userId!!)

            studentRef.get().addOnSuccessListener { document ->
                if (typeStage.editText?.text.toString().isBlank()){
                    Toast.makeText(context, "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show()
                }
                else{
                    if (uri == null) {
                        Toast.makeText(context, "Veuillez sélectionner un fichier PDF", Toast.LENGTH_LONG).show()
                    } else if (document.exists()) {
                        val nom = document.getString("nom")
                        val prenom = document.getString("prenom")

                        // Get a reference to the Firebase Storage instance
                        val storage = Firebase.storage
                        // Create a reference to the CVs folder and the file with the user ID as the name
                        val storageRef = storage.reference.child("CVs/${userId}.pdf")
                        // Get the InputStream of the selected file
                        val inputStream = requireActivity().contentResolver.openInputStream(uri!!)
                        // Read the contents of the file into a byte array
                        val bytes = inputStream?.readBytes()
                        // Upload the file to Firebase Storage
                        val uploadTask = storageRef.putBytes(bytes!!)
                        uploadTask.addOnSuccessListener {
                            // Get the download URL of the uploaded file
                            storageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                                // Create a new demande document in the demandes subcollection
                                val demandesRef = db.collection("entreprises")
                                    .document(annonce.entrepriseId.toString())
                                    .collection("annonces")
                                    .document(annonce.id.toString())
                                    .collection("demandes").document(userId)
                                var demande = Demande(
                                    nom.toString(),
                                    prenom.toString(),
                                    typeStage.editText?.text.toString(),
                                    downloadUri.toString(),
                                    "pas de reponce",
                                    Date(),
                                    annonce.id.toString(),
                                    userId
                                )
                                try {
                                    demandesRef.set(demande)
                                    // Get the FragmentManager
                                    val fragmentManager = parentFragmentManager

                                    // Pop the current fragment and return to the previous one
                                    fragmentManager.popBackStack()

                                    Toast.makeText(
                                        context,
                                        "Votre demande est bien envoyée",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } catch (e: java.lang.Exception) {
                                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                                }
                            }
                        }.addOnFailureListener {
                            Toast.makeText(
                                context,
                                "Échec de l'envoi du fichier : ${it.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }

    return view
    }



}