package com.example.my_pfe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.my_pfe.databinding.ActivityEntrepriseRegisterBinding

class EntrepriseRegisterActivity : AppCompatActivity() {

    lateinit var entrepriseRegisterBinding: ActivityEntrepriseRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        entrepriseRegisterBinding = ActivityEntrepriseRegisterBinding.inflate(layoutInflater)
        val view = entrepriseRegisterBinding.root
        setContentView(view)

        entrepriseRegisterBinding.etudiantButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        entrepriseRegisterBinding.loginTextView.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        entrepriseRegisterBinding.nextButton.setOnClickListener {

            val nom : String = entrepriseRegisterBinding.nameEditText.editText?.text.toString()
            val code_entreprise : String = entrepriseRegisterBinding.codeEditText.editText?.text.toString()
            val email : String = entrepriseRegisterBinding.emailEditText.editText?.text.toString()
            val adress : String = entrepriseRegisterBinding.adresseEditText.editText?.text.toString()
            val password : String = entrepriseRegisterBinding.passwordEditText.editText?.text.toString()


            if (nom.isBlank() || code_entreprise.isBlank() || email.isBlank() || password.isBlank() || adress.isBlank()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_LONG).show()

            }else {
                val intent = Intent(this, EntrepriseRegisterPart2::class.java)
                intent.putExtra("nom", nom)
                intent.putExtra("code_entreprise", code_entreprise)
                intent.putExtra("email", email)
                intent.putExtra("adress", adress)
                intent.putExtra("password", password)
                startActivity(intent)
            }
        }

    }
}