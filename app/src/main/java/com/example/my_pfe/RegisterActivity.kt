package com.example.my_pfe

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isEmpty
import com.example.my_pfe.databinding.ActivityRegisterBinding
import com.google.common.base.Ascii
import com.google.common.base.Ascii.toLowerCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {

    lateinit var registerBinding: ActivityRegisterBinding


    private lateinit var firebaseAuthe: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = registerBinding.root
        setContentView(view)

        registerBinding.entrepriseButton.setOnClickListener {
            val intent = Intent(this, EntrepriseRegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        registerBinding.loginTextView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        firebaseAuthe = FirebaseAuth.getInstance()

        registerBinding.registerButton.setOnClickListener {

            val nom = registerBinding.nameEditText.editText?.text.toString()
            val prenom = registerBinding.prenomEditText.editText?.text.toString()
            val email = registerBinding.emailEditText.editText?.text.toString()
            val password = registerBinding.passwordEditText.editText?.text.toString()
            val numero = registerBinding.numEditText.editText?.text.toString()


            if (nom.isBlank() || prenom.isBlank() || email.isBlank() || password.isBlank() || numero.isBlank()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show()

            }
            else {
                val stud = Student(nom, prenom, toLowerCase(email), numero)
                firebaseAuthe.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if(it.isSuccessful){
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    val user = it.result?.user
                    val uid = user?.uid
                    if (uid != null) {
                        saveStudent(stud, uid)
                    }
                }
            }
        }

    }

    private fun saveStudent(std : Student,uid : String) = CoroutineScope(Dispatchers.IO).launch {
        try{
            val StudentCollection = Firebase.firestore.collection("students").document(uid)
            StudentCollection.set(std).await()

            withContext(Dispatchers.Main){
                Toast.makeText(this@RegisterActivity, "Votre data est bien enregistrer", Toast.LENGTH_LONG).show()
                registerBinding.nameEditText.editText!!.text.clear()
                registerBinding.emailEditText.editText!!.text.clear()
                registerBinding.prenomEditText.editText!!.text.clear()
                registerBinding.passwordEditText.editText!!.text.clear()
                registerBinding.numEditText.editText!!.text.clear()
            }
        }catch(e: java.lang.Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(this@RegisterActivity, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

}