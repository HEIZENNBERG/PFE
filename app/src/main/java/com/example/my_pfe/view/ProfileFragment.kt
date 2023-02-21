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
import com.example.my_pfe.R
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
                    val nomTextView =  view?.findViewById<TextView>(R.id.nomProfileEdit)
                    val prenomTextView =  view?.findViewById<TextView>(R.id.prenomProfileEdit)
                    val emailTextView =  view?.findViewById<TextView>(R.id.emailProfileEdit)
                    val numeroTextView =  view?.findViewById<TextView>(R.id.phoneNumProfileEdit)

                    // set text values of TextViews
                    upEmailTextView?.text = email
                    fullNameTextView?.text = nom +" "+prenom
                    nomTextView?.text = nom
                    prenomTextView?.text = prenom
                    emailTextView?.text = email
                    numeroTextView?.text = numero
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