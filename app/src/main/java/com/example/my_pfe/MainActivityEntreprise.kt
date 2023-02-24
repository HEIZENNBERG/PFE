package com.example.my_pfe

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.my_pfe.databinding.ActivityEntrepriseMainBinding
import com.example.my_pfe.databinding.ActivityMainEntrepriseBinding

class MainActivityEntreprise : AppCompatActivity(){

    lateinit var mainBinding: ActivityMainEntrepriseBinding

    private var isListnersEnabeld = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainEntrepriseBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

            // Add any necessary code to initialize the views and components
            mainBinding.entrepriseProfile.setOnClickListener {
                if(isListnersEnabeld)
                {
                    val intent = Intent(this, EntrepriseProfile::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            mainBinding.entrepriseMainName.setOnClickListener {
                if(isListnersEnabeld)
                {
                    val intent = Intent(this, EntrepriseProfile::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            mainBinding.entrepriseNotification.setOnClickListener {
                if(isListnersEnabeld)
                {
                    val intent = Intent(this, EntrepriseNotification::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            mainBinding.entrepriseNotification.setOnClickListener {
                if(isListnersEnabeld){
                    val intent = Intent(this, EntrepriseNotification::class.java)
                    startActivity(intent)
                    finish()
                }
            }

        mainBinding.addAnn.setOnClickListener {
            if(isListnersEnabeld)
            {
                val fragment = FragmentAddAnnonceTest()
                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                ft.replace(R.id.entrepriseMainMenu, fragment)
                ft.addToBackStack(null)
                ft.commit()
                // Stop the activity from working
                isListnersEnabeld = false
            }
        }

    }

    fun enableListeners() {
        isListnersEnabeld = true
    }

}


