package com.example.my_pfe

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.my_pfe.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    lateinit var registerBinding: ActivityRegisterBinding

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

    }
}