package com.example.my_pfe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.my_pfe.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    lateinit var loginBinding: ActivityLoginBinding
    private lateinit var firebaseAuthe: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        val view = loginBinding.root
        setContentView(view)

        loginBinding.registerTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)

        }

        firebaseAuthe = FirebaseAuth.getInstance()


        loginBinding.loginButton.setOnClickListener{
            val email = loginBinding.emailEditText.editText?.text.toString()
            val password = loginBinding.passwordEditText.editText?.text.toString()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show()
            }
            else {
                //check the authentication email and password
                firebaseAuthe.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        val db = Firebase.firestore

                        //check wich user is logged by the email

                        //here check if user is a student

                        val studentsCollection = db.collection("students")
                        val studentsQuery = studentsCollection.whereEqualTo("email", email)

                        studentsQuery.get().addOnSuccessListener { documents ->
                            if (!documents.isEmpty) {

                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)

                            }
                            else {

                                //and here if the user is entreprise

                                val entreprisesCollection = db.collection("entreprises")
                                val entreprisesQuery = entreprisesCollection.whereEqualTo("email", email)

                                entreprisesQuery.get().addOnSuccessListener { documentss ->

                                    if (!documentss.isEmpty) {

                                        val intent = Intent(this, MainActivityEntreprise::class.java)
                                        startActivity(intent)
                                    }
                                    else {

                                        Toast.makeText(this, "Email not found", Toast.LENGTH_LONG).show()
                                    }
                                }.addOnFailureListener { e ->
                                    Toast.makeText(this, "Failed to retrieve entreprise documents", Toast.LENGTH_LONG).show()
                                }
                            }
                        }.addOnFailureListener { e ->
                            Toast.makeText(this, "Failed to retrieve student documents", Toast.LENGTH_LONG).show()
                        }

                    } else {
                        Toast.makeText(this, "Authentication failed: " + task.exception, Toast.LENGTH_LONG).show()
                    }
                }

            }
        }



    }
}