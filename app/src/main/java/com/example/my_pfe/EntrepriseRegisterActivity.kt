package com.example.my_pfe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    }
}