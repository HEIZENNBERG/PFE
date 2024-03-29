package com.example.my_pfe.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.my_pfe.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        displayStudentInfo()
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //edit profil here hhh
        val editButton =  view?.findViewById<Button>(R.id.editdButton)
        editButton!!.setOnClickListener {
            val db = Firebase.firestore
            val studentId = FirebaseAuth.getInstance().currentUser?.uid
            val studentRef = db.collection("students").document(studentId!!)

            val nomTextView = view.findViewById<TextInputLayout>(R.id.nomProfileEdit)
            val prenomTextView = view.findViewById<TextInputLayout>(R.id.prenomProfileEdit)
            val numeroTextView = view.findViewById<TextInputLayout>(R.id.phoneNumProfileEdit)

            val nom = nomTextView.editText?.text.toString()
            val prenom = prenomTextView.editText?.text.toString()
            val numero = numeroTextView.editText?.text.toString()

            studentRef.update(
                mapOf(
                    "nom" to nom,
                    "prenom" to prenom,
                    "numero" to numero
                )
            ).addOnSuccessListener {
                Toast.makeText(context, "Votre data est bien modifier", Toast.LENGTH_LONG).show()
            }.addOnFailureListener { e ->
                Toast.makeText(context, "Un error est occurer !", Toast.LENGTH_LONG).show()
            }
        }


    }

    private fun displayStudentInfo() {
        val db = Firebase.firestore
        val studentId = FirebaseAuth.getInstance().currentUser?.uid
        val studentRef = db.collection("students").document(studentId!!)

        studentRef.get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {

                    val nom = document.getString("nom")
                    val prenom = document.getString("prenom")
                    val email = document.getString("email")
                    val numero = document.getString("numero")

                    // get references to TextViews
                    val upEmailTextView = view?.findViewById<TextView>(R.id.profileEmail)
                    val fullNameTextView = view?.findViewById<TextView>(R.id.profileName)
                    val nomTextView =  view?.findViewById<TextInputLayout>(R.id.nomProfileEdit)
                    val prenomTextView =  view?.findViewById<TextInputLayout>(R.id.prenomProfileEdit)
                    //val emailTextView =  view?.findViewById<TextView>(R.id.emailProfileEdit)
                    val numeroTextView =  view?.findViewById<TextInputLayout>(R.id.phoneNumProfileEdit)

                    // set text values of TextViews
                    upEmailTextView?.text = email
                    fullNameTextView?.text = nom +" "+prenom
                    if (nomTextView != null) {
                        nomTextView.editText?.setText(nom)
                    }
                    if (prenomTextView != null) {
                        prenomTextView.editText?.setText(prenom)
                    }
                    //emailTextView?.text = email
                    if (numeroTextView != null) {
                        numeroTextView.editText?.setText(numero)
                    }
                } else {
                    // document not found
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                // handle errors
                Log.d(TAG, "get failed with ", exception)
            }
    }
}