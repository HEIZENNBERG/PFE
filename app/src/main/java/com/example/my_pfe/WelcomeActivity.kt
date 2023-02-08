package com.example.my_pfe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.my_pfe.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    lateinit var welcomeBinding : ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        welcomeBinding = ActivityWelcomeBinding.inflate(layoutInflater)
        val view = welcomeBinding.root
        setContentView(view)

        welcomeBinding.loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        welcomeBinding.registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}