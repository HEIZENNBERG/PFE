package com.example.my_pfe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.my_pfe.databinding.ActivityEntrepriseProfileBinding

class EntrepriseProfile : AppCompatActivity() {

    lateinit var entrepriseProfileBinding: ActivityEntrepriseProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        entrepriseProfileBinding = ActivityEntrepriseProfileBinding.inflate(layoutInflater)
        val view = entrepriseProfileBinding.root
        setContentView(view)

        entrepriseProfileBinding.backHome.setOnClickListener {
            val intent = Intent(this, MainActivityEntreprise::class.java)
            startActivity(intent)
            finish()
        }
    }
}