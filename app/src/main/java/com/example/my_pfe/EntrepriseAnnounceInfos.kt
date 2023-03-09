package com.example.my_pfe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.my_pfe.databinding.ActivityEntrepriseAnnounceInfosBinding
import com.example.my_pfe.databinding.EntrepriseAnnonceBinding

class EntrepriseAnnounceInfos : AppCompatActivity() {

    lateinit var entrepriseAnnonceInfosBinding: ActivityEntrepriseAnnounceInfosBinding
    lateinit var adapter : StagaireAdapter
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        entrepriseAnnonceInfosBinding = ActivityEntrepriseAnnounceInfosBinding.inflate(layoutInflater)
        val view = entrepriseAnnonceInfosBinding.root
        setContentView(view)

        val name = intent.getStringExtra("name")
        val annonceName = findViewById<TextView>(R.id.annonceName)
        annonceName.text = name

        recyclerView = findViewById(R.id.StagaireRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StagaireAdapter()
        recyclerView.adapter = adapter

        entrepriseAnnonceInfosBinding.goBackFromAnnonce.setOnClickListener {
            val intent = Intent(this, MainActivityEntreprise::class.java)
            startActivity(intent)
            finish()
        }

    }
}