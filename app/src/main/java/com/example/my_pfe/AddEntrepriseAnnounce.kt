package com.example.my_pfe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.my_pfe.databinding.ActivityAddEntrepriseAnnounceBinding

class AddEntrepriseAnnounce : AppCompatActivity() {

    lateinit var addEntrepriseAnnounceBinding: ActivityAddEntrepriseAnnounceBinding
    var deparetement_holder : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addEntrepriseAnnounceBinding = ActivityAddEntrepriseAnnounceBinding.inflate(layoutInflater)
        val view = addEntrepriseAnnounceBinding.root
        setContentView(view)

        // Create an ArrayAdapter to populate the dropdown menu
        val categories = resources.getStringArray(R.array.entreprise_departements)
        val adapter = ArrayAdapter(this, R.layout.list_categorie, categories)
        adapter.setDropDownViewResource(R.layout.list_categorie)

        // Get the dropdown menu view and set the adapter
        addEntrepriseAnnounceBinding.annonceDepartementEditText.setAdapter(adapter)

        // Set an OnItemClickedListener to get the Clicked item
        addEntrepriseAnnounceBinding.annonceDepartementEditText.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position) as String
                // Use the selected item as needed
                deparetement_holder = selectedItem
            }
        }

        addEntrepriseAnnounceBinding.cancelButton.setOnClickListener {
            val intent = Intent(this, MainActivityEntreprise::class.java)
            startActivity(intent)
            finish()
        }

        addEntrepriseAnnounceBinding.addAnnounce.setOnClickListener {

        }

    }
}