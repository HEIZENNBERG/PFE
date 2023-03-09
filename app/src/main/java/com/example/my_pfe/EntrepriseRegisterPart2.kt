package com.example.my_pfe

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.my_pfe.databinding.ActivityEntrepriseRegisterPart2Binding
import com.google.android.gms.tasks.Task
import com.google.common.base.Ascii.toLowerCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await



class EntrepriseRegisterPart2 : AppCompatActivity() {

    lateinit var entrepriseRegisterPart2: ActivityEntrepriseRegisterPart2Binding
    var categorie_holder : String = ""
    var imageUri : Uri? = null


    private lateinit var storage : StorageReference

    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        entrepriseRegisterPart2 = ActivityEntrepriseRegisterPart2Binding.inflate(layoutInflater)
        val view = entrepriseRegisterPart2.root
        setContentView(view)



        // Create an ArrayAdapter to populate the dropdown menu
        val categories = resources.getStringArray(R.array.entreprise_categories)
        val adapter = ArrayAdapter(this, R.layout.list_categorie, categories)
        adapter.setDropDownViewResource(R.layout.list_categorie)


        // Get the dropdown menu view and set the adapter
        entrepriseRegisterPart2.autoCompleteTextView.setAdapter(adapter)


        // Set an OnItemClickedListener to get the Clicked item
        entrepriseRegisterPart2.autoCompleteTextView.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position) as String
                // Use the selected item as needed
                categorie_holder = selectedItem
            }
        }

        entrepriseRegisterPart2.etudiantButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)

        }

        entrepriseRegisterPart2.loginTextView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        }

        firebaseAuth = FirebaseAuth.getInstance()

        entrepriseRegisterPart2.registerButton.setOnClickListener {
            // transfer data to firebase

            // first register data
            val nom = intent.getStringExtra("nom").toString()
            val code_entreprise = intent.getStringExtra("code_entreprise").toString()
            val email = intent.getStringExtra("email").toString()
            val adress = intent.getStringExtra("adress").toString()
            val password = intent.getStringExtra("password").toString()

            // second register data
            val numero = entrepriseRegisterPart2.numeroEditText.editText?.text.toString()
            val description = entrepriseRegisterPart2.descriptionEditText.editText?.text.toString()


            if (numero.isBlank() || description.isBlank() || categorie_holder.isBlank()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show()

            } else {
                storage = FirebaseStorage.getInstance().reference.child("images")
                storage = storage.child(System.currentTimeMillis().toString())

                val uploadTask = imageUri?.let {
                    storage.putFile(it)
                } ?: throw IllegalArgumentException("Image Uri cannot be null")

                uploadTask.continueWithTask { task ->
                    if (task.isSuccessful) {
                        storage.downloadUrl
                    } else {
                        throw task.exception ?: IllegalStateException("Failed to upload image")
                    }
                }.addOnSuccessListener { uri ->
                    var url = uri.toString()


                val entr = Entreprise(nom, code_entreprise, toLowerCase(email) , adress, numero, categorie_holder, description, url)
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if(it.isSuccessful){
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    val user = it.result?.user
                    val uid = user?.uid
                    if (uid != null) {
                        saveCompany(entr, uid)
                    }
                }
                }
            }
        }


        entrepriseRegisterPart2.backImageButton.setOnClickListener {
            val intent = Intent(this, EntrepriseRegisterActivity::class.java)
            startActivity(intent)
        }

        val getLogoImage = registerForActivityResult(
            ActivityResultContracts.GetContent()){

                imageUri = it
        }

        entrepriseRegisterPart2.logoButton.setOnClickListener {
            getLogoImage.launch("image/*")
        }

    }



    private fun saveCompany(ent : Entreprise,uid : String) = CoroutineScope(Dispatchers.IO).launch {

        try{
            val CompanyCollection = Firebase.firestore.collection("entreprises").document(uid)
            CompanyCollection.set(ent).await()


            withContext(Dispatchers.Main){
                Toast.makeText(this@EntrepriseRegisterPart2, "Votre data est bien enregistrer", Toast.LENGTH_LONG).show()
                entrepriseRegisterPart2.numeroEditText.editText!!.text.clear()
                entrepriseRegisterPart2.descriptionEditText.editText!!.text.clear()
                entrepriseRegisterPart2.categorieEditText.editText!!.text.clear()
            }
        }catch(e: java.lang.Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(this@EntrepriseRegisterPart2, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

}