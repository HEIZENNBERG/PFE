package com.example.my_pfe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.my_pfe.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var loginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        val view = loginBinding.root
        setContentView(view)

        loginBinding.registerTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}