package com.example.my_pfe

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.my_pfe.databinding.ActivityEntrepriseAnnounceInfosBinding
import com.example.my_pfe.databinding.EntrepriseAnnonceBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EntrepriseAnnounceInfos : AppCompatActivity(), OnItemClickListener {

    lateinit var entrepriseAnnonceInfosBinding: ActivityEntrepriseAnnounceInfosBinding
    lateinit var adapter : StagaireAdapter
    lateinit var recyclerView: RecyclerView
    private lateinit var demandesList : ArrayList<Demande>
    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        entrepriseAnnonceInfosBinding = ActivityEntrepriseAnnounceInfosBinding.inflate(layoutInflater)
        val view = entrepriseAnnonceInfosBinding.root
        setContentView(view)
        recyclerView = findViewById(R.id.StagaireRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val name = intent.getStringExtra("name")
        val id = intent.getStringExtra("id")
        val entrepriseid = intent.getStringExtra("entrepriseId")

        val annonceName = findViewById<TextView>(R.id.annonceName)
        annonceName.text = name

        demandesList = arrayListOf()

        db = FirebaseFirestore.getInstance()

        val annonceRef = db.collection("entreprises").document(entrepriseid.toString()).collection("annonces").document(id.toString())
        val demandesRef = annonceRef.collection("demandes")

        demandesRef.get().addOnSuccessListener { documents ->
            for (document in documents) {
                val demande : Demande? = document.toObject(Demande::class.java)
                if (demande != null) {
                    demandesList.add(demande)
                }
            }
            adapter = StagaireAdapter(demandesList, this)
            recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }


        entrepriseAnnonceInfosBinding.goBackFromAnnonce.setOnClickListener {
            val intent = Intent(this, MainActivityEntreprise::class.java)
            startActivity(intent)
            finish()
        }

    }
    override fun onItemClick(position: Int) {
        val url = demandesList[position].pdfUrl
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse(url), "application/pdf")
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        val chooser = Intent.createChooser(intent, "Open File")
        try {
            startActivity(chooser)
        } catch (e: Exception) {
            // Handle any errors
        }
    }
}