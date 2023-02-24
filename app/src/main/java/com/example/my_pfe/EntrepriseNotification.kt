package com.example.my_pfe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.my_pfe.databinding.ActivityEntrepriseNotificationBinding

class EntrepriseNotification : AppCompatActivity() {

    lateinit var entrepriseNotificationBinding: ActivityEntrepriseNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        entrepriseNotificationBinding = ActivityEntrepriseNotificationBinding.inflate(layoutInflater)
        val view = entrepriseNotificationBinding.root
        setContentView(view)

        entrepriseNotificationBinding.backHomeButton.setOnClickListener {
            val intent = Intent(this, MainActivityEntreprise::class.java)
            startActivity(intent)
            finish()
        }

    }
}