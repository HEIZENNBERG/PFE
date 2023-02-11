package com.example.my_pfe

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.my_pfe.databinding.ActivityEntrepriseRegisterBinding
import com.example.my_pfe.databinding.ActivityEntrepriseRegisterPart2Binding



class EntrepriseRegisterPart2 : AppCompatActivity() {

    lateinit var entrepriseRegisterPart2: ActivityEntrepriseRegisterPart2Binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        entrepriseRegisterPart2 = ActivityEntrepriseRegisterPart2Binding.inflate(layoutInflater)
        val view = entrepriseRegisterPart2.root
        setContentView(view)


        val getLogoImage = registerForActivityResult(
            ActivityResultContracts.GetContent(), ActivityResultCallback {
                // what you wanna do with the image
            }
        )

        val categories = resources.getStringArray(R.array.entreprise_categories)
        val arrayAdapter = ArrayAdapter(this, R.layout.list_categorie, categories)
        entrepriseRegisterPart2.autoCompleteTextView.setAdapter(arrayAdapter)

        entrepriseRegisterPart2.etudiantButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        entrepriseRegisterPart2.loginTextView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        entrepriseRegisterPart2.registerButton.setOnClickListener {
            // transfer data to firebase

            // first register data
            val nom = intent.getStringExtra("nom").toString()
            val code_entreprise = intent.getStringExtra("code_entreprise").toString().toInt()
            val email = intent.getStringExtra("email").toString()
            val adress = intent.getStringExtra("adress").toString()
            val password = intent.getStringExtra("password").toString()

            // second register data
            val numero = entrepriseRegisterPart2.numeroEditText.editText?.text.toString().toInt()
            val description = entrepriseRegisterPart2.descriptionEditText.editText?.text.toString()
            val categorie = entrepriseRegisterPart2.categorieEditText.editText.toString()

        }

        entrepriseRegisterPart2.backImageButton.setOnClickListener {
            val intent = Intent(this, EntrepriseRegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        entrepriseRegisterPart2.logoButton.setOnClickListener {
            getLogoImage.launch("image/*")
        }

    }
}